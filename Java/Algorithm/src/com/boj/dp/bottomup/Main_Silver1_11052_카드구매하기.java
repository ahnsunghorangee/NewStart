package com.boj.dp.bottomup;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main_Silver1_11052_카드구매하기 {

	static int N;
	static int[] pack, dp;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringBuilder sb = new StringBuilder();

		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // 구매하려는 카드 개수

		pack = new int[N + 1];
		st = new StringTokenizer(br.readLine());
		for (int i = 1; i < N + 1; i++) {
			pack[i] = Integer.parseInt(st.nextToken()); // 카드 팩 가격
		}

		dp = new int[N + 1]; // ★. +1 해주는 이유는 dp에서 처음값이 2배가 되어 들어가기 때문
		
		for (int i = 1; i < N + 1; i++) {
			for (int j = 1; j <= i; j++) {
				dp[i] = Math.max(dp[i], dp[i - j] + pack[j]); // ★. 포인트
			}
		}
		
		sb.append(dp[N]);

		bw.write(sb.toString());
		bw.flush();

		br.close();
		bw.close();
	}
}
