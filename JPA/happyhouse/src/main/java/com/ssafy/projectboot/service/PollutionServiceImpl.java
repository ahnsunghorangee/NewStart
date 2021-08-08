package com.ssafy.projectboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.projectboot.dto.PollutionDto;
import com.ssafy.projectboot.repo.PollutionRepo;

@Service
public class PollutionServiceImpl implements PollutionService{

	@Autowired
	private PollutionRepo pollutionRepo;
	
	@Override
	public List<PollutionDto> selectAll() {
		return pollutionRepo.selectAll();
	}

	@Override
	public List<PollutionDto> search(String word) {
		return pollutionRepo.search(word);
	}

}
