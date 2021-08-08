package com.ssafy.projectboot.repo;

import java.util.List;

import com.ssafy.projectboot.dto.NoticeDto;

public interface NoticeRepo {
	// 목록 보기
	public List<NoticeDto> listNotice();
	// 글 가져오기 (글 번호로 가져오기)
	public NoticeDto getNotice(int articleno);
	
	// 글작성
	public void writeNotice(NoticeDto noticeDto);
	// 글수정
	public void modifyNotice(NoticeDto noticeDto);
	// 글삭제
	public void deleteNotice(int articleno);
}
