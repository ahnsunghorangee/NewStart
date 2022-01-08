package com.lgcns.mission.pension.exception;

public class AlreadyCheckoutException extends Exception {

	public AlreadyCheckoutException() {
		super("[에러] 이미 퇴실되어있습니다.");
	}
}
