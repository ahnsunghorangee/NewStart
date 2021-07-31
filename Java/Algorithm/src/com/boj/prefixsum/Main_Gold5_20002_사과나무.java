package com.boj.prefixsum;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main_Gold5_20002_사과나무 {

	static int N, ans;
	static int[][] map, prefix;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringBuilder sb = new StringBuilder();

		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // 과수원 크기, 1 ≤ N ≤ 300

		map = new int[N][N];
		prefix = new int[N + 1][N + 1];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				prefix[i + 1][j + 1] = prefix[i][j + 1] + prefix[i + 1][j] - prefix[i][j] + map[i][j];
			}
		}
		
		ans = map[0][0];

		for (int n = 1; n < N + 1; n++) {
			for (int i = 0; i + n < N + 1; i++) {
				for (int j = 0; j + n < N + 1; j++) {
					int value = prefix[i + n][j + n] + prefix[i][j] - prefix[i + n][j] - prefix[i][j + n];
					ans = Math.max(ans, value);
				}
			}
		}

		sb.append(ans);

		bw.write(sb.toString());
		bw.flush();

		br.close();
		bw.close();
	}
}
