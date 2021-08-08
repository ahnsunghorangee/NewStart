package com.ssafy.projectboot.repo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.projectboot.dto.FavoriteDto;
import com.ssafy.projectboot.dto.LocationDto;

@Repository
public class FavoriteRepoImpl implements FavoriteRepo{
	
	@Autowired
	private SqlSession sqlSession;

	// loccode 얻어오기  / map에는 { 'sido_name' : 서울특별시, 'gugun_name' : 강남구, 'dong_name' : 대치동 }
	@Override
	public String findLoccode(LocationDto locationDto) {
		return sqlSession.selectOne("com.ssafy.projectboot.repo.FavoriteRepo.findLoccode",locationDto);
	}

	// loccode, userid 를 가지고 Favorite테이블에 추가하기 
	@Override
	public int addFavorite(FavoriteDto favoriteDto) {
		sqlSession.insert("com.ssafy.projectboot.repo.FavoriteRepo.addFavorite", favoriteDto);
		return 1;
	}

	// 관심지역 목록 가져오기 
	@Override
	public List<FavoriteDto> listFavorite(String userid) {
		return sqlSession.selectList("com.ssafy.projectboot.repo.FavoriteRepo.listFavorite", userid);
	}

	// 구 이름 얻어오기
	@Override
	public String findGuname(String gucode) {
		return sqlSession.selectOne("com.ssafy.projectboot.repo.FavoriteRepo.findGuname",gucode);
	}

	// 관심지역 삭제
	@Override
	public int selectDelete(int del) {
		return sqlSession.delete("com.ssafy.projectboot.repo.FavoriteRepo.selectDelete",del);
	}

	// 찜목록에 있는지 조회
	@Override
	public int isZzim(String no, String userid) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("no", no);
		map.put("userid", userid);
		return sqlSession.selectOne("com.ssafy.projectboot.repo.FavoriteRepo.isZzim",map);
	}
	
	// 찜목록에 추가
	@Override
	public Object addZzim(String no, String userid) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("no", no);
		map.put("userid", userid);
		return sqlSession.selectOne("com.ssafy.projectboot.repo.FavoriteRepo.addZzim",map);
	}

	// 찜목록에서 제거
	@Override
	public void deleteZzim(String no, String userid) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("no", no);
		map.put("userid", userid);
		sqlSession.delete("com.ssafy.projectboot.repo.FavoriteRepo.deleteZzim", map);
	}
	
	
	

}
