package com.ssafy.projectboot.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssafy.projectboot.dto.MemberDto;
import com.ssafy.projectboot.service.FavoriteService;

@Controller
@RequestMapping("/favorite")
public class FavoriteController {

	@Autowired
	private FavoriteService favorieService;
	
	@PostMapping("/selectDelete")
	public String delete(int[] del){
		
		for(int favo : del) {
			System.out.print("favo: "+favo+" ");
			favorieService.selectDelete(favo);
		}
		System.out.println("정상적으로 삭제되었습니다!");
		
		return "redirect:/favorite/go";
		
	}

	// 찜목록에서 삭제
//		@PostMapping("/delete/{no}") // 삭제하기
//		public String delete(@PathVariable String no, HttpSession session) {
//			System.out.println("zzim delete: "+no);
//			MemberDto member = (MemberDto) session.getAttribute("userinfo");
//			String userid = member.getUserid();
//			favorieService.deleteZzim(no, userid);
//			return "redirect:/index";
//		}
}
