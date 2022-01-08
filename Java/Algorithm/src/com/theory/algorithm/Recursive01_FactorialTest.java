package com.theory.algorithm;

import java.util.Scanner;

public class Recursive01_FactorialTest {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		System.out.println(factorial(N));

	}
	
	// n!: n * (n-1)!
	
	// n! 계산
	private static long factorial(int n) {
		// 기저 파트
		if(n==1 || n == 0) return 1;
		// 유도 파트
		return n * factorial(n-1);
		
	}
}
