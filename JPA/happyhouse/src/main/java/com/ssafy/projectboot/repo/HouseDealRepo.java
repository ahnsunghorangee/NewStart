package com.ssafy.projectboot.repo;

import java.util.List;

import com.ssafy.projectboot.dto.HouseDealDto;
import com.ssafy.projectboot.dto.HouseDealinfo;
import com.ssafy.projectboot.dto.HouseInfoDto;
import com.ssafy.projectboot.dto.LocationDto;

public interface HouseDealRepo {

	//전체 매매거래 목록 가져오기
	public List<HouseDealinfo> listHouseDealinfo();

	//전체 집정보 목록 가져오기
	public List<HouseInfoDto> listHouseinfo();
	
	// 매물등록페이지 -> 매물 등록하기
	public void insertHouseDeal(HouseDealDto houseDealDto);

	// 아파트명+동 으로 매물정보 가져오기
	public List<HouseDealDto> listHouseDeal(String aptname, String dong, String pageNo, String spp);

	// 아파트명+동 으로 매물정보 검색시 총 갯수 얻기.
	public int dealCount(String aptname, String dong);

	// 코드 가져오기
	public List<LocationDto> listCode();

	// 매물번호로 특정매물 가져오기
	public HouseDealinfo getHouseDealinfo(int no);

	// 매물등록페이지 -> 내가 등록한 매물 불러오기
	public List<HouseDealDto> searchUserRegister(String userid);
	
	// 매물등록페이지 -> 상세정보
	public HouseDealDto getHouseDeal(int saleno);
	// 매물등록페이지 -> 업데이트
	public void updateHouseDeal(HouseDealDto housedealDto, int saleno);
	// 매물등록페이지 -> 삭제
	public void deleteHouseDeal(int saleno);
	// 매물등록페이지 -> 선택삭제
	public void selectDelete(int saleno);
	// 매물등록페이지 -> 최근 등록 매물 불러오기
	public List<HouseDealDto> getRecentList();
}
