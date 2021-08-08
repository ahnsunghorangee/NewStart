package com.boj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main_Silver5_1359_복권 {

	static int N, M, K;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringBuilder sb = new StringBuilder();

		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		int numerator = 1; // 분자
		int denominator = 1; // 분모
		
		for (int i = N; i > N - M; i--) {
			numerator *= i;
		}
		
		for (int i = M; i > 0; i--) {
			denominator *= i;
		}
		int totalCase = numerator / denominator;

		sb.append((double) K / totalCase);

		bw.write(sb.toString());
		bw.flush();

		br.close();
		bw.close();
	}
}
