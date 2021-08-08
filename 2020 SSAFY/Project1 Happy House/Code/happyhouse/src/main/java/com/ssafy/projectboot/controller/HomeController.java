package com.ssafy.projectboot.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ssafy.projectboot.dto.NoticeDto;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "index";
	}
	
	@GetMapping("/shop/go")
	public String mvShop(){
		return "/shop/shopmap";
	}
	
	@GetMapping("/favorite/go")
	public String mvShopMap(){
		return "/favorite/favorite";
	}
	
	@GetMapping("/pollution/go")
	public String mvPollution(){
		return "/pollution/pollution";
	}
	
	@GetMapping("/notice/go")
	public String mvNotice(){
		return "/notice/list";
	}
	
	@GetMapping("/member/go")
	public String mvMember(){
		return "/member/search";
	}
	
	@GetMapping("/login/go")
	public String mvLogin(){
		return "/member/login";
	}
	
	@PostMapping("/login/go")
	public String mvLogin2(){
		return "/member/login";
	}
	
	@GetMapping("/qna/go")
	public String mvQnA(){
		return "/qna/qna";
	}
	
	@GetMapping("/subway/go")
	public String mvSubway() {
		return "/subway/subway";
	}
	
	@GetMapping("/cluster/go")
	public String mvCluster() {
		return "/maincluster/cluster";
	}
	@GetMapping("sale/go")
	public String mvSale() {
		return "sale/sale";
	}
	
	
	@GetMapping("jusoPopup/go")
	public String mvJusoPopup() { // sale.jsp -> jusoPopup.jsp
		return "sale/jusoPopup";
	}
	@PostMapping("jusoPopup/go")
	public String mvJusoPopup2() { // jusoPopup.jsp.jsp -> sale.jsp
		System.out.println("히히");
		return "sale/jusoPopup";
	}
	
	@GetMapping("sale/register")
	public String mvSaleRegister(){
		return "sale/register";
	}
	@GetMapping("sale/list")
	public String mvSaleList(){
		return "sale/list";
	}
	@GetMapping("sale/recent")
	public String mvSaleRecent(){
		return "sale/recent";
	}
}
