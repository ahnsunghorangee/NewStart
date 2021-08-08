package com.ssafy.projectboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ssafy.projectboot.dto.HouseDealDto;
import com.ssafy.projectboot.dto.HouseDealinfo;
import com.ssafy.projectboot.dto.HouseInfoDto;
import com.ssafy.projectboot.service.HouseDealService;

@Controller
@RequestMapping("/detailinfo")
public class HouseDealDetailController {
	
	@Autowired 
	private HouseDealService housedealService;
	
	// 매물 상세페이지로 이동!
	@RequestMapping(value = "/{no}", method = RequestMethod.GET)
	public String detailHouseDeal(@PathVariable int no, Model model) {
		System.out.println("매물 번호: "+ no);

		/** 매물번호(no)로 특정매물정보 select후 houseinfo와 아파트명,동이 일치하는 정보에서 lat,lng 까지 붙여서 가져오기
		(no, dong, AptName, dealAmount, buildYear, dealYear, dealDay, area, floor, jibun , lat, lng ) */
		HouseDealinfo house = housedealService.getHouseDealinfo(no);
		System.out.println(house);
		model.addAttribute("house", house);
		return "/subway/detail" ; // .jsp로 바로이동
		
	}
}
