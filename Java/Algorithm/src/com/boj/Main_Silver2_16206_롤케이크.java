package com.boj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main_Silver2_16206_롤케이크 {

	static int N, M, ans;
	static int[] cake;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringBuilder sb = new StringBuilder();

		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		st = new StringTokenizer(br.readLine());
		cake = new int[N];
		for (int i = 0; i < N; i++) {
			cake[i] = Integer.parseInt(st.nextToken());
		}

		ans = 0;
		for (int i = 0; i < N; i++) {
			if (cake[i] < 10) {
				continue;
			}

			if (cake[i] == 10) {
				ans++;
				continue;
			}

			if (M > 0) {
				if (cake[i] % 10 == 0) { // 10으로 나누어 떨어지는 케익을 먼저 자른다.
					int slice = cake[i] / 10 - 1;
					if (slice <= M) {
						M -= slice;
						ans += (slice + 1);
					} else {
						ans += M;
						M = 0;
					}
				}
			}

		}

		for (int i = 0; i < N; i++) {
			if (M == 0) {
				break;
			}
			if (cake[i] < 10) {
				continue;
			}

			if (cake[i] % 10 == 0) {
				continue;
			}

			int slice = cake[i] / 10;
			if (slice <= M) {
				M -= slice;
				ans += slice;
			} else {
				ans += M;
				M = 0;
			}

		}

		sb.append(ans);

		bw.write(sb.toString());
		bw.flush();

		br.close();
		bw.close();
	}
	
}
