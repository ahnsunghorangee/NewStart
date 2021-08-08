package com.ssafy.projectboot.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.projectboot.dto.HouseDealDto;
import com.ssafy.projectboot.dto.HouseDealinfo;
import com.ssafy.projectboot.dto.HouseInfoDto;
import com.ssafy.projectboot.dto.MemberDto;
import com.ssafy.projectboot.dto.SaleImgDto;
import com.ssafy.projectboot.service.HouseDealService;
import com.ssafy.projectboot.service.SaleService;

@RestController
@RequestMapping("/sale")
public class SaleRestController {
	
	static int currentSaleNo;
	@Autowired
	private SaleService saleService;
	
	
	/**
	 * 멀티파일 업로드
	 */
	@PostMapping("/upload/multi")
    public ResponseEntity<?> uploadFileMulti(
            @RequestParam("extraField") String extraField,
            @RequestParam("customField") String customField,
            @RequestParam("files") MultipartFile[] uploadfiles,
            HttpSession session,
            HttpServletRequest request) {
		
		MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
		
        // Get file name
        String uploadedFileName = Arrays.stream(uploadfiles)
        								.map(x -> x.getOriginalFilename())
        								.filter(x -> !StringUtils.isEmpty(x))
        								.collect(Collectors.joining(" , "));

        if (StringUtils.isEmpty(uploadedFileName)) {
            return new ResponseEntity("파일을 선택하세요.", HttpStatus.OK);
        }
        
        int saleno = saleService.getSaleNo();
        currentSaleNo = saleno;
        System.out.println("마지막 번호: "+ saleno);
//        String realPath = request.getSession().getServletContext().getRealPath("/");
        try {
        	saleService.saveUploadFiles(Arrays.asList(uploadfiles),memberDto.getUserid(),saleno,request);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        System.out.println(uploadedFileName);
        
        
        StringTokenizer st = new StringTokenizer(uploadedFileName," , ");
        
        
        while(st.hasMoreElements()) {
        	SaleImgDto saleImgDto = new SaleImgDto();
        	saleImgDto.setUserid(memberDto.getUserid());
        	saleImgDto.setImgname(st.nextToken());
        	saleImgDto.setSaleno(saleno);
        	
        	System.out.println("이미지 삽입:" + saleImgDto.toString());
        	saleService.insertImg(saleImgDto);
        }

        return new ResponseEntity("업로드 성공 - " + uploadedFileName, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/getImage/{imagePath}")
    @ResponseBody
    public byte[] getImage(@PathVariable String imagePath, HttpServletRequest request) throws IOException {
		System.out.println("AAA" + imagePath);
        String rpath = "yourFileStoringDirectoryURL" + imagePath + "a.jpg";
        Path path = Paths.get(rpath);
        byte[] data = Files.readAllBytes(path);
        return data;
}
	
	
	/*
	@GetMapping(value="/selectAllImg", consumes="application/json")
	public @ResponseBody List<SaleImgDto> selectAll2() {
		List<SaleImgDto> list = new ArrayList<>();
		list = saleService.selectAll();
		System.out.println("회원 전체 검색"+list.toString());
		return list;
	}
	*/
}
