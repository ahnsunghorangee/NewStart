package com.project.happyhouse.dao;

import java.util.List;

import com.project.happyhouse.dto.QnA;

public interface QnADAO { // namespace는 이 interface를 찾기위함
	public List<QnA> selectQnA();
	public QnA detailQnA(int no);
	public int writeQnA(QnA qna);
	public int updateQnA(QnA qna);
	public int deleteQnA(int no);
	
}
