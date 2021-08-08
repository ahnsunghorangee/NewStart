package com.ssafy.projectboot.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.projectboot.dto.FavoriteDto;
import com.ssafy.projectboot.dto.LocationDto;
import com.ssafy.projectboot.repo.FavoriteRepo;

@Service
public class FavoriteServiceImpl implements FavoriteService{
	
	@Autowired
	private FavoriteRepo favoriteRepo;

	// loccode 얻어오기  / map에는 { 'sido_name' : 서울특별시, 'gugun_name' : 강남구, 'dong_name' : 대치동 }
	@Override
	public String findLoccode(LocationDto locationDto) {
		return favoriteRepo.findLoccode(locationDto);
	}
	
	// loccode, userid 를 가지고 Favorite테이블에 추가하기
	@Override
	public int addFavorite(FavoriteDto favoriteDto) {
		return favoriteRepo.addFavorite(favoriteDto);
	}

	// 관심지역 목록 가져오기 
	@Override
	public List<FavoriteDto> listFavorite(String userid) {
		return favoriteRepo.listFavorite(userid) ;
	}

	// 구 이름 얻기 
	@Override
	public String findGuname(String gucode) {
		return favoriteRepo.findGuname(gucode);
	}

	// 관심지역 삭제
	@Override
	public int selectDelete(int del) {
		return favoriteRepo.selectDelete(del);
	}
 
	// 찜목록에 있는지 조회
	@Override
	public int isZzim(String no, String userid) {
		return favoriteRepo.isZzim(no,userid);
	}

	// 찜목록에 추가
	@Override
	public void addZzim(String no, String userid) {
		favoriteRepo.addZzim(no,userid);
	}

    // 찜목록에서 제거 
	@Override
	public void deleteZzim(String no, String userid) {
		favoriteRepo.deleteZzim(no,userid);
	}

	
	
}
