package com.cospro.test03;

// 구현
public class Solution05 {
	public String solution(String phrases, int second) {
		// 여기에 코드를 작성해주세요.
		String answer = "";

		second %= 28;
		String display = "______________" + phrases + "______________";
		answer = display.substring(second, second + 14);

		return answer;
	}

	// 아래는 테스트케이스 출력을 해보기 위한 main 메소드입니다.
	public static void main(String[] args) {
		Solution05 sol = new Solution05();
		String phrases = new String("happy-birthday");
		int second = 3;
		String ret = sol.solution(phrases, second);
		// [실행] 버튼을 누르면 출력 값을 볼 수 있습니다.
		System.out.println("solution 메소드의 반환 값은 " + ret + " 입니다.");
	}
}