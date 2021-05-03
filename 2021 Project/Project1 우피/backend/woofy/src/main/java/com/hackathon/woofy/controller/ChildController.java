package com.hackathon.woofy.controller;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hackathon.woofy.entity.Child;
import com.hackathon.woofy.entity.Mission;
import com.hackathon.woofy.entity.Parent;
import com.hackathon.woofy.entity.User;
import com.hackathon.woofy.request.UserRequest;
import com.hackathon.woofy.response.BasicResponse;
import com.hackathon.woofy.service.ChildService;
import com.hackathon.woofy.service.ParentService;
import com.hackathon.woofy.service.RedisService;
import com.hackathon.woofy.service.SuspiciousService;
import com.hackathon.woofy.service.UserService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/child")
public class ChildController {

	private final ChildService childService;
	private final UserService userService;
	private final ParentService parentService;
	private final SuspiciousService suspiciousService;
	
	@Autowired PasswordEncoder passwordEncoder;
	@Autowired RedisService redisService;

	@PostMapping(value = "", produces = "application/json; charset=utf8")
	public Object signup(@RequestBody Map<String, Object> jsonRequest) {

		final BasicResponse basicResponse = new BasicResponse();

		Map<String, Object> dataBodyObject = (Map<String, Object>) jsonRequest.get("dataBody");
		Map<String, Object> userObject = new JSONObject(), childObject = new JSONObject();
		
		String targetPassword = (String)dataBodyObject.get("password");
		
		String targetRequestCode = (String) jsonRequest.get("requestCode");
		String targetSMSCRTFCode = (String) jsonRequest.get("smsCRTFCode");

		// User Data
		userObject.put("username", (String)dataBodyObject.get("username"));
		userObject.put("password", passwordEncoder.encode(targetPassword));
		userObject.put("phoneNumber", (String)dataBodyObject.get("phoneNumber"));
		userObject.put("role", "ROLE_CHILD");
		
		// Parent Data
		childObject.put("firstName", (String)dataBodyObject.get("firstName"));
		childObject.put("lastName", (String)dataBodyObject.get("lastName"));
		childObject.put("birthDay", (String)dataBodyObject.get("birthDay"));
		
		
		try {
			String requestParentUsername = redisService.getHashSetItem("ChildSignupRequestParentTable", targetRequestCode);

			// 1. 요청한 부모의 데이터를 검증한다.
			if (requestParentUsername == null) {
				basicResponse.status = "400";
				return new ResponseEntity<>(basicResponse, HttpStatus.BAD_REQUEST);
			}
			
			User targetParentID = userService.findByUsername(requestParentUsername);	// 현단계에서는 디버그 전화번호를 사용한다. -> 조회할 때는 username으로 조회하기로 통일합시다
			
			if (targetParentID == null) {
				basicResponse.status = "400";
				return new ResponseEntity<>(basicResponse, HttpStatus.BAD_REQUEST);
			}
			
			Parent targetParent = parentService.findByUser(targetParentID);
			
			// 2. SMS 인증의 유효성을 검증한다.
			String requestPhoneNumber = redisService.getHashSetItem("ChildSignupRequestSMSTable", targetSMSCRTFCode);
			
			if (requestPhoneNumber == null) {
				basicResponse.status = "400";
				return new ResponseEntity<>(basicResponse, HttpStatus.BAD_REQUEST);
			}
			
			// 3. 자녀의 정보를 등록한다. 
			User user = new User(userObject);		
			Child child = new Child(childObject, targetParent);
			
			User newUser = userService.saveUser(user);
			child.setUser(newUser);
			child.setAuth(true);
			
			Child result = childService.saveChild(child);
			
			// 4. 결과 값을 리턴하기 위한 오브젝트를 생성하고 basicResponse에 기록한다.
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("username", user.getUsername());
			
			basicResponse.dataBody = jsonObject;
			basicResponse.status = "201";
		} catch (Exception e) {
			basicResponse.status = "500";
			e.printStackTrace();
		}

		return new ResponseEntity<>(basicResponse, HttpStatus.OK);
	}
	
