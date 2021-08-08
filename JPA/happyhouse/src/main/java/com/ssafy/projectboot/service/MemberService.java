package com.ssafy.projectboot.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ssafy.projectboot.dto.MemberDto;

public interface MemberService {
	public MemberDto login(MemberDto memberDto, HttpServletResponse response) throws Exception;
	
	public int joinUser(MemberDto memberDto, HttpServletResponse response) throws Exception;;
	public void modifyUser(MemberDto memberDto);
	public void deleteUser(String userid);
	
	public MemberDto getUser(String userid);
	public List<MemberDto> listUser();
	public MemberDto pwdFind(String userid, String username);
	
	public int idCheck(String userid);
	
	public void selectDelete(String userid);
	
	
	// 아이디 중복 검사
	public void check_id(String userid, HttpServletResponse response) throws Exception;
	// 이메일 중복 검사
	public void check_email(String email, HttpServletResponse response) throws Exception;
	// naver SMTP 인증키 생성
	public String create_key() throws Exception;
	// 이메일 발송
	public void send_mail(MemberDto memberDto, String div) throws Exception;
	// 회원 인증
	public void approval_member(MemberDto memberDto, HttpServletResponse response, HttpServletRequest request) throws Exception;
	// 비밀번호 찾기
	public void find_pw(HttpServletResponse response, MemberDto memberDto) throws Exception;
}
