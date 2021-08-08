package com.ssafy.projectboot.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.projectboot.dto.SaleImgDto;
import com.ssafy.projectboot.repo.SaleImgRepo;

@Service
public class SaleServiceImpl implements SaleService{
	
	@Autowired
	private SaleImgRepo saleImgRepo;
	
	private static String UPLOADED_FOLDER = "./upload/";
            
	// 파일 저장
	@Override
	public void saveUploadFiles(List<MultipartFile> files, String userid, int saleno, HttpServletRequest request) throws IOException{
		
//        System.out.println(realPath);
//		String folderPath = realPath+"/aptImg/";
		
		// 파일명 - 싱글파일업로드와 다르게 멀티파일업로드는 HEADER로 넘어옴 
//        String name = request.getHeader("file-name");
        // 확장자
//        String ext = name.substring(name.lastIndexOf(".")+1);
        // 파일 기본경로
//        String defaultPath = request.getServletContext().getRealPath("/aptimg/");
        // 파일 기본경로 _ 상세경로
//        String folderPath = defaultPath + File.separator;
        String defaultPath  = "C:\\SSAFY\\spring_01\\projectboot\\src\\main\\resources\\static\\aptimg\\";
//		  String defaultPath = "/Users/hyerin/git/happyhouse6_2/src/main/resources/static/aptimg/";
        
		File makeFolder = new File(defaultPath);
		if(!makeFolder.exists()) {
            makeFolder.mkdirs(); 
        } 
		for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue;
            }
            byte[] bytes = file.getBytes();
            Path path = Paths.get(defaultPath+file.getOriginalFilename());
            Files.write(path, bytes);
        }
		
	}

	@Override
	public List<SaleImgDto> selectAll() {
		return saleImgRepo.selectAll();
	}
	
	@Override
	public int getSaleNo() { // DB에서 마지막 번호 가져오기 (매물 등록 번호)
		return saleImgRepo.getSaleNo();
	}
	

	@Override
	public void insertImg(SaleImgDto saleImgDto) {
		saleImgRepo.insertImg(saleImgDto);
	}
	

}
