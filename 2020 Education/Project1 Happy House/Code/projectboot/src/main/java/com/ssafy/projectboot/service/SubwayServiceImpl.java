package com.project.projectboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.projectboot.dto.SubwayDto;
import com.project.projectboot.repo.SubwayRepo;

@Service
public class SubwayServiceImpl implements SubwayService{

	@Autowired
	private SubwayRepo subwayRepo;
	
	// 지하철 정보 가져오기
	@Override
	public List<SubwayDto> listSubway() {
		return subwayRepo.listSubway();
	}

}
