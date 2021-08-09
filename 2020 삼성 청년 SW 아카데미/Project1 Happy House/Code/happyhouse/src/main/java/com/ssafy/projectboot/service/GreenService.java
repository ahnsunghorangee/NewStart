package com.ssafy.projectboot.service;

import java.util.List;

import com.ssafy.projectboot.dto.GreenDto;

public interface GreenService {

	//녹지 리스트 
	public List<GreenDto> selectGreenList();

}
