package com.boj.implementation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main_Silver5_2578_빙고 {

	static int[][] map;
	static boolean[][] checked;
	static int[] num;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringBuilder sb = new StringBuilder();

		map = new int[5][5];
		StringTokenizer st = null;
		for (int i = 0; i < 5; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < 5; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		num = new int[25];
		int temp = 0;
		for (int i = 0; i < 5; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < 5; j++) {
				num[temp + j] = Integer.parseInt(st.nextToken());
			}
			temp += 5;
		}

		checked = new boolean[5][5];
		for (int t = 0; t < 25; t++) {
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					if (num[t] == map[i][j]) {
						checked[i][j] = true;
					}
				}
			}

			if (t <= 10) { // 절대 빙고가 나올 수 없다.
				continue;
			}

			int bingo = 0;
			for (int i = 0; i < 5; i++) {
				if (checkRow(i)) {
					bingo++;
				}
				if (checkCol(i)) {
					bingo++;
				}
			}
			if (checkRight()) {
				bingo++;
			}

			if (checkLeft()) {
				bingo++;
			}

			if (bingo >= 3) {
				sb.append(t + 1);
				break;
			}

		}

		bw.write(sb.toString());
		bw.flush();

		br.close();
		bw.close();
	}

	private static boolean checkRow(int x) { // 가로 탐색
		int cnt = 0;
		for (int i = 0; i < 5; i++) {
			if (checked[i][x]) {
				cnt++;
			}
		}

		return cnt == 5 ? true : false;
	}

	private static boolean checkCol(int y) { // 세로 탐색
		int cnt = 0;
		for (int i = 0; i < 5; i++) {
			if (checked[y][i]) {
				cnt++;
			}
		}

		return cnt == 5 ? true : false;
	}

	private static boolean checkRight() { // 오른쪽 대각선 탐색
		int cnt = 0;
		for (int i = 0; i < 5; i++) {
			if (checked[i][i]) {
				cnt++;
			}
		}

		return cnt == 5 ? true : false;
	}

	private static boolean checkLeft() { // 왼쪽 대각선 탐색
		int cnt = 0;
		int j = 4;
		for (int i = 0; i < 5; i++) {
			if (checked[i][j]) {
				cnt++;
				j--;
			}
		}

		return cnt == 5 ? true : false;
	}
}
