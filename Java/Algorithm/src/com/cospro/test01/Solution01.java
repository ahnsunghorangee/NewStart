package com.cospro.test01;

// 구현
public class Solution01 {
	public long solution(long num) {
		num++;
		long digit = 1;
		while (num / digit % 10 == 0) {
			num += digit;
			digit *= 10;
		}
		return num;
	}

	// The following is main method to output testcase.
	public static void main(String[] args) {
		Solution01 sol = new Solution01();
		long num = 9949999;
		long ret = sol.solution(num);

		// Press Run button to receive output.
		System.out.println("Solution: return value of the method is " + ret + " .");
	}
}