package com.ssafy.projectboot.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.projectboot.dto.FavoriteDto;
import com.ssafy.projectboot.dto.LocationDto;
import com.ssafy.projectboot.dto.MemberDto;
import com.ssafy.projectboot.dto.ZzimDto;
import com.ssafy.projectboot.service.FavoriteService;

@RestController
@RequestMapping("/favorite")
public class FavoriteRestContoller {

	@Autowired
	private FavoriteService favorieService;

	// loccode를 찾기 : findLoccode, favorite테이블에 insert하기.
	@GetMapping("/add")
	public List<FavoriteDto> add(@RequestParam Map<String, String> map, HttpSession session) {

		/** loccode 얻어오기 */
		String gu = map.get("gu");
		String dong = map.get("dong");

		LocationDto loc = new LocationDto();
		loc.setGu(gu);
		loc.setDong(dong);
		String loccode = favorieService.findLoccode(loc);

		/** 관심지역 테이블에 데이터 추가하기 */
		MemberDto member = (MemberDto) session.getAttribute("userinfo");
		String userid = member.getUserid();
		FavoriteDto fav = new FavoriteDto();
		fav.setLoccode(loccode);
		fav.setUserid(userid);
		if (favorieService.addFavorite(fav) > 0) {
			System.out.println("성공적으로 추가되었습니다.");
		}
		; // id랑 loccode 보내줌

		/** 관심지역 리스트 리턴해주기 */
		List<FavoriteDto> list = favorieService.listFavorite(userid);
		return list;
	}

	@GetMapping("/list")
	public List<FavoriteDto> list(HttpSession session) {
		MemberDto member = (MemberDto) session.getAttribute("userinfo");
		String userid = member.getUserid();
		return favorieService.listFavorite(userid);
	}
	
	// 찜목록에 있는지 조회!
	@GetMapping("/zzim")
	public int inZzim(@RequestParam Map<String, String> map, HttpSession session) {
		int result=0;
		String no = map.get("no");
		MemberDto member = (MemberDto) session.getAttribute("userinfo");
		String userid = member.getUserid();
		if(favorieService.isZzim(no, userid)>=1) {
			result = 1;
		}
		System.out.println("찜목록에서 조회 결과 "+ result);
		return result;
	}
	
	// 찜목록에 추가
	@PostMapping("/zzim")
	public void addZzim(@RequestParam Map<String, String> map, HttpSession session) {
		String no = map.get("no");
		MemberDto member = (MemberDto) session.getAttribute("userinfo");
		String userid = member.getUserid();
		favorieService.addZzim(no, userid);
		System.out.println("찜목록에"+no+"번 매물이 추가되었습니다.");
	}
//	public void addZzim(@PathVariable String no, HttpSession session) {
//		MemberDto member = (MemberDto) session.getAttribute("userinfo");
//		String userid = member.getUserid();
//		favorieService.deleteZzim(no,userid);
//	}
	
	//찜목록에서 삭제
	@DeleteMapping("/zzim")
	public void deleteZzim(@RequestParam Map<String, String> map, HttpSession session) {
		String no = map.get("no");
		MemberDto member = (MemberDto) session.getAttribute("userinfo");
		String userid = member.getUserid();
		favorieService.deleteZzim(no, userid);
	}
	
	
}
