package com.hackathon.woofy.request.wooriApi;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class GetAccBasicInfoRequestBody {

	private String INQ_ACNO;
	private String INQ_BAS_DT;
	private String ACCT_KND;
	private String INQ_CUCD;

	public GetAccBasicInfoRequestBody(String INQ_ACNO, String INQ_BAS_DT, String INQ_CUCD,String ACCT_KND) {
		this.INQ_ACNO = INQ_ACNO;
		this.INQ_BAS_DT = INQ_BAS_DT;
		this.INQ_CUCD = INQ_CUCD;
		this.ACCT_KND = ACCT_KND;
	}
}
