package com.hackathon.woofy.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "transinfo")
@Getter
@Setter
public class Transinfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "transinfo_id")
	private Long id;
	
	private LocalDateTime date; // 결제 날짜
	private String location; // 결제 장소
	private int price; // 결제 금액
	
	private Long childNum; // 자녀 결제 시, child_id 저장
	
	@Enumerated(EnumType.STRING)
	private TransinfoStatus transinfoStatus;
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private Parent parent;
}
