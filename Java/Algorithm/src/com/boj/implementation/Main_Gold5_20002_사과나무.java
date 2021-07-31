package com.boj.implementation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/**
 * 시간 초과 -> Prefix Sum
 * @author jugia
 *
 */
public class Main_Gold5_20002_사과나무 {

	static int N, ans;
	static int[][] map;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringBuilder sb = new StringBuilder();

		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // 과수원 크기, 1 ≤ N ≤ 300

		map = new int[N][N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		

		ans = map[0][0];

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				ans = ans < map[i][j] ? map[i][j] : ans;
				check(i,j);
			}
		}
		
		sb.append(ans);

		bw.write(sb.toString());
		bw.flush();

		br.close();
		bw.close();
	}

	private static void check(int y, int x) {

		for (int n = 1; n < N; n++) {
			if (y + n < N && x + n < N) {
				int sum = 0;
				
				for (int i = y; i <= y + n; i++) {
					for (int j = x; j <= x + n; j++) {
						sum+=map[i][j];
					}
				}
				
				ans = ans < sum ? sum : ans;
			} else {
				break;
			}
		}
	}
	
}
