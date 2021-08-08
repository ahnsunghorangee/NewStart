package com.ssafy.projectboot.dto;

public class ShopDto {
	private int no;
	private String shopname;
	private String address;
	private String lng;
	private String lat;
	private String code1;
	private String codename1;
	private String code2;
	private String codename2;
	private String code3;
	private String codename3;
	private String code4;
	private String codename4;
	private String citycode;
	private String city;
	private String gu;
	private String gucode;
	private String dong;
	private String dongcode;
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getShopname() {
		return shopname;
	}
	public void setShopname(String shopname) {
		this.shopname = shopname;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getCode1() {
		return code1;
	}
	public void setCode1(String code1) {
		this.code1 = code1;
	}
	public String getCodename1() {
		return codename1;
	}
	public void setCodename1(String codename1) {
		this.codename1 = codename1;
	}
	public String getCode2() {
		return code2;
	}
	public void setCode2(String code2) {
		this.code2 = code2;
	}
	public String getCodename2() {
		return codename2;
	}
	public void setCodename2(String codename2) {
		this.codename2 = codename2;
	}
	public String getCode3() {
		return code3;
	}
	public void setCode3(String code3) {
		this.code3 = code3;
	}
	public String getCodename3() {
		return codename3;
	}
	public void setCodename3(String codename3) {
		this.codename3 = codename3;
	}
	public String getCode4() {
		return code4;
	}
	public void setCode4(String code4) {
		this.code4 = code4;
	}
	public String getCodename4() {
		return codename4;
	}
	public void setCodename4(String codename4) {
		this.codename4 = codename4;
	}
	public String getCitycode() {
		return citycode;
	}
	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getGu() {
		return gu;
	}
	public void setGu(String gu) {
		this.gu = gu;
	}
	public String getGucode() {
		return gucode;
	}
	public void setGucode(String gucode) {
		this.gucode = gucode;
	}
	public String getDong() {
		return dong;
	}
	public void setDong(String dong) {
		this.dong = dong;
	}
	public String getDongcode() {
		return dongcode;
	}
	public void setDongcode(String dongcode) {
		this.dongcode = dongcode;
	}
	@Override
	public String toString() {
		return "ShopServiceImpl [no=" + no + ", shopname=" + shopname + ", address=" + address + ", lng=" + lng
				+ ", lat=" + lat + ", code1=" + code1 + ", codename1=" + codename1 + ", code2=" + code2 + ", codename2="
				+ codename2 + ", code3=" + code3 + ", codename3=" + codename3 + ", code4=" + code4 + ", codename4="
				+ codename4 + ", citycode=" + citycode + ", city=" + city + ", gu=" + gu + ", gucode=" + gucode
				+ ", dong=" + dong + ", dongcode=" + dongcode + "]";
	}
}
