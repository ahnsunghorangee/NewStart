package com.lgcns.mission.pension;

public class Standard extends RoomType{

	public Standard() {
		super(80000,14);
	}

	@Override
	public String toString() {
		return "스탠다드룸 "+super.toString();
	}
}
