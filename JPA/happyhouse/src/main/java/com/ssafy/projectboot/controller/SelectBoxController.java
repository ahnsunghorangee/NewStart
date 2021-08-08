package com.ssafy.projectboot.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.projectboot.dto.HouseDealDto;
import com.ssafy.projectboot.dto.HouseInfoDto;
import com.ssafy.projectboot.dto.SiDoCodeDto;
import com.ssafy.projectboot.service.SelectBoxService;

@RestController
@RequestMapping("/selectbox")
public class SelectBoxController {

	@Autowired
	private SelectBoxService selectBoxService;
	
	
	// ready: 도/광역시 리스트 출력
	@GetMapping("/sido")
	public List<SiDoCodeDto> selectSido(){
		List<SiDoCodeDto> list = new ArrayList<>();
		list = selectBoxService.selectSido();
		return list;
	}
	
	// 도/광역시 결정되면 구/군 검색
	@GetMapping("/gugun")
	public List<SiDoCodeDto> selectGugun(@RequestParam Map<String,String> map){
		List<SiDoCodeDto> list = new ArrayList<>();
		list = selectBoxService.selectGugun(map.get("sido"));
		return list;
	}
	@GetMapping("/dong")
	public List<HouseInfoDto> selectDong(@RequestParam Map<String,String> map){
		List<HouseInfoDto> list = new ArrayList<>();
		list = selectBoxService.selectDong(map.get("gugun"));
		return list;
	}
	
	// 거래내역 검색
	@GetMapping("/apt")
	public List<HouseDealDto> selectApt(@RequestParam Map<String,String> map){
		List<HouseDealDto> list = new ArrayList<>();
		list = selectBoxService.selectApt(map.get("dong"));
		return list;
	}
	
	//구,동으로 매물 검색 + 페이징
	@GetMapping("/aptbygudong")
	public List<HouseDealDto> selectAptbyGudong(@RequestParam Map<String,String> map){
		String gugun = map.get("gugun");
		String dong = map.get("dong");
		int pageNo = Integer.parseInt(map.get("pageNo"));
		int spp = Integer.parseInt(map.get("spp"));
		List<HouseDealDto> list = selectBoxService.selectAptbyGudong(gugun, dong, pageNo, spp);
		return list;
	}
	
	// 구,동으로 매물검색해서 총갯수 리턴
	@GetMapping("/dealCount")
	public int dealCount(@RequestParam Map<String, String> map) {
		String gugun = map.get("gugun"); //11110
		String dong = map.get("dong"); // 내수동
		System.out.println("컨트롤러 진입. gu:"+gugun+" 동: "+dong);
		int count = selectBoxService.dealCount(gugun, dong);
		System.out.println("총갯수: "+count);
		return count;
	}
	
	// 아파트 경도, 위도 검색
	@GetMapping("/houseinfo")
	public List<HouseInfoDto> selectHouseInfo(@RequestParam Map<String,String> map){
		List<HouseInfoDto> list = new ArrayList<>();
		list = selectBoxService.selectHouseInfo(map.get("dong"));
		
		
		return list;
	}

	
}
