package com.ssafy.projectboot.repo;

import java.util.List;

import com.ssafy.projectboot.dto.ShopDto;

public interface ShopRepo {
	
	// 전체업종 목록 검색
	public List<ShopDto> selectAll();
	
	// 업종명으로 검색 
	public List<ShopDto> search(String word);
	
	// 대분류 목록
	public List<ShopDto> selectLarge();
	
	// 중분류 목록
	public List<ShopDto> selectMiddle(String largecode);
	
	// 소분류 목록
	public List<ShopDto> selectSmall(String middlecode);
	
	// 가게 위도,경도 얻어오기
	public List<ShopDto> selectShopInfo(String no);
}
