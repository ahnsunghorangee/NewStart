package com.ssafy.projectboot.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.projectboot.dto.GreenDto;
import com.ssafy.projectboot.service.GreenService;


@RestController
@RequestMapping("/green")
public class GreenController {

	@Autowired
	private GreenService greenService;
	
	// 녹지 위치의 위도,경도 
	@GetMapping("/list")
	public List<GreenDto> selectGreenList(@RequestParam Map<String,String> map){
		List<GreenDto> list = new ArrayList<>();
		list = greenService.selectGreenList();
		return list;
	}
	
	
}
