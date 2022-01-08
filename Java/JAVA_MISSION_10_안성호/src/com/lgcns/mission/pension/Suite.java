package com.lgcns.mission.pension;

public class Suite extends RoomType{

	public Suite() {
		super(260000,30);
	}

	@Override
	public String toString() {
		return "스위트룸 " + super.toString();
	}
}
