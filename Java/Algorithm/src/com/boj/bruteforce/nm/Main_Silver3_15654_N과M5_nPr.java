package com.boj.bruteforce.nm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_Silver3_15654_N과M5_nPr {
	static int N, M;
	static int[] input;
	static boolean[] isSelected;
	static int[] output;
	
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		input = new int[N];
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			input[i] = Integer.parseInt(st.nextToken());
		}
		Arrays.sort(input);

		isSelected = new boolean[N];
		output = new int[N];

		// nPr: 서로 다른 n개 중 r개를 선택하는 경우(순서 o)
		nPr(0);
		System.out.println(sb);
	}

	private static void nPr(int cnt) {
		if (cnt == M) {
			for (int i = 0; i < M; i++) {
				sb.append(output[i]).append(" ");
			}
			sb.append("\n");
			return;
		}

		for (int i = 0; i < N; i++) {
			if (isSelected[i])
				continue;

			output[cnt] = input[i];
			isSelected[i] = true;
			nPr(cnt + 1);
			isSelected[i] = false;
		}

	}
}
