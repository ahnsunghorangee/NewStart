package com.ssafy.projectboot.repo;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.projectboot.dto.HouseDealinfo;
import com.ssafy.projectboot.dto.SaleImgDto;

@Repository
public class SaleImgRepoImpl implements SaleImgRepo{
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public int getSaleNo() {
		return sqlSession.selectOne("com.ssafy.projectboot.repo.SaleImgRepo.getSaleNo");
	}
	
	@Override
	public List<SaleImgDto> selectAll() {
		return sqlSession.selectList("com.ssafy.projectboot.repo.SaleImgRepo.selectAll");
	}

	@Override
	public void insertImg(SaleImgDto saleImgDto) {
		sqlSession.insert("com.ssafy.projectboot.repo.SaleImgRepo.insertImg", saleImgDto);
	}

}
