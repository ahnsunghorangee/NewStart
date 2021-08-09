package com.ssafy.projectboot.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.projectboot.dto.GreenDto;
import com.ssafy.projectboot.dto.PollutionDto;
import com.ssafy.projectboot.service.PollutionService;

@RestController
@RequestMapping("/pollution")
public class PollutionController {
	
	@Autowired
	private PollutionService pollutionService;
	
	// 전체검색
	@GetMapping(value="/", consumes="application/json")
	public List<PollutionDto> selectAll(){
		System.out.println("오염시설 전체검색!");
		List<PollutionDto> list = new ArrayList<>();
		list = pollutionService.selectAll();
		return list;
	}
	
	// 검색하기
	@GetMapping(value="/{word}", consumes="application/json")
	public List<PollutionDto> search(@PathVariable String word){
		System.out.println("word: "+word);
		List<PollutionDto> list = new ArrayList<>();
		list = pollutionService.search(word);
		System.out.println("shop selectAll:"+list.toString());
		return list;
	}
	
	// 녹지 위치의 위도,경도 
	@GetMapping("/list")
	public List<PollutionDto> selectPollutionList(@RequestParam Map<String,String> map){
		List<PollutionDto> list = new ArrayList<>();
		list = pollutionService.selectAll();
		return list;
	}
}
