package com.boj.bruteforce.nm;

import java.util.Scanner;

public class Main_Silver3_15651_N과M3_nPIr {
	static int N, M;
	static int[] output;
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		M = sc.nextInt();
		output = new int[N + 1];

		// nPIr: 중복 순열
		nPIr(0);
		System.out.println(sb);
	}

	private static void nPIr(int cnt) {
		if (cnt == M) {
			for (int i = 0; i < M; i++) {
				sb.append(output[i]).append(" ");
			}
			sb.append("\n");
			return;
		}

		for (int i = 1; i <= N; i++) {

			output[cnt] = i;
			nPIr(cnt + 1);
		}

	}
}
