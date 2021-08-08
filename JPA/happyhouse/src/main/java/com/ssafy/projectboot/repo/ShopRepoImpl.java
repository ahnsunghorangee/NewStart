package com.ssafy.projectboot.repo;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.projectboot.dto.ShopDto;

@Repository
public class ShopRepoImpl implements ShopRepo{
	
	@Autowired 
	private SqlSession sqlSession;
	
	@Override
	public List<ShopDto> selectAll() {
		return sqlSession.selectList("com.ssafy.projectboot.repo.ShopRepo.selectAll");
	}

	@Override
	public List<ShopDto> search(String word) {
		return sqlSession.selectList("com.ssafy.projectboot.repo.ShopRepo.search", word);
	}

	@Override
	public List<ShopDto> selectLarge() {
		return sqlSession.selectList("com.ssafy.projectboot.repo.ShopRepo.selectLarge");
	}

	@Override
	public List<ShopDto> selectMiddle(String largecode) {
		return sqlSession.selectList("com.ssafy.projectboot.repo.ShopRepo.selectMiddle", largecode);
	}

	@Override
	public List<ShopDto> selectSmall(String middlecode) {
		return sqlSession.selectList("com.ssafy.projectboot.repo.ShopRepo.selectSmall", middlecode);
	}

	@Override
	public List<ShopDto> selectShopInfo(String no) {
		return sqlSession.selectList("com.ssafy.projectboot.repo.ShopRepo.selectShopInfo", no);
	}

}
