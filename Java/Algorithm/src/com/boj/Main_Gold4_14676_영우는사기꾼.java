package com.boj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main_Gold4_14676_영우는사기꾼 {

	static int N, M, K; // 1 ≤ N, M, K ≤ 100,000
	static int[] indegree;
	static List<List<Integer>> list;
	static int[][] game;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringBuilder sb = new StringBuilder();

		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // 건물 종류의 개수
		M = Integer.parseInt(st.nextToken()); // 건물 사이 관계의 수
		K = Integer.parseInt(st.nextToken()); // 영우의 게임 정보의 개수

		indegree = new int[N + 1];
		list = new ArrayList<>();
		for (int i = 0; i < N + 1; i++) {
			list.add(new ArrayList<Integer>());
		}
		
		

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken()); // a가 반드시
			int b = Integer.parseInt(st.nextToken()); // b보다 먼저 지어져야 한다.

			list.get(a).add(b);
			indegree[b]++;
		}

		game = new int[K][2]; // 영우의 게임 정보
		for (int i = 0; i < K; i++) {
			st = new StringTokenizer(br.readLine());
			game[i][0] = Integer.parseInt(st.nextToken()); // 1: 건설, 2: 파괴
			game[i][1] = Integer.parseInt(st.nextToken()); // 건물 번호
		}

		bw.write(sb.toString());
		bw.flush();

		br.close();
		bw.close();
	}
}
