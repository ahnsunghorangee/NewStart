package com.ssafy.projectboot.repo;

import java.util.List;

import com.ssafy.projectboot.dto.GreenDto;

public interface GreenRepo {

	//녹지 리스트 
	public List<GreenDto> selectGreenList();
}
