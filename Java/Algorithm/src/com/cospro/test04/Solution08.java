package com.cospro.test04;

import java.util.ArrayList;
import java.util.Arrays;

// 구현
public class Solution08 {

	static boolean[] isSelected;
	static int[] output;
	static ArrayList<Integer> list;

	public int solution(int[] card, int n) {
		// 여기에 코드를 작성해주세요.
		int answer = 0;

		Arrays.sort(card);

		isSelected = new boolean[card.length];
		output = new int[card.length];
		list = new ArrayList<Integer>();

		nPr(card, n, 0);

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) == n) {

				answer = i + 1;
				break;
			}
		}

		return answer == 0 ? -1 : answer;
	}

	private static void nPr(int[] card, int n, int cnt) {
		if (cnt == card.length) {
			int num = 0;
			cnt--;

			for (int i = 0; i < card.length; i++) {
				num += output[i] * Math.pow(10, cnt--);
			}

			list.add(num); // ★. list에 한번에 넣고 계산

			return;
		}

		for (int i = 0; i < card.length; i++) {
			if (isSelected[i]) {
				continue;
			}

			output[cnt] = card[i];
			isSelected[i] = true;

			nPr(card, n, cnt + 1);
			isSelected[i] = false;
		}
	}

	// 아래는 테스트케이스 출력을 해보기 위한 main 메소드입니다.
	public static void main(String[] args) {
		Solution08 sol = new Solution08();
		int card1[] = { 1, 2, 1, 3 };
		int n1 = 1312;
		int ret1 = sol.solution(card1, n1);

		// [실행] 버튼을 누르면 출력 값을 볼 수 있습니다.
		System.out.println("solution 메소드의 반환 값은 " + ret1 + " 입니다.");

		int card2[] = { 1, 1, 1, 2 };
		int n2 = 1122;
		int ret2 = sol.solution(card2, n2);

		// [실행] 버튼을 누르면 출력 값을 볼 수 있습니다.
		System.out.println("solution 메소드의 반환 값은 " + ret2 + " 입니다.");
	}
}