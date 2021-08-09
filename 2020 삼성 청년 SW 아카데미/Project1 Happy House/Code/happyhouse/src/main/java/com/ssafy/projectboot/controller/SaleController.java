package com.ssafy.projectboot.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssafy.projectboot.dto.HouseDealDto;
import com.ssafy.projectboot.dto.HouseInfoDto;
import com.ssafy.projectboot.dto.MemberDto;
import com.ssafy.projectboot.dto.PopupDto;
import com.ssafy.projectboot.service.HouseDealService;
import com.ssafy.projectboot.service.HouseInfoService;
import com.ssafy.projectboot.service.SaleService;

@Controller
@RequestMapping("/sale")
public class SaleController {
	@Autowired
	private SaleService saleService;
	@Autowired 
	private HouseDealService housedealService;
	@Autowired
	private HouseInfoService houseinfoService;
	
	// 매물등록페이지 -> 매물 등록하기
	@PostMapping("/register/{userid}")
	public String registerSale(@ModelAttribute PopupDto popupDto, @PathVariable String userid) {
		int saleno = saleService.getSaleNo() - 1;
        System.out.println("마지막 번호: "+ saleno);
        System.out.println("SSS"+popupDto.toString());
        
        
        
		System.out.println("PopupDto: " + popupDto.toString());
		HouseInfoDto houseInfo = new HouseInfoDto();
		houseInfo.setDong(popupDto.getEmdNm());
		houseInfo.setAptName(popupDto.getAddrDetail());
		houseInfo.setCode(popupDto.getAdmCd());
		houseInfo.setBuildYear(popupDto.getBuildYear());
		houseInfo.setJibun(popupDto.getLnbrMnnm());
		houseInfo.setLat(popupDto.getEntX());
		houseInfo.setLng(popupDto.getEntY());
		houseInfo.setImg(saleno);
		System.out.println(houseInfo.toString());
		houseinfoService.insertHouseInfo(houseInfo);
		
		
		HouseDealDto houseDeal = new HouseDealDto();
		houseDeal.setDong(popupDto.getEmdNm()); // 동
		houseDeal.setAptName(popupDto.getAddrDetail()); // 아파트 이름 입력할 때
		houseDeal.setCode(popupDto.getAdmCd()); // 행정구역 코드
		houseDeal.setJibun(popupDto.getLnbrMnnm()); // 지번
		houseDeal.setBuildYear(popupDto.getBuildYear());
		houseDeal.setArea(popupDto.getArea());
		houseDeal.setFloor(popupDto.getFloor());
		houseDeal.setDealAmount(popupDto.getDealAmount());
		// 날짜는 DB에서
		
		
		houseDeal.setSaleno(saleno);
		houseDeal.setUserid(userid);
		System.out.println("housedeal: " + houseDeal.toString());
		housedealService.insertHouseDeal(houseDeal);
		
		
//				saleService.registerSale(houserdealinfo);
		
		return "redirect:/sale/go";
	}
	
	// delete 선택 삭제
	@PostMapping("/selectDelete")
	public String delete(String[] del,HttpSession session){
		MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
		
		for(String saleno : del) {
			System.out.print("del: " +  saleno+" ");
			housedealService.selectDelete(Integer.parseInt(saleno));
		
		}
		return "redirect:/sale/go";
	}
	
	// HouseDealDto 하나 빼오기
	@GetMapping("/getSaleno/{saleno}")
	public String getSaleno(@PathVariable int saleno, Model model) {
		HouseDealDto housedeal = housedealService.getHouseDeal(saleno);
		model.addAttribute("sales", housedeal);
//		SaleImgDto saleimg = saleService.selectAll();
		return "sale/view";
	}
	
	@GetMapping("/modify/{saleno}")
	public String modify(@PathVariable int saleno, HouseDealDto housedealDto) {
		System.out.println("update: " + housedealDto.toString());
		housedealService.updateHouseDeal(housedealDto, saleno);
		return "redirect:/sale/go";
	}
	
	@GetMapping("/delete/{saleno}")
	public String delete(@PathVariable int saleno, HouseDealDto housedealDto) {
		housedealService.deleteHouseDeal(saleno);
		return "redirect:/sale/go";
	}
}
