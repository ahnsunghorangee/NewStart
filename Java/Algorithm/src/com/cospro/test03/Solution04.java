package com.cospro.test03;

// 구현
public class Solution04 {
	public int solution(String s1, String s2) {
		// 여기에 코드를 작성해주세요.
		int answer = s1.length()+s2.length();
		
		int case1 = check(s1,s2);
		int case2 = check(s2,s1);
		
		System.out.println(case1+" "+case2);
		
		if(case1 > case2) {
			answer -= case2;
		} else {
			answer -= case1;
		}
		
		
		return answer;
	}

	private static int check(String s1, String s2) {
		StringBuffer sb = new StringBuffer(s1);
		String reverse = sb.reverse().toString();
		
		int sameCnt = 0;
		for(int i=0;i<s2.length();i++) {
			if(reverse.length() < i) {
				break;
			}
			
			System.out.println(reverse.charAt(i)+" "+s2.charAt(i));
			
			if(reverse.charAt(i) == s2.charAt(i)) {
				sameCnt++;
			} else {
				break;
			}
		}
		
		System.out.println(sameCnt);

		return sameCnt;
	}

	// 아래는 테스트케이스 출력을 해보기 위한 main 메소드입니다.
	public static void main(String[] args) {
		Solution04 sol = new Solution04();
		String s1 = new String("ababc");
		String s2 = new String("abcdab");
		int ret = sol.solution(s1, s2);

		// [실행] 버튼을 누르면 출력 값을 볼 수 있습니다.
		System.out.println("solution 메소드의 반환 값은 " + ret + " 입니다.");
	}
}