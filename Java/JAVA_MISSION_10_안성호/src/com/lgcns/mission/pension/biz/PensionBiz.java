package com.lgcns.mission.pension.biz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.lgcns.mission.pension.Customer;
import com.lgcns.mission.pension.Standard;
import com.lgcns.mission.pension.Suite;
import com.lgcns.mission.pension.exception.AlreadyCheckoutException;
import com.lgcns.mission.pension.exception.AlreadyReservedException;

public class PensionBiz implements IPensionBiz {

	private ArrayList<HashMap<String, Object>> data;

	@Override
	public void checkIn(String roomId, String custName, String custPhone) throws Exception {
		boolean isPossible = false;

		for (HashMap<String, Object> h : data) {
			if (h.containsKey(roomId)) {
				if (h.containsKey("guest")) {
					throw new AlreadyReservedException();
				} else {
					h.put("guest", new Customer(custName, custPhone));
					isPossible = true;
					break;
				}
			}
		}

		if (!isPossible) {
			throw new Exception("[에러] 정확한 방 번호를 입력하세요.");
		}
	}

	@Override
	public void checkOut(String roomId) throws Exception {
		boolean isPossible = false;

		for (HashMap<String, Object> h : data) {
			if (h.containsKey(roomId)) {
				if (!h.containsKey("guest")) {
					throw new AlreadyCheckoutException();
				} else {
					h.remove("guest");
					isPossible = true;
					break;
				}
			}
		}

		if (!isPossible) {
			throw new Exception("[에러] 정확한 방 번호를 입력하세요.");
		}
	}

	@Override
	public void initializeRoomData() {
		this.data = new ArrayList<>();

		HashMap<String, Object> hm = new HashMap<>();
		hm.put("101", new Standard());
		data.add(hm);

		HashMap<String, Object> hm2 = new HashMap<>();
		hm2.put("102", new Standard());
		data.add(hm2);

		HashMap<String, Object> hm3 = new HashMap<>();
		hm3.put("103", new Standard());
		data.add(hm3);

		HashMap<String, Object> hm4 = new HashMap<>();
		hm4.put("104", new Suite());
		data.add(hm4);

		HashMap<String, Object> hm5 = new HashMap<>();
		hm5.put("105", new Suite());
		data.add(hm5);
	}

	@Override
	public void roomList() {
		// 방 번호만 추출
		ArrayList<String> roomList = new ArrayList<>();

		for (HashMap<String, Object> hm : data) {
			for (String s : hm.keySet()) {
				if (s.equals("guest")) {
					continue;
				} else {
					roomList.add(s);
				}
			}
		}

		// 오름차순 정렬
		Collections.sort(roomList);

		// 출력
		for (String roomId : roomList) {
			for (HashMap<String, Object> hm : data) {
				if (hm.containsKey(roomId)) {
					System.out.print(roomId + "호: " + hm.get(roomId).toString());

					if (hm.containsKey("guest")) {
						System.out.print(hm.get("guest").toString());
					}
					
				}
			}
			System.out.println();
		}
	}

}
