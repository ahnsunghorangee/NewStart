package com.ssafy.projectboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.projectboot.dto.ShopDto;
import com.ssafy.projectboot.repo.ShopRepo;

@Service
public class ShopServiceImpl implements ShopService{
	@Autowired
	private ShopRepo shopRepo;

	@Override
	public List<ShopDto> selectAll() {
		return shopRepo.selectAll();
	}
	
	@Override
	public List<ShopDto> search(String word) {
		return shopRepo.search(word);
	}

	@Override
	public List<ShopDto> selectLarge() {
		return shopRepo.selectLarge();
	}

	@Override
	public List<ShopDto> selectMiddle(String largecode) {
		return shopRepo.selectMiddle(largecode);
	}

	@Override
	public List<ShopDto> selectSmall(String middlecode) {
		return shopRepo.selectSmall(middlecode);
	}

	@Override
	public List<ShopDto> selectShopInfo(String no) {
		return shopRepo.selectShopInfo(no);
	}
	
}
