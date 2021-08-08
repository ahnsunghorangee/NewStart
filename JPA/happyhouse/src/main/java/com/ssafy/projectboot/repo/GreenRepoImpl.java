package com.ssafy.projectboot.repo;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.projectboot.dto.GreenDto;

@Repository
public class GreenRepoImpl implements GreenRepo{

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List<GreenDto> selectGreenList() {
		return sqlSession.selectList("com.ssafy.projectboot.repo.GreenRepo.selectGreenList");
	}

}
