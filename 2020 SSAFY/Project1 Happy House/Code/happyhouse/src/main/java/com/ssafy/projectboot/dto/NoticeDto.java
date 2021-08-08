package com.ssafy.projectboot.dto;

public class NoticeDto {
	private int articleno;
	private String subject;
	private String content;
	private int click;
	private String regtime;
	public int getArticleno() {
		return articleno;
	}
	public void setArticleno(int articleno) {
		this.articleno = articleno;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getClick() {
		return click;
	}
	public void setClick(int click) {
		this.click = click;
	}
	public String getRegtime() {
		return regtime;
	}
	public void setRegtime(String regtime) {
		this.regtime = regtime;
	}
	
	@Override
	public String toString() {
		return "NoticeDto [articleno=" + articleno + ", subject=" + subject + ", content=" + content + ", click="
				+ click + ", regtime=" + regtime + "]";
	}
}
