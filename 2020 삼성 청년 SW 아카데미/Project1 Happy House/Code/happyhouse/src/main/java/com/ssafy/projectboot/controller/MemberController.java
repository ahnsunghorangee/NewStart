package com.ssafy.projectboot.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ssafy.projectboot.dto.MemberDto;
import com.ssafy.projectboot.service.MemberService;

/*
 * 목록: /book/				GET
 * 검색: /book/(isbn명)		GET(얘네는 주소가 다르니간 상관없어)
 * 등록: /book/				Post
 * 수정: /book/(isbn명)		put / get
 * 삭제: /book/(isbn명)		delete / post
 * -> 똑같은 주소가 많아, (http프로토콜로는 불가능) 그래서 REST사용
 */

@Controller
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@PostMapping("/login")
	public String login(@ModelAttribute MemberDto memberDto, HttpSession session, HttpServletResponse response) throws Exception { //id, pwd만 들어있는 dto / 스프링은 HttpSession만 호출하면 알아서 만들어준다.
		memberDto = memberService.login(memberDto, response);
		session.setAttribute("userinfo", memberDto); // userinfo라는 이름으로 memberDto를 담아라
		return "index";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "index";
	}
	
	@GetMapping("/join") // 단순 페이지 이동
	public String mvJoin() {
		return "/member/join";
	}
	
	@GetMapping("/findpwd") // 단순 페이지 이동
	public String mvFindPwd() {
		return "/member/findpwd";
	}
	
	/*
	@PostMapping("/join") // 회원가입 정보 전달 + 페이지 이동
	public String join(MemberDto memberDto) {
		System.out.println(memberDto.toString());
		memberService.joinUser(memberDto);
		return "index";
	}
	*/
	// 회원 가입
	@PostMapping("/join")
	public String join_member(@ModelAttribute MemberDto member, RedirectAttributes rttr, HttpServletResponse response) throws Exception{
		System.out.println("join; " +  member.toString());
		member.setAddress(member.getRoadFullAddr() + " " + member.getAddrDetail());
		member.setEmail(member.getEmail()+"@"+member.getEmaildomain());
		rttr.addFlashAttribute("result", memberService.joinUser(member, response));
		
		return "index";
	}
	
	@GetMapping("/confirm") // 마이페이지로 이동
	public String mvMyPage() {
		return "/member/confirm";
	}
	
	@GetMapping("/search") // 마이페이지로 이동
	public String mvSearch() {
		return "/member/search";
	}
	
	@GetMapping("/modify") // 수정페이지로 이동
	public String mvModify() {
		return "/member/modify";
	}
	
	
	
	

	
	@GetMapping(value="/selectAll", consumes="application/json")
	public @ResponseBody List<MemberDto> selectAll() {
		List<MemberDto> list = new ArrayList<>();
		list = memberService.listUser();
		return list;
	}
	
	@RequestMapping("/idcheck.test")
	public @ResponseBody String idCheck(@RequestParam("sid") String userid) {
		int idcount = memberService.idCheck(userid);
		return idcount + "," + userid;
	}
	
	
	// 마이페이지 수정
	@GetMapping("/modify/{userid}") // 수정
	public String modify(@PathVariable String userid, MemberDto memberDto, HttpSession session) {
		
		memberService.modifyUser(memberDto);
		session.setAttribute("userinfo", memberDto);
		return "redirect:/"; // 질문: mvMyPAge 함수 어떻게 짜는게 효율적인지
	}
	
	// 마이페이지 삭제
	@PostMapping("/delete/{userid}") // 삭제하기
	public String delete(@PathVariable String userid, HttpSession session) {
		System.out.println("member delete: "+userid);
		memberService.deleteUser(userid);
		session.invalidate();
		return "redirect:/";
	}
	
	
	// delete 선택 삭제
	@PostMapping("/selectDelete")
	public String delete(String[] del,HttpSession session){
		MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
		if(memberDto == null) {
			return "redirect:/index";
		}else {
		for(String userid : del) {
			memberService.selectDelete(userid);
		}
		return "redirect:/member/search";
		}
	}

	
	
	
	
	
	// 아이디 중복 검사(AJAX)
	@RequestMapping(value = "/check_id.do", method = RequestMethod.POST)
	public void check_id(@RequestParam("userid") String userid, HttpServletResponse response) throws Exception{
		memberService.check_id(userid, response);
	}
	
	// 이메일 중복 검사(AJAX)
	@RequestMapping(value = "/check_email.do", method = RequestMethod.POST)
	public void check_email(@RequestParam("email") String email, HttpServletResponse response) throws Exception{
		memberService.check_email(email, response);
	}
	// 회원 인증
	@PostMapping(value="/approval_member.do")
	public void approval_member(@ModelAttribute MemberDto memberDto, HttpServletResponse response, HttpServletRequest request) throws Exception{
		memberService.approval_member(memberDto, response, request);
	}
	// 비밀번호 찾기
	@PostMapping("/find_pw.do")
	public void find_pw(@ModelAttribute MemberDto memberDto, HttpServletResponse response) throws Exception{
		memberService.find_pw(response, memberDto);
	}
	
}