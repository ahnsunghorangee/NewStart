package com.boj.dp.topdown;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main_Silver3_2579_계단오르기 {

	static int n;
	static int[] steps;
	static Integer[] dp;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		n = Integer.parseInt(br.readLine()); // 계단 수
		steps = new int[n + 1]; // steps[0]은 시작점

		for (int i = 1; i <= n; i++) {
			steps[i] = Integer.parseInt(br.readLine());
		}

		if (n == 1) {
			System.out.println(steps[1]);
		} else if (n == 2) {
			System.out.println(steps[1] + steps[2]);
		} else {
			dp = new Integer[n+1];
			
			dp[0] = steps[0];
			dp[1] = steps[1];
			dp[2] = steps[1]+steps[2];
			
			System.out.println(find(n));
		}

	}

	private static int find(int n) {
		if (dp[n] == null) {
			dp[n] = Math.max(find(n - 2), find(n - 3) + steps[n - 1]) + steps[n];
		}
		return dp[n];
	}
}
