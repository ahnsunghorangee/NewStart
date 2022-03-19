package com.cospro.test01;

// 구현
public class Solution03 {

	static int[][] dirs = { { 1, 2 }, { 1, -2 }, { -1, 2 }, { -1, -2 }, { 2, 1 }, { 2, -1 }, { -2, 1 }, { -2, -1 } };

	public int solution(String pos) {
		// Write code here.
		int answer = 0;

		int y = pos.charAt(1) - '0' - 1;
		int x = pos.charAt(0) - 'A';

		for (int d = 0; d < 8; d++) {
			if (y + dirs[d][0] < 0 || y + dirs[d][0] >= 8) {
				continue;
			}

			if (x + dirs[d][1] >= 0 && x + dirs[d][1] < 8) {
				answer++;
			}
		}

		return answer;
	}

	// The following is main method to output testcase.
	public static void main(String[] args) {
		Solution03 sol = new Solution03();
		String pos = "A7";
		int ret = sol.solution(pos);

		// Press Run button to receive output.
		System.out.println("Solution: return value of the method is " + ret + " .");
	}
}