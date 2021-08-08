package com.skeleton.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.skeleton.dto.MemberDto;
import com.skeleton.service.MemberService;


@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private MemberService memberService;
	
	@GetMapping("/join") // 단순 페이지 이동
	public String mvJoin() {
		// test
		return "/member/join";
	}
	
	// 회원 가입
	@PostMapping("/join")
	public String join_member(@ModelAttribute MemberDto member) {
		int response = memberService.join(member);
		System.out.println("member controller " + response);
		return "index";
	}
}
