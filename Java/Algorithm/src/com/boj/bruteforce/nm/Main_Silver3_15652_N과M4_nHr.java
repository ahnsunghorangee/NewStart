package com.boj.bruteforce.nm;

import java.util.Scanner;

public class Main_Silver3_15652_N과M4_nHr {
	static int N, M;
	static int[] output;
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		M = sc.nextInt();
		output = new int[N + 1];

		// nHr: 중복 조합
		nHr(0,1);
		System.out.println(sb);
	}

	private static void nHr(int cnt, int cur) {
		if (cnt == M) {
			for (int i = 0; i < M; i++) {
				sb.append(output[i]).append(" ");
			}
			sb.append("\n");
			return;
		}

		for (int i = cur; i <= N; i++) {

			output[cnt] = i;
			nHr(cnt + 1, i);
		}

	}
}
