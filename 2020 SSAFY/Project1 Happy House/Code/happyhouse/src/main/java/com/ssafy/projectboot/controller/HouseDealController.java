package com.ssafy.projectboot.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.projectboot.dto.HouseDealDto;
import com.ssafy.projectboot.dto.HouseDealinfo;
import com.ssafy.projectboot.dto.HouseInfoDto;
import com.ssafy.projectboot.dto.LocationDto;
import com.ssafy.projectboot.service.HouseDealService;
import com.ssafy.projectboot.service.SaleService;

@RestController
@RequestMapping("/dealinfo")
public class HouseDealController {
	
	@Autowired
	private HouseDealService housedealService;
	@Autowired
	private SaleService saleService;
	
	
	//전체 매매거래 목록 가져오기 (+ lat, lng)
	@GetMapping("/list")
	public List<HouseDealinfo> list(){
		System.out.println("HouseDealinfo 여기 들어왔옹~");
		return housedealService.listHouseDealinfo();
	}
	
	// 집정보만 가져오기 
	@GetMapping("/list2")
	public List<HouseInfoDto> infolist(){
		System.out.println("HouseInfo 여기 들옴! ");
		return housedealService.listHouseinfo();
	}
	
	// 아파트명+동 으로 매물정보 가져오기 + 페이징처리 
	@GetMapping("/dealbyAptname")
	public List<HouseDealDto> deallist(@RequestParam Map<String, String> map){
		String aptname = map.get("aptname");
		String pageNo = map.get("pageNo");
		String dong = map.get("dong");
		String spp = map.get("spp");
		return housedealService.listHouseDeal(aptname, dong, pageNo, spp);
	}
	
	// 코드 가져오기
	@GetMapping("/code")
	public List<LocationDto> codelist(){
		return housedealService.listCode();
	}
	
	// 아파트명+동 으로 매물정보 검색시 총 갯수 얻기.
	@GetMapping("/dealCount")
	public int dealCount(@RequestParam Map<String, String> map) {
		String aptname = map.get("aptname");
		String dong = map.get("dong");
		return housedealService.dealCount(aptname, dong);
	}
	
	// 매물등록페이지 -> 내가 등록한 매물 불러오기
	@GetMapping(value="/searchUserRegister/{userid}", consumes="application/json")
	public List<HouseDealDto> searchUserRegister(@PathVariable String userid){
		System.out.println("userid: "+userid);
		List<HouseDealDto> list = new ArrayList<>();
		list = housedealService.searchUserRegister(userid);
		System.out.println(list.toString());
		return list;
	}
	
	// 매물등록페이지 -> 매물정보 상세정보
	@GetMapping(value="/{saleno}", consumes="application/json")
	public HouseDealDto getSaleno(@PathVariable int saleno) {
		return housedealService.getHouseDeal(saleno);
	}
	// 매물등록페이지 -> 매물정보 업데이트
	@PutMapping(value="/{saleno}", consumes="application/json")
	public void update(@PathVariable int saleno, @RequestBody HouseDealDto housedealDto) {
		System.out.println("update "+housedealDto.toString());
		housedealService.updateHouseDeal(housedealDto, saleno);
	}
	// 매물등록페이지 -> 매물정보 삭제
	@DeleteMapping(value="/{saleno}", consumes="application/json")
	public void delete(@PathVariable int saleno) {
		housedealService.deleteHouseDeal(saleno);
	}
	// 매물등록페이지 -> 최근 등록 매물 불러오기
	@GetMapping(value="/recentList", consumes="application/json")
	public List<HouseDealDto> recentList(){
		List<HouseDealDto> list = new ArrayList<>();
		list = housedealService.getRecentList();
		System.out.println("들어옴?: " + list.toString());
		return list;
	}
}
