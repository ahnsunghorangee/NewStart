package com.project.happyhouse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.happyhouse.dao.QnADAO;
import com.project.happyhouse.dto.QnA;

@Service
public class QnAServiceImpl implements QnAService{

	@Autowired
	private QnADAO dao;
	
	@Override
	public List<QnA> retrieveQnA() {
		System.out.println("service selectAll");
		return dao.selectQnA();
	}

	@Override
	public QnA detailQnA(int no) {
		return dao.detailQnA(no);
	}

	@Override
	public boolean writeQnA(QnA qna) {
		return dao.writeQnA(qna) == 1;
	}

	@Override
	public boolean updateQnA(QnA qna) {
		return dao.updateQnA(qna) == 1;
	}

	@Override
	public boolean deleteQnA(int no) {
		return dao.deleteQnA(no) == 1;
	}

	

}