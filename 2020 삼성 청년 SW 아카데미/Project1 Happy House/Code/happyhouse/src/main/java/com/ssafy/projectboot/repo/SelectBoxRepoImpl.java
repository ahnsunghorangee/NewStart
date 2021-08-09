package com.ssafy.projectboot.repo;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.projectboot.dto.HouseDealDto;
import com.ssafy.projectboot.dto.HouseInfoDto;
import com.ssafy.projectboot.dto.SiDoCodeDto;

@Repository
public class SelectBoxRepoImpl implements SelectBoxRepo{
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<SiDoCodeDto> selectSido() {
		return sqlSession.selectList("com.ssafy.projectboot.repo.SelectBoxRepo.selectSido");
	}

	@Override
	public List<SiDoCodeDto> selectGugun(String sido) {
		return sqlSession.selectList("com.ssafy.projectboot.repo.SelectBoxRepo.selectGugun",sido);
	}

	@Override
	public List<HouseInfoDto> selectDong(String gugun) {
		return sqlSession.selectList("com.ssafy.projectboot.repo.SelectBoxRepo.selectDong",gugun);
	}

	@Override
	public List<HouseDealDto> selectApt(String dong) {
		return sqlSession.selectList("com.ssafy.projectboot.repo.SelectBoxRepo.selectApt",dong);
	}

	@Override
	public List<HouseInfoDto> selectHouseInfo(String dong) {
		return sqlSession.selectList("com.ssafy.projectboot.repo.SelectBoxRepo.selectHouseInfo",dong);
	}

	//구,동으로 매물 검색 + 페이징
	@Override
	public List<HouseDealDto> selectAptbyGudong(String gugun, String dong, int pageNo, int spp) {
		
		HashMap<String, String> map = new HashMap<String, String>();
		int pagenum = pageNo;
		int sppnum = spp;
		int start = (pagenum-1)*sppnum;
		RowBounds row = new RowBounds(start, sppnum);
		map.put("gugun", gugun);
		map.put("dong", dong);
		return sqlSession.selectList("com.ssafy.projectboot.repo.SelectBoxRepo.selectAptbyGudong",map,row);
	}

	// 구,동으로 매물검색해서 총갯수 리턴
	@Override
	public int dealCount(String gugun, String dong) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("gugun", gugun);
		map.put("dong", dong);
		return sqlSession.selectOne("com.ssafy.projectboot.repo.SelectBoxRepo.dealCount", map);
	}

}
