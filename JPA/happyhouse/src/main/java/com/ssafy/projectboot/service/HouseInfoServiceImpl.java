package com.ssafy.projectboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.projectboot.dto.HouseInfoDto;
import com.ssafy.projectboot.repo.HouseInfoRepo;

@Service
public class HouseInfoServiceImpl implements HouseInfoService{

	@Autowired
	private HouseInfoRepo houseinfoRepo;
	
	@Override
	public void insertHouseInfo(HouseInfoDto houseinfo) {
		houseinfoRepo.insertHouseInfo(houseinfo);
	}
	
}
