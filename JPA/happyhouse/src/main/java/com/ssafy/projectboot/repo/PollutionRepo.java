package com.ssafy.projectboot.repo;

import java.util.List;

import com.ssafy.projectboot.dto.PollutionDto;

public interface PollutionRepo {
	public List<PollutionDto> selectAll();
	public List<PollutionDto> search(String word);
}
