package com.boj.dp.bottomup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_Silver3_2579_계단오르기 {

	static int n;
	static int[] steps;

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
			int[] dp = new int[n + 1];
			dp[0] = steps[0];
			dp[1] = steps[1];
			dp[2] = steps[1] + steps[2];

			for (int i = 3; i <= n; i++) {
				dp[i] = Math.max(dp[i - 3] + steps[i - 1], dp[i - 2]) + steps[i];
			}

			System.out.println(dp[n]);
		}

	}
}
