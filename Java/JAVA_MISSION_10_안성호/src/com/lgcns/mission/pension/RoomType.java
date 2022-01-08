package com.lgcns.mission.pension;

public class RoomType {

	private int price;
	private int roomSize;

	public RoomType(int price, int roomSize) {
		super();
		this.price = price;
		this.roomSize = roomSize;
	}

	@Override
	public String toString() {
		return "(" + roomSize + "평/" + price + "원)";
	}
}
