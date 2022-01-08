package com.lgcns.mission.pension;

public class Customer {

	private String name;
	private String phone;
	
	public Customer(String name, String phone) {
		super();
		this.name = name;
		this.phone = phone;
	}

	@Override
	public String toString() {
		return " 현재 투숙객 - [이름] " + name + " [전화번호] " + phone;
	}
}
