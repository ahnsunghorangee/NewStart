package com.ssafy.projectboot.dto;

public class PollutionDto {
	private String name;
	private String bizcode;
	private String address;
	private String dongcode;
	private String dong;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBizcode() {
		return bizcode;
	}
	public void setBizcode(String bizcode) {
		this.bizcode = bizcode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDongcode() {
		return dongcode;
	}
	public void setDongcode(String dongcode) {
		this.dongcode = dongcode;
	}
	public String getDong() {
		return dong;
	}
	public void setDong(String dong) {
		this.dong = dong;
	}
	
	@Override
	public String toString() {
		return "PollutionDto [name=" + name + ", bizcode=" + bizcode + ", address=" + address + ", dongcode=" + dongcode
				+ ", dong=" + dong + "]";
	}
	
}
