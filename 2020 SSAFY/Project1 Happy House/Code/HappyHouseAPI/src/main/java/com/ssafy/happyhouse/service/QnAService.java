package com.project.happyhouse.service;

import java.util.List;

import com.project.happyhouse.dto.QnA;


public interface QnAService {
	public List<QnA> retrieveQnA();
	public QnA detailQnA(int no);
	public boolean writeQnA(QnA qna);
	public boolean updateQnA(QnA qna);
	public boolean deleteQnA(int no);
}
