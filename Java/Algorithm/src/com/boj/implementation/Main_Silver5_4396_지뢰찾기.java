package com.boj.implementation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main_Silver5_4396_지뢰찾기 {

	static int N;
	static char[][] map;
	static char[][] open;
	static char[][] answer;
	static int[][] dirs = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 }, { 1, 1 }, { 1, -1 }, { -1, 1 }, { -1, -1 } };

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringBuilder sb = new StringBuilder();

		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());

		map = new char[N][N];
		for (int i = 0; i < N; i++) {
			map[i] = br.readLine().toCharArray();
		}

		open = new char[N][N];
		for (int i = 0; i < N; i++) {
			open[i] = br.readLine().toCharArray();
		}

		answer = new char[N][N];
		boolean mineOpen = false;

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {

				if (open[i][j] == 'x') {
					// 지뢰가 열려있는 칸이면 지뢰가 있는 모든 칸이 별표로 표시되어야 한다.
					if (map[i][j] == '*') {
						mineOpen = true;
						continue;
					}

					if (isSurroundX(i, j)) { // x로 둘러쌓인 경우
						answer[i][j] = '0';
						continue;
					}

					// 주변에 .이 있는 경우, map[][] 검사
					int starCnt = 0;

					for (int d = 0; d < 8; d++) {
						int ny = i + dirs[d][0];
						int nx = j + dirs[d][1];

						if (!boundary(ny, nx)) {
							continue;
						}

						if (map[ny][nx] == '*') {
							starCnt++;
						}
					}

					answer[i][j] = (char) (starCnt + '0');
				} else {
					answer[i][j] = '.';
				}
			}
		}

		if (mineOpen) {
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if (map[i][j] == '*') {
						answer[i][j] = '*';
					}
				}
			}
		}

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				sb.append(answer[i][j]);
			}
			sb.append("\n");
		}

		bw.write(sb.toString());
		bw.flush();

		br.close();
		bw.close();
	}

	private static boolean isSurroundX(int i, int j) {
		for (int d = 0; d < 8; d++) {
			int ny = i + dirs[d][0];
			int nx = j + dirs[d][1];

			if (!boundary(ny, nx)) {
				continue;
			}

			if (map[ny][nx] != '.') {
				return false;
			}
		}

		return true;
	}

	private static boolean boundary(int ny, int nx) {
		return ny >= 0 && ny < N && nx >= 0 && nx < N;
	}
}
