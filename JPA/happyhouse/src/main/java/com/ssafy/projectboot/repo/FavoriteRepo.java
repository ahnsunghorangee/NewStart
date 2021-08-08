package com.ssafy.projectboot.repo;

import java.util.List;
import java.util.Map;

import com.ssafy.projectboot.dto.FavoriteDto;
import com.ssafy.projectboot.dto.LocationDto;

public interface FavoriteRepo {

	// loccode 얻어오기  / map에는 { 'si' : 서울특별시, 'gu' : 강남구, 'dong' : 대치동 }
	public String findLoccode(LocationDto locationDto);

	// loccode, userid 를 가지고 Favorite테이블에 추가하기
	public int addFavorite(FavoriteDto favoriteDto);
	
	// 관심지역 목록 가져오기
	public List<FavoriteDto> listFavorite(String userid);
	
	// 구 이름 얻기
	public String findGuname(String gucode);

	// 관심지역 삭제
	public int selectDelete(int del);

	// 찜목록에 있는지 조회
	public int isZzim(String no, String userid);

	// 찜목록에 추가
	public Object addZzim(String no, String userid);

	// 찜목록에서 제거
	public void deleteZzim(String no, String userid);
}
