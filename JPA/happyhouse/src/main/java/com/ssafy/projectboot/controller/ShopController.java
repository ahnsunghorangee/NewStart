package com.ssafy.projectboot.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.projectboot.dto.HouseDealDto;
import com.ssafy.projectboot.dto.HouseInfoDto;
import com.ssafy.projectboot.dto.ShopDto;
import com.ssafy.projectboot.dto.SiDoCodeDto;
import com.ssafy.projectboot.service.ShopService;

@RestController
@RequestMapping("/shop")
public class ShopController {
	
	@Autowired
	private ShopService shopService;
	
	// 전체검색
	@GetMapping(value="/", consumes="application/json")
	public List<ShopDto> selectAll(){
		System.out.println("전체검색!");
		List<ShopDto> list = new ArrayList<>();
		list = shopService.selectAll();
		return list;
	}
	
	// 검색하기
	@GetMapping(value="/{word}", consumes="application/json")
	public List<ShopDto> search(@PathVariable String word){
		System.out.println("word: "+word);
		List<ShopDto> list = new ArrayList<>();
		list = shopService.search(word);
		System.out.println("shop selectAll:"+list.toString());
		return list;
	}
	
	/** 대-중-소분류 */
	
	// ready: 대분류 리스트 출력
		@GetMapping("/large")
		public List<ShopDto> selectLarge(){
			List<ShopDto> list = new ArrayList<>();
			list = shopService.selectLarge();
			System.out.println(list.get(0).toString());
			return list;
		}
		
		// 대분류 결정되면 중분류 검색
		@GetMapping("/middle")
		public List<ShopDto> selectGugun(@RequestParam Map<String,String> map){
			List<ShopDto> list = new ArrayList<>();
			list = shopService.selectMiddle(map.get("largecode"));
			return list;
		}
		
		// 중분류 결정되면 소분류 검색 
		@GetMapping("/small")
		public List<ShopDto> selectDong(@RequestParam Map<String,String> map){
			List<ShopDto> list = new ArrayList<>();
			list = shopService.selectSmall(map.get("middlecode"));
			return list;
		}
		
		
		// 가게 경도, 위도 검색
		@GetMapping("/shopinfo")
		public List<ShopDto> selectShopInfo(@RequestParam Map<String,String> map){
			List<ShopDto> list = new ArrayList<>();
			list = shopService.selectShopInfo(map.get("smallcode"));
			System.out.println(list.get(0).toString());
			return list;
		}
	
}
