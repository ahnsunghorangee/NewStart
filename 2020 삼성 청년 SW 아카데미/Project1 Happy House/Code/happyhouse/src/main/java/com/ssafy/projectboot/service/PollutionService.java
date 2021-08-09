package com.ssafy.projectboot.service;

import java.util.List;

import com.ssafy.projectboot.dto.PollutionDto;

public interface PollutionService {
	public List<PollutionDto> selectAll();
	public List<PollutionDto> search(String word);
}
