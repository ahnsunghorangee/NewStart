package com.ssafy.projectboot.dto;

public class SaleImgDto {
	private int no;
	private String userid;
	private String imgname;
	private int saleno;
	public int getSaleno() {
		return saleno;
	}
	public void setSaleno(int saleno) {
		this.saleno = saleno;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getImgname() {
		return imgname;
	}
	public void setImgname(String imgname) {
		this.imgname = imgname;
	}
	@Override
	public String toString() {
		return "SaleImgDto [no=" + no + ", userid=" + userid + ", imgname=" + imgname + ", saleno=" + saleno + "]";
	}
	
}
