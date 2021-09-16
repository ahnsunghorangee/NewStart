package com.boj.math;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_Silver5_16208_귀찮음 {

	static int N;
	static long[] in;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringBuilder sb = new StringBuilder();

		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());

		st = new StringTokenizer(br.readLine());
		in = new long[N];
		long total = 0;
		for (int i = 0; i < N; i++) {
			in[i] = Integer.parseInt(st.nextToken());
			total += in[i];
		}

		Arrays.sort(in);

		long answer = 0;
		for (int i = 0; i < N - 1; i++) {
			answer += (in[i] * (total - in[i]));
			total -= in[i];
		}

		sb.append(answer);

		bw.write(sb.toString());
		bw.flush();

		br.close();
		bw.close();
	}
}
