package com.ssafy.projectboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.projectboot.dto.NoticeDto;
import com.ssafy.projectboot.repo.NoticeRepo;

@Service
public class NoticeServiceImpl implements NoticeService{

	@Autowired
	private NoticeRepo noticeRepo;
	
	// 목록 보기
	@Override
	public List<NoticeDto> listNotice() {
		return noticeRepo.listNotice();
	}

	// 글하나 가져오기 
	@Override
	public NoticeDto getNotice(int articleno) {
		return noticeRepo.getNotice(articleno);
	}
	
	// 글쓰기 
	@Override
	public void writeNotice(NoticeDto noticeDto) {
		noticeRepo.writeNotice(noticeDto);
	}

	//글수정
	@Override
	public void modifyNotice(NoticeDto noticeDto) {
		noticeRepo.modifyNotice(noticeDto);
	}

	//글삭제
	@Override
	public void deleteNotice(int articleno) {
		noticeRepo.deleteNotice(articleno);
	}

}
