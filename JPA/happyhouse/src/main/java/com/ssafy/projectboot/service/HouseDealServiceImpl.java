package com.ssafy.projectboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.projectboot.dto.HouseDealDto;
import com.ssafy.projectboot.dto.HouseDealinfo;
import com.ssafy.projectboot.dto.HouseInfoDto;
import com.ssafy.projectboot.dto.LocationDto;
import com.ssafy.projectboot.repo.HouseDealRepo;

@Service
public class HouseDealServiceImpl implements HouseDealService{

	@Autowired
	private HouseDealRepo houseDealRepo;
	
	//전체 매매거래 목록 가져오기
	@Override
	public List<HouseDealinfo> listHouseDealinfo() {
		return houseDealRepo.listHouseDealinfo();
	}

	//전체 집정보 목록 가져오기
	@Override
	public List<HouseInfoDto> listHouseinfo() {
		return houseDealRepo.listHouseinfo();
	}

	// 아파트명으로 매물정보 가져오기
	@Override
	public List<HouseDealDto> listHouseDeal(String aptname, String dong, String pageNo, String spp) {
		return houseDealRepo.listHouseDeal(aptname, dong, pageNo, spp);
	}

	// 아파트명으로 매물정보 검색시 총 갯수 얻기.
	@Override
	public int dealCount(String aptname, String dong) {
		return houseDealRepo.dealCount(aptname, dong);
	}

	// 코드 가져오기
	@Override
	public List<LocationDto> listCode() {
		return houseDealRepo.listCode();
	}

	// 매물번호로 특정매물 가져오기
	@Override
	public HouseDealinfo getHouseDealinfo(int no) {
		return houseDealRepo.getHouseDealinfo(no);
	}

	// 매물등록페이지 -> 매물 등록하기
	@Override
	public void insertHouseDeal(HouseDealDto houseDealDto) {
		houseDealRepo.insertHouseDeal(houseDealDto);
	}

	// 매물등록페이지 -> 내가 등록한 매물 불러오기
	@Override
	public List<HouseDealDto> searchUserRegister(String userid) {
		return houseDealRepo.searchUserRegister(userid);
	}
	
	// 매물등록페이지 -> 상세정보
	@Override
	public HouseDealDto getHouseDeal(int saleno) {
		return houseDealRepo.getHouseDeal(saleno);
	}

	// 매물등록페이지 -> 업데이트
	@Override
	public void updateHouseDeal(HouseDealDto housedealDto, int saleno) {
		houseDealRepo.updateHouseDeal(housedealDto, saleno);
	}

	// 매물등록페이지 -> 삭제
	@Override
	public void deleteHouseDeal(int saleno) {
		houseDealRepo.deleteHouseDeal(saleno);
	}

	@Override
	public void selectDelete(int saleno) {
		houseDealRepo.selectDelete(saleno);
	}
	
	// 매물등록페이지 -> 최근 등록 매물 불러오기
	@Override
	public List<HouseDealDto> getRecentList() {
		return houseDealRepo.getRecentList();
	}
}
