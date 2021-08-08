package com.ssafy.projectboot.repo;

import java.util.List;

import com.ssafy.projectboot.dto.HouseDealinfo;
import com.ssafy.projectboot.dto.SaleImgDto;

public interface SaleImgRepo {
	public int getSaleNo(); // 마지막 매물 등록 번호 가져오기
	
	public List<SaleImgDto> selectAll();
	public void insertImg(SaleImgDto saleImgDto); 
	
}
