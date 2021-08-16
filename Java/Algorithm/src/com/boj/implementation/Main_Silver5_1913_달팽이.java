package com.boj.implementation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main_Silver5_1913_달팽이 {

	static int N, M;
	static int[][] map;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringBuilder sb = new StringBuilder();

		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(br.readLine());
		M = Integer.parseInt(st.nextToken());

		map = new int[N][N];

		int j = 0; // 열 위치
		int i = -1; // 행위치
		int count = 1; // 행열에 더해줄 변수
		int max = N * N;
		int temp = N;

		while (true) {
			for (int t = 1; t <= N; t++) {
				i += count;
				map[i][j] = max;
				max--;
			}

			N--;
			if (N <= 0) {
				break;
			}

			for (int t = 1; t <= N; t++) {
				j += count;
				map[i][j] = max;
				max--;
			}

			count *= -1;
		}

		int[] ans = new int[2];
		for (int y = 0; y < temp; y++) {
			for (int x = 0; x < temp; x++) {
				if(map[y][x] == M) {
					ans[0] = y+1;
					ans[1] = x+1;
				}
				sb.append(map[y][x]+" ");
			}
			sb.append("\n");
		}
		sb.append(ans[0]+" "+ans[1]);

		bw.write(sb.toString());
		bw.flush();

		br.close();
		bw.close();
	}
}
