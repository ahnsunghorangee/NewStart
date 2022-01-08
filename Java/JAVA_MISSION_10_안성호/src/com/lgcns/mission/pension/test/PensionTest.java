package com.lgcns.mission.pension.test;

import com.lgcns.mission.pension.biz.IPensionBiz;
import com.lgcns.mission.pension.biz.PensionBiz;
import com.lgcns.mission.util.MissionUtil;

public class PensionTest {

	public static void main(String[] args) {

		IPensionBiz pb = new PensionBiz();

		pb.initializeRoomData();

		int menu = -1;
		while (menu != 9) {
			printHeader();

			try {
				menu = Integer.parseInt(MissionUtil.getUserInput());
			} catch (NumberFormatException e) {
				System.out.println("[에러] 메뉴를 다시 입력하세요.");
				continue;
			}

			if (menu < 0 || menu > 3) {
				System.out.println("[에러] 메뉴를 다시 입력하세요.");
				continue;
			}

			if (menu == 1) {
				System.out.println("-------------방 정보-------------");
				pb.roomList();
			} else if (menu == 2) {
				System.out.print("투숙하실 방 번호를 입력하세요 >> ");
				String roomId = MissionUtil.getUserInput();
				System.out.print("고객님의 이름을 입력하세요 >> ");
				String custName = MissionUtil.getUserInput();
				System.out.print("고객님의 전화번호를 입력하세요 >> ");
				String custPhone = MissionUtil.getUserInput();

				try {
					Integer.parseInt(roomId);

					pb.checkIn(roomId, custName, custPhone);

					System.out.println("@ 예약되었습니다.");
				} catch (NumberFormatException e) {
					System.out.println("[에러] 방 번호는 숫자로 입력하세요.");
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			} else if (menu == 3) {
				System.out.print("투숙하실 방 번호를 입력하세요 >> ");
				String roomId = MissionUtil.getUserInput();

				try {
					Integer.parseInt(roomId);

					pb.checkOut(roomId);

					System.out.println("@ 퇴실되었습니다.");
				} catch (NumberFormatException e) {
					System.out.println("[에러] 방 번호는 숫자로 입력하세요.");
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			} else if (menu == 0) {
				System.out.println("Bye~ Bye~");
				break;
			}
		}
	}

	public static void printHeader() {
		System.out.println("======<< 춘 펜션 관리 시스템 >>======");
		System.out.println(" 1. 전체 방 조회");
		System.out.println(" 2. 입실");
		System.out.println(" 3. 퇴실");
		System.out.println(" 0. 종료");
		System.out.println("==============================");
		System.out.print("# 메뉴를 입력하세요 >> ");
	}
}