	@Secured({"ROLE_PARENT", "ROLE_CHILD"})
	@GetMapping(value = "/{c_username}")
	public Object getChildInfo(@PathVariable(value="c_username") String c_username) {
		final BasicResponse basicResponse = new BasicResponse();

		try {
			Map<String, Object> map = new HashMap<>();

			Child result = childService.findByUsername(c_username);
			
			map.put("child", result);
			basicResponse.dataBody = map;
			basicResponse.status = "success";

		} catch (Exception e) {
			basicResponse.status = "error";
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(basicResponse, HttpStatus.OK);
	}
	
	@Secured({"ROLE_CHILD"})
	@PutMapping(value = "/secretcode", produces = "application/json; charset=utf8")
	public Object modifyChildInfo(@RequestBody Map<String, Object> jsonRequest) {
		final BasicResponse basicResponse = new BasicResponse();
		
		Map<String, Object> childObject = (Map<String, Object>) jsonRequest.get("dataBody");
		User authUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		String targetSecretCode = (String)childObject.get("code");
		System.out.println(targetSecretCode);
		authUser.applySecretCodeChange(passwordEncoder.encode(targetSecretCode));
		
		userService.saveUser(authUser);
		
//		System.out.println(authUser.getSecretCode());
		
		basicResponse.status = "200";
		return new ResponseEntity<>(basicResponse, HttpStatus.OK);
	}


	@PutMapping(value = "/{childUsername}", produces = "application/json; charset=utf8")
	public String modifyChildInfo(@PathVariable(value="childUsername") String childUsername, @RequestBody Map<String, Object> jsonRequest) {
		// JsonObject dataBody = JsonParser.parseString(jsonRequest.toString()).getAsJsonObject();
		Map<String, Object> childObject = (Map<String, Object>) jsonRequest.get("dataBody");
		System.out.println(childObject);
		
		return "DEBUG";
	}

	/**
	 * 자식 삭제
	 * @param parentUserName
	 * @return
	 */
	@Secured({"ROLE_PARENT"})
	@DeleteMapping(value = "/{child_id}", produces = "application/json; charset=utf8")
	public Object deleteParentInfo(@PathVariable(value="child_id") Long child_id) {
		final BasicResponse basicResponse = new BasicResponse();
		
		Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
		Parent targetRequestedParent = parentService.findByUsername(authUser.getName());

		try {
			childService.deleteChild(child_id);
			basicResponse.status = "success";
		} catch (Exception e) {
			basicResponse.status = "error";
			e.printStackTrace();
		} finally {
			return new ResponseEntity<>(basicResponse, HttpStatus.OK);
		}
	}

	@Secured("ROLE_PARENT")
	@PutMapping(value = "/{childUsername}/spendlimit", produces = "application/json; charset=utf8")
	public Object modifyChildAllowenceInfo(@PathVariable(value="childUsername") String childUsername, @RequestBody Map<String, Object> jsonRequest) {
		final BasicResponse basicResponse = new BasicResponse();
		User authUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Map<String, Object> childObject = (Map<String, Object>) jsonRequest.get("dataBody");
		String requestType = (String) childObject.get("type");
		int requestAmount = (int) childObject.get("amount");
				
		// 디버그 용도로 계좌 확인 절차 비활성화
		
		try {
			Map<String, Object> map = new HashMap<>();
			Child child = childService.findByUsername(childUsername);
			
			if (child == null || !child.getParent().getUser().getUsername().equals(authUser.getUsername())) {
				basicResponse.status = "invalid";
				return new ResponseEntity<>(basicResponse, HttpStatus.BAD_REQUEST);
			}
						
			if (requestType.equals("1")) {
				// 여기에서 부모의 한도 검사 기능 추가 예정
				child.increaseSpendLimit(requestAmount);
				childService.saveChild(child);
				basicResponse.status = "success";
			}
			
			else {
				if (child.decreaseSpendLimit(requestAmount)) {
					childService.saveChild(child);
					basicResponse.status = "success";
				}
				
				else {
					basicResponse.status = "fail";
				}
			}
			
			map.put("spendLimit", child.getSpendLimit());
			basicResponse.dataBody = map;
		} catch (Exception e) {
			basicResponse.status = "error";
			e.printStackTrace();
			return new ResponseEntity<>(basicResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(basicResponse, HttpStatus.OK);
	}
}