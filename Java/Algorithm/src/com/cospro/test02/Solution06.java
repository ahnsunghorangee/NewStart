package com.cospro.test02;

import java.util.Arrays;

// 구현
public class Solution06 {
	public int[] solution(String commands) {
		// 여기에 코드를 작성해주세요.
		int[] answer = { 0, 0 };

		for (int i = 0; i < commands.length(); i++) {
			char alpha = commands.charAt(i);

			if (alpha == 'L') {
				answer[0]--;
			} else if (alpha == 'R') {
				answer[0]++;
			} else if (alpha == 'U') {
				answer[1]++;
			} else {
				answer[1]--;
			}
		}

		return answer;
	}

	// 아래는 테스트케이스 출력을 해보기 위한 main 메소드입니다.
	public static void main(String[] args) {
		Solution06 sol = new Solution06();
		String commands = "URDDL";
		int[] ret = sol.solution(commands);

		// [실행] 버튼을 누르면 출력 값을 볼 수 있습니다.
		System.out.println("solution 메소드의 반환 값은 " + Arrays.toString(ret) + " 입니다.");
	}
}