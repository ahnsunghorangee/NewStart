package com.ssafy.projectboot.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssafy.projectboot.dto.NoticeDto;
import com.ssafy.projectboot.service.NoticeService;
	/** articleno; //공지사항 글번호 
	 * subject; // 글제목 
	 * content; // 글내용
	 * click; // 조회수
	 * regtime; //글쓴날짜 
	 */

@Controller
@RequestMapping("/notice")
public class NoticeController {
	
	@Autowired
	private NoticeService noticeService;
	
	// 리스트 데이터만 전달 
	@GetMapping(value="/", consumes="application/json")
	public @ResponseBody List<NoticeDto> selectAll(){
		System.out.println("전체검색!");
		List<NoticeDto> list = new ArrayList<>();
		list = noticeService.listNotice();
		System.out.println(list.toString());
		return list;
	}
	
	//글수정페이지로 이동시키기
	@RequestMapping(value = "/upform/{articleno}" , method = RequestMethod.GET)//수정폼 보이기
	public String upform(@PathVariable int articleno, Model model) {
//		int articleno = Integer.parseInt(map.get("articleno"));
		System.out.println("글번호: "+articleno);
		NoticeDto notice = noticeService.getNotice(articleno);
		model.addAttribute("notice",notice);
		
		return "/notice/modify"; // 이건바로 .jsp로 이동!!!  <-> redirect 가 컨트롤러 거쳐가는것 !!! 
	}
//	@RequestMapping(value = "/upform" , method = RequestMethod.GET)//수정폼 보이기
//	public String upform(@RequestParam Map<String, String> map, Model model) {
//		int articleno = Integer.parseInt(map.get("articleno"));
//		System.out.println("글번호: "+articleno);
//		NoticeDto notice = noticeService.getNotice(articleno);
//		model.addAttribute("notice",notice);
//		
//		return "/notice/modify"; // 이건바로 .jsp로 이동!!!  <-> redirect 가 컨트롤러 거쳐가는것 !!! 
//	}
	
	//수정완료
	@PostMapping("/modify") // POST방식 (글수정)
	public String modify(NoticeDto noticeDto) {
		noticeService.modifyNotice(noticeDto);
		return "redirect:/notice/list";
	}
	
	@GetMapping(value="/list")
	public String mvlist() {
		return "notice/list"; //list.jsp로 이동
	}
	
	// 글쓰기 페이지로 이동
	@GetMapping(value="/write")
	public String mvwrite() {
		return "notice/write"; // write.jsp 로 이동...
	}
	
	// notice/write 에 POST로 요청했으면 여기롱 ( 글쓰기 완료 )
	@PostMapping(value="/write")
	public String write(NoticeDto noticeDto) {
		System.out.println("여기 진입함 ");
		noticeService.writeNotice(noticeDto);
		System.out.println(noticeDto.toString());
		System.out.println("글이 잘 입력됐어요!  ");
		return "redirect:/notice/list"; // 컨트롤러로 보내기 
	}
	
	//글삭제
	@PostMapping("/delete") // POST방식 (글삭제)
	public String delete(NoticeDto noticeDto) {
		noticeService.deleteNotice(noticeDto.getArticleno());
		return "redirect:/notice/list";
	}
	
	
}
