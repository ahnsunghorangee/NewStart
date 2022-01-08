package com.lgcns.mission.pension.exception;

public class AlreadyReservedException extends Exception {

	public AlreadyReservedException() {
		super("[에러] 이미 예약되어있습니다.");
	}
}
