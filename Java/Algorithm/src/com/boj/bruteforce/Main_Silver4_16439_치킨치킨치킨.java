package com.boj.bruteforce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_Silver4_16439_치킨치킨치킨 {

	static int N, M, max;
	static int[][] satisfaction;
	static int[] out;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken()); // 고리 회원의 수, 1 <= N <= 30
		M = Integer.parseInt(st.nextToken()); // 치킨 종류의 수, 3 <= M <= 30

		satisfaction = new int[N][M];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				satisfaction[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		// 최대 3가지 종류의 치킨
		out = new int[3];
		max = 0;

		nCr(0, 0);

		// 만족도 합의 최댓값
		System.out.println(max);
	}

	public static void nCr(int cnt, int cur) {
		if (cnt == 3) {
			int sum = 0;
			for (int i = 0; i < N; i++) {
				int better = 0;
				for (int j = 0; j < 3; j++) {
					if (satisfaction[i][out[j]] > better) {
						better = satisfaction[i][out[j]];
					}
				}
				sum += better;
			}

			if (max < sum) {
				max = sum;
			}

			return;
		}

		for (int i = cur; i < M; i++) {
			out[cnt] = i;
			nCr(cnt + 1, i + 1);
		}
	}
}
