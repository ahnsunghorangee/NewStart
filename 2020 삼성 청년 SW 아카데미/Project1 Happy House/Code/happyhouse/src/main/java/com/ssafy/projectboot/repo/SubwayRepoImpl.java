package com.ssafy.projectboot.repo;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.projectboot.dto.SubwayDto;

@Repository
public class SubwayRepoImpl implements SubwayRepo{

	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<SubwayDto> listSubway() {
		return sqlSession.selectList("com.ssafy.projectboot.repo.SubwayRepo.listSubway");
	}
}
