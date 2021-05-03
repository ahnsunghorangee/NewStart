package com.hackathon.woofy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hackathon.woofy.entity.Parent;
import com.hackathon.woofy.entity.User;
import com.hackathon.woofy.request.UserRequest;
import com.hackathon.woofy.response.BasicResponse;
import com.hackathon.woofy.service.ChildService;
import com.hackathon.woofy.service.ParentService;
import com.hackathon.woofy.service.RedisService;
import com.hackathon.woofy.service.UserService;
import com.hackathon.woofy.util.SmsFunc;
import com.hackathon.woofy.util.WooriFunc;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/api/v1/parent")
public class ParentController {
	
	@Autowired ParentService parentService;
	@Autowired ChildService childService;
	@Autowired UserService userService;
	@Autowired RedisService redisService;
	@Autowired PasswordEncoder passwordEncoder;
	
	WooriFunc wooriFunc = new WooriFunc();
	SmsFunc smsFunc = new SmsFunc();
	
	@PostMapping(value = "", produces = "application/json; charset=utf8")
	public Object signup(@RequestBody Map<String, Object> jsonRequest) {

		final BasicResponse basicResponse = new BasicResponse();
		
		Map<String, Object> dataBodyObject = (Map<String, Object>) jsonRequest.get("dataBody");
		Map<String, Object> userObject = new JSONObject(), parentObject = new JSONObject();
		
		String targetPassword = (String)dataBodyObject.get("password");
		
		// User Data
		userObject.put("username", (String)dataBodyObject.get("username"));
		userObject.put("password", passwordEncoder.encode(targetPassword));
		userObject.put("phoneNumber", (String)dataBodyObject.get("phoneNumber"));
		userObject.put("role", "ROLE_PARENT");
		
		// Parent Data
		parentObject.put("firstName", (String)dataBodyObject.get("firstName"));
		parentObject.put("lastName", (String)dataBodyObject.get("lastName"));
		parentObject.put("email", (String)dataBodyObject.get("email"));
		parentObject.put("birthDay", (String)dataBodyObject.get("birthDay"));
		parentObject.put("accountNumber", (String)dataBodyObject.get("accountNumber"));
		
		String requestCode = (String)jsonRequest.get("requestCode"); // JPA 기반으로 작성된 코드의 동작을 저해하지 않도록 하기 위해 dataBody와 별도의 key를 분리했다.
		
		if (requestCode == null) {
			basicResponse.status = "400";
			new ResponseEntity<>(basicResponse, HttpStatus.BAD_REQUEST);
		}
		
		try {
			User user = new User(userObject);
			Parent parent = new Parent(parentObject);
			
			// KeyDB 서버에 가입 CRTF 키가 저장되어 있는지 확인한다. 없으면 유효하지 않은 가입 세션이 된다. (keyDB를 이용하여 유사 세션을 구현했다.)
			String currentPhoneNumber = redisService.getHashSetItem("ParentJoinCRTFTable", requestCode);
			
			if (currentPhoneNumber == null || !currentPhoneNumber.equals(user.getPhoneNumber())) {
				basicResponse.status = "400";
				new ResponseEntity<>(basicResponse, HttpStatus.BAD_REQUEST);
			}
			
			// KeyDB에 저장된 계좌 정보의 나머지를 가져온다.
			String currentAccountRemainNumber = redisService.getHashSetItem("ParentJoinAccTable", requestCode);

			// Account의 나머지 부분과 현재 부분을 합쳐서 저장한다.
			parent.setAccount(currentAccountRemainNumber + parent.getAccount());
			
			// 계좌에 대한 간단 정보를 가져온다.
			String accountVerifyResult = wooriFunc.getAccBasicInfo(parent.getAccount(), "20210220", "P", "KRW");
			
			// 계좌 정보가 없으면 400 에러를 반환한다. (해커톤 프로젝트 상에서 API 호출시 에러 확률은 적음)
			if (accountVerifyResult == null) {
				basicResponse.status = "400";
				new ResponseEntity<>(basicResponse, HttpStatus.BAD_REQUEST);
			}
			
			parent.setAuth(true);	// 부모의 경우 이전의 단계들로부터 휴대폰 인증을 완료했다. 여기에서는 바로 true로 설정한다.
			
			// 부모와 유저의 정보를 저장한다.		
			User newUser = userService.saveUser(user);
			parent.setUser(newUser);
			parentService.saveParent(parent);
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("username", user.getUsername());
			
			basicResponse.dataBody = jsonObject;
			basicResponse.status = "201";
		} catch(Exception e) {
			basicResponse.status = "500";
			e.printStackTrace();
			
			return new ResponseEntity<>(basicResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(basicResponse, HttpStatus.CREATED);
	}
	
	@Secured("ROLE_PARENT")
	@PostMapping(value = "/code", produces = "application/json; charset=utf8")
	public Object createAndSendChildJoin(@RequestBody Map<String, Object> jsonRequest) {
		Map<String, Object> joinRequestObject = (Map<String, Object>) jsonRequest.get("dataBody");
		
		String frontPageURL = "http://localhost:8081/ChildSignup?request_code=";
		String targetRequestCode = "MQ" + RandomStringUtils.randomNumeric(10);
		
		String targetUserName = (String)joinRequestObject.get("userName");
		String targetPhoneNumber = (String)joinRequestObject.get("phoneNumber");
		
		redisService.insertHashTableContent("ChildSignupRequestParentTable", targetRequestCode, targetUserName);
		redisService.setHashSetTimeLimit("ChildSignupRequestParentTable", targetRequestCode, 900);

		String targetMessage1 = 
				"Woori i 자녀 계정 가입은 이 링크를 통해 진행해주세요. (";
		
		String targetMessage2 = frontPageURL + targetRequestCode + ")";
		smsFunc.sendMessage(targetPhoneNumber, targetMessage1 + targetMessage2);
		
		final BasicResponse basicResponse = new BasicResponse();
		
		JSONObject responseObject = new JSONObject();
		responseObject.put("url", frontPageURL + targetRequestCode);
		
		basicResponse.status = "201";
		basicResponse.dataBody = responseObject;
		
		return new ResponseEntity<>(basicResponse, HttpStatus.CREATED);
	}
	
	@Secured({"ROLE_PARENT"})
	@PutMapping(value = "/secretcode", produces = "application/json; charset=utf8")
	public Object modifyChildInfo(@RequestBody Map<String, Object> jsonRequest) {
		final BasicResponse basicResponse = new BasicResponse();
		
		Map<String, Object> childObject = (Map<String, Object>) jsonRequest.get("dataBody");
		User authUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		String targetSecretCode = (String)childObject.get("code");
		authUser.applySecretCodeChange(passwordEncoder.encode(targetSecretCode));
		
		userService.saveUser(authUser);
		
		basicResponse.status = "200";
		
		return new ResponseEntity<>(basicResponse, HttpStatus.OK);
	}
	
	/**
	 * 부모
	 * 자식의 username을 통해 한도를 조정한다.
	 * @param userRequest
	 * @return
	 */
	@Secured({"ROLE_PARENT"})
	@PutMapping(value = "/changeLimit", produces = "application/json; charset=utf8")
	public Object changeLimit(@RequestBody UserRequest userRequest) {
		final BasicResponse basicResponse = new BasicResponse();

		Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
		Parent targetRequestedParent = parentService.findByUsername(authUser.getName());
		
		try {
			Child child = childService.findByUsername(userRequest.getUsername());
			
			// 자신의 자식인지 판별을 해야하는데 일단 패스
			
			child.setSpendLimit(userRequest.getSpendLimit());
			childService.saveChild(child);

			if (child == null) {
				basicResponse.status = "none";
			} else {
				basicResponse.status = "success";
			}

		} catch (Exception e) {
			basicResponse.status = "error";
			e.printStackTrace();
		} finally {
			return new ResponseEntity<>(basicResponse, HttpStatus.OK);
		}
	}

		
	@Secured({"ROLE_PARENT"})
	@GetMapping(value = "/childs", produces = "application/json; charset=utf8")
	public Object getParentChildList() {
		final BasicResponse basicResponse = new BasicResponse();

		Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
		Parent targetRequestedParent = parentService.findByUsername(authUser.getName());
		
		try {
			Map<String, Object> map = new HashMap<>();

			List<Child> childs = childService.findByParent(targetRequestedParent);

			if (childs == null) {
				basicResponse.status = "none";
			} else {

				map.put("child", childs);
				basicResponse.dataBody = map;
				basicResponse.status = "success";
			}

		} catch (Exception e) {
			basicResponse.status = "error";
			e.printStackTrace();
		} finally {
			return new ResponseEntity<>(basicResponse, HttpStatus.OK);
		}

	}

}
