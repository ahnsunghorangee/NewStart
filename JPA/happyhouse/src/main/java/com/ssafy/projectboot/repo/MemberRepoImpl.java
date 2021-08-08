package com.ssafy.projectboot.repo;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.projectboot.dto.MemberDto;

@Repository
public class MemberRepoImpl implements MemberRepo{
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public MemberDto login(MemberDto memberDto) {
		return sqlSession.selectOne("com.ssafy.projectboot.repo.MemberRepo.login", memberDto);
	}

	@Override
	public int joinUser(MemberDto memberDto) {
		return sqlSession.insert("com.ssafy.projectboot.repo.MemberRepo.joinUser", memberDto);
	}

	@Override
	public void modifyUser(MemberDto memberDto) {
		sqlSession.update("com.ssafy.projectboot.repo.MemberRepo.modifyUser", memberDto);
	}

	@Override
	public void deleteUser(String userid) {
		sqlSession.delete("com.ssafy.projectboot.repo.MemberRepo.deleteUser", userid);
		
	}

	@Override
	public MemberDto getUser(String userid) {
		return sqlSession.selectOne("com.ssafy.projectboot.repo.MemberRepo.getUser", userid);
	}

	@Override
	public List<MemberDto> listUser() {
		return sqlSession.selectList("com.ssafy.projectboot.repo.MemberRepo.listUser");
	}

	@Override
	public MemberDto pwdFind(String userid, String username) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int idCheck(String userid) {
		return sqlSession.selectOne("com.ssafy.projectboot.repo.MemberRepo.idCheck", userid);
	}

	@Override
	public void selectDelete(String userid) {
		sqlSession.selectOne("com.ssafy.projectboot.repo.MemberRepo.selectDelete", userid);
	}

	@Override
	public int check_id(String userid) throws Exception {
		return sqlSession.selectOne("com.ssafy.projectboot.repo.MemberRepo.check_id", userid);
	}

	@Override
	public int check_email(String email) throws Exception {
		return sqlSession.selectOne("com.ssafy.projectboot.repo.MemberRepo.check_email", email);
	}
	
	// 이메일 인증
	@Override
	public int approval_member(MemberDto memberDto) throws Exception {
		return sqlSession.update("com.ssafy.projectboot.repo.MemberRepo.approval_member", memberDto);
	}
	
	// 비밀번호 변경
	@Override
	public int update_pw(MemberDto memberDto) throws Exception{
		return sqlSession.update("com.ssafy.projectboot.repo.MemberRepo.update_pw", memberDto);
	}
}
