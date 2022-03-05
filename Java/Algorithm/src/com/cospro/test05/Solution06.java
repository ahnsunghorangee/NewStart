package com.cospro.test05;

// 처음부터 구현하기
public class Solution06 {
	public String solution(String s1, String s2, int p, int q) {
		// 여기에 코드를 작성해주세요.
		String answer = "";

		// p진법으로 구현한 s1, s2
		int pS1 = 0;
		for (int i = 0; i < s1.length(); i++) {
			pS1 += (s1.charAt(s1.length() - 1 - i) - '0') * Math.pow(p, i);
		}
		int pS2 = 0;
		for (int i = 0; i < s2.length(); i++) {
			pS1 += (s2.charAt(s2.length() - 1 - i) - '0') * Math.pow(p, i);
		}

		// q진법으로 바꾸기
		int sum = pS1 + pS2;
		while (sum > 0) {
			answer += sum % 8;
			sum /= 8;
		}

		// 뒤집기
		StringBuilder sb = new StringBuilder(answer);
		answer = sb.reverse().toString();

		return answer;
	}

	// 아래는 테스트케이스 출력을 해보기 위한 main 메소드입니다.
	public static void main(String[] args) {
		Solution06 sol = new Solution06();
		String s1 = new String("112001");
		String s2 = new String("12010");
		int p = 3;
		int q = 8;
		String ret = sol.solution(s1, s2, p, q);

		// [실행] 버튼을 누르면 출력 값을 볼 수 있습니다.
		System.out.println("solution 메소드의 반환 값은 " + ret + " 입니다.");
	}
}