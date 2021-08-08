package com.ssafy.projectboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.projectboot.dto.SubwayDto;
import com.ssafy.projectboot.service.SubwayService;

@RestController
@RequestMapping("/subway")
public class SubwayController {
	
	@Autowired
	private SubwayService subwayService;
	
	// 지하철 정보 가져오기
	@GetMapping("/list")
	public List<SubwayDto> subwaylist(){
		return subwayService.listSubway();
	}

}
