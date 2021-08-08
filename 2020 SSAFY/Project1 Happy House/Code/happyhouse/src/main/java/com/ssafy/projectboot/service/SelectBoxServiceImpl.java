package com.ssafy.projectboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.projectboot.dto.HouseDealDto;
import com.ssafy.projectboot.dto.HouseInfoDto;
import com.ssafy.projectboot.dto.SiDoCodeDto;
import com.ssafy.projectboot.repo.SelectBoxRepo;

@Service
public class SelectBoxServiceImpl implements SelectBoxService{

	@Autowired
	private SelectBoxRepo selectBoxRepo;
	
	@Override
	public List<SiDoCodeDto> selectSido() {
		return selectBoxRepo.selectSido();
	}

	@Override
	public List<SiDoCodeDto> selectGugun(String sido) {
		return selectBoxRepo.selectGugun(sido);
	}

	@Override
	public List<HouseInfoDto> selectDong(String gugun) {
		return selectBoxRepo.selectDong(gugun);
	}

	@Override
	public List<HouseDealDto> selectApt(String dong) {
		return selectBoxRepo.selectApt(dong);
	}

	@Override
	public List<HouseInfoDto> selectHouseInfo(String dong) {
		return selectBoxRepo.selectHouseInfo(dong);
	}

	//구,동으로 매물 검색 + 페이징
	@Override
	public List<HouseDealDto> selectAptbyGudong(String gugun, String dong, int pageNo, int spp) {
		return selectBoxRepo.selectAptbyGudong(gugun, dong, pageNo, spp);
	}

	// 구,동으로 매물검색해서 총갯수 리턴
	@Override
	public int dealCount(String gugun, String dong) {
		return selectBoxRepo.dealCount(gugun, dong);
	}

}
