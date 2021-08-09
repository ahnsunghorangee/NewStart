package com.ssafy.projectboot.dto;

public class PopupDto {
	private String roadFullAddr;
	private String roadAddrPart1;
	private String addrDetail;
	private String roadAddrPart2;
	private String engAddr;
	private String jibunAddr;
	private String zipNo;
	private String admCd;
	private String rnMgtSn;
	private String bdMgtSn;
	private String detBdNmList;
	private String bdNm;
	private String bdKdcd;
	private String siNm;
	private String sggNm;
	private String emdNm;
	private String liNm;
	private String rn;
	private String udrtYn;
	private String buldMnnm;
	private String buldSlno;
	private String mtYn;
	private String lnbrMnnm;
	private String lnbrSlno;
	private String emdNo;
	private String entX;
	private String entY;
	private String dealAmount;
	private String buildYear;
	private String area;
	private String floor;
	
	public String getDealAmount() {
		return dealAmount;
	}
	public void setDealAmount(String dealAmount) {
		this.dealAmount = dealAmount;
	}
	public String getBuildYear() {
		return buildYear;
	}
	public void setBuildYear(String buildYear) {
		this.buildYear = buildYear;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public String getRoadFullAddr() {
		return roadFullAddr;
	}
	public void setRoadFullAddr(String roadFullAddr) {
		this.roadFullAddr = roadFullAddr;
	}
	public String getRoadAddrPart1() {
		return roadAddrPart1;
	}
	public void setRoadAddrPart1(String roadAddrPart1) {
		this.roadAddrPart1 = roadAddrPart1;
	}
	public String getAddrDetail() {
		return addrDetail;
	}
	public void setAddrDetail(String addrDetail) {
		this.addrDetail = addrDetail;
	}
	public String getRoadAddrPart2() {
		return roadAddrPart2;
	}
	public void setRoadAddrPart2(String roadAddrPart2) {
		this.roadAddrPart2 = roadAddrPart2;
	}
	public String getEngAddr() {
		return engAddr;
	}
	public void setEngAddr(String engAddr) {
		this.engAddr = engAddr;
	}
	public String getJibunAddr() {
		return jibunAddr;
	}
	public void setJibunAddr(String jibunAddr) {
		this.jibunAddr = jibunAddr;
	}
	public String getZipNo() {
		return zipNo;
	}
	public void setZipNo(String zipNo) {
		this.zipNo = zipNo;
	}
	public String getAdmCd() {
		return admCd;
	}
	public void setAdmCd(String admCd) {
		this.admCd = admCd;
	}
	public String getRnMgtSn() {
		return rnMgtSn;
	}
	public void setRnMgtSn(String rnMgtSn) {
		this.rnMgtSn = rnMgtSn;
	}
	public String getBdMgtSn() {
		return bdMgtSn;
	}
	public void setBdMgtSn(String bdMgtSn) {
		this.bdMgtSn = bdMgtSn;
	}
	public String getDetBdNmList() {
		return detBdNmList;
	}
	public void setDetBdNmList(String detBdNmList) {
		this.detBdNmList = detBdNmList;
	}
	public String getBdNm() {
		return bdNm;
	}
	public void setBdNm(String bdNm) {
		this.bdNm = bdNm;
	}
	public String getBdKdcd() {
		return bdKdcd;
	}
	public void setBdKdcd(String bdKdcd) {
		this.bdKdcd = bdKdcd;
	}
	public String getSiNm() {
		return siNm;
	}
	public void setSiNm(String siNm) {
		this.siNm = siNm;
	}
	public String getSggNm() {
		return sggNm;
	}
	public void setSggNm(String sggNm) {
		this.sggNm = sggNm;
	}
	public String getEmdNm() {
		return emdNm;
	}
	public void setEmdNm(String emdNm) {
		this.emdNm = emdNm;
	}
	public String getLiNm() {
		return liNm;
	}
	public void setLiNm(String liNm) {
		this.liNm = liNm;
	}
	public String getRn() {
		return rn;
	}
	public void setRn(String rn) {
		this.rn = rn;
	}
	public String getUdrtYn() {
		return udrtYn;
	}
	public void setUdrtYn(String udrtYn) {
		this.udrtYn = udrtYn;
	}
	public String getBuldMnnm() {
		return buldMnnm;
	}
	public void setBuldMnnm(String buldMnnm) {
		this.buldMnnm = buldMnnm;
	}
	public String getBuldSlno() {
		return buldSlno;
	}
	public void setBuldSlno(String buldSlno) {
		this.buldSlno = buldSlno;
	}
	public String getMtYn() {
		return mtYn;
	}
	public void setMtYn(String mtYn) {
		this.mtYn = mtYn;
	}
	public String getLnbrMnnm() {
		return lnbrMnnm;
	}
	public void setLnbrMnnm(String lnbrMnnm) {
		this.lnbrMnnm = lnbrMnnm;
	}
	public String getLnbrSlno() {
		return lnbrSlno;
	}
	public void setLnbrSlno(String lnbrSlno) {
		this.lnbrSlno = lnbrSlno;
	}
	public String getEmdNo() {
		return emdNo;
	}
	public void setEmdNo(String emdNo) {
		this.emdNo = emdNo;
	}
	public String getEntX() {
		return entX;
	}
	public void setEntX(String entX) {
		this.entX = entX;
	}
	public String getEntY() {
		return entY;
	}
	public void setEntY(String entY) {
		this.entY = entY;
	}
	@Override
	public String toString() {
		return "PopupDto [roadFullAddr=" + roadFullAddr + ", roadAddrPart1=" + roadAddrPart1 + ", addrDetail="
				+ addrDetail + ", roadAddrPart2=" + roadAddrPart2 + ", engAddr=" + engAddr + ", jibunAddr=" + jibunAddr
				+ ", zipNo=" + zipNo + ", admCd=" + admCd + ", rnMgtSn=" + rnMgtSn + ", bdMgtSn=" + bdMgtSn
				+ ", detBdNmList=" + detBdNmList + ", bdNm=" + bdNm + ", bdKdcd=" + bdKdcd + ", siNm=" + siNm
				+ ", sggNm=" + sggNm + ", emdNm=" + emdNm + ", liNm=" + liNm + ", rn=" + rn + ", udrtYn=" + udrtYn
				+ ", buldMnnm=" + buldMnnm + ", buldSlno=" + buldSlno + ", mtYn=" + mtYn + ", lnbrMnnm=" + lnbrMnnm
				+ ", lnbrSlno=" + lnbrSlno + ", emdNo=" + emdNo + ", entX=" + entX + ", entY=" + entY + ", dealAmount="
				+ dealAmount + ", buildYear=" + buildYear + ", area=" + area + ", floor=" + floor + "]";
	}
	
}
