package com.ssafy.projectboot.repo;

import java.util.List;

import com.ssafy.projectboot.dto.MemberDto;

public interface MemberRepo {
	public MemberDto login(MemberDto memberDto);
	
	public int joinUser(MemberDto memberDto);
	public void modifyUser(MemberDto memberDto);
	public void deleteUser(String userid);
	
	public MemberDto getUser(String userid);
	public List<MemberDto> listUser();
	public MemberDto pwdFind(String userid, String username);
	
	public int idCheck(String userid);
	
	public void selectDelete(String userid);
	
	
	// 아이디 중복 검사
	public int check_id(String userid) throws Exception;
	// 이메일 중복 검사
	public int check_email(String email) throws Exception;
	// 이메일 인증
	public int approval_member(MemberDto memberDto) throws Exception;
	// 비밀번호 변경
	public int update_pw(MemberDto memberDto) throws Exception;
}