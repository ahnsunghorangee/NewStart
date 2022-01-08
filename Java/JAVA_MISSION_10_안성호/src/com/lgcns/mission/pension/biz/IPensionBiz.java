package com.lgcns.mission.pension.biz;

public interface IPensionBiz {
	public void checkIn(String roomId, String custName, String custPhone) throws Exception;
	
	public void checkOut(String roomId) throws Exception;
	
	public void initializeRoomData();
	
	public void roomList();
}
