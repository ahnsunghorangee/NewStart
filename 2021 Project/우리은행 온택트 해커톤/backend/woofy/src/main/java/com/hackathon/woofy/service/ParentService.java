package com.hackathon.woofy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hackathon.woofy.entity.Parent;
import com.hackathon.woofy.repo.ParentRepo;

@Service
public class ParentService {

	@Autowired ParentRepo parentRepo;
	
	@Transactional
	public Parent saveParent(Parent parent) {
		return parentRepo.save(parent);
	}
	
	@Transactional
	public Parent findParent(String phoneNumber){
		return parentRepo.findByPhoneNumber(phoneNumber);
	}
	
}
