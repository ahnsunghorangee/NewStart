package com.boj.unionfind;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_Gold4_1976_여행가자 {

	static int N, M;
	static int[] parents;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine()); // 도시 수
		M = Integer.parseInt(br.readLine()); // 여행 계획 수

		makeSet();

		StringTokenizer st = null;
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());

			for (int j = 0; j < N; j++) {
				if (Integer.parseInt(st.nextToken()) == 1) { // 연결되어 있으면
					union(i, j); // 합한다.
				}
			}
		}

		st = new StringTokenizer(br.readLine());
		int start = find(Integer.parseInt(st.nextToken()) - 1);
		for (int i = 1; i < M; i++) {

			if (start != find(Integer.parseInt(st.nextToken()) - 1)) { // 시작점과 부모가 다르면 연결이 안 되어 있음을 의미한다.
				System.out.println("NO");
				return;
			}
		}

		System.out.println("YES");
	}

	private static void makeSet() {
		parents = new int[N];
		for (int i = 0; i < N; i++) {
			parents[i] = i;
		}

		return;
	}

	private static int find(int x) {
		if (parents[x] == x)
			return x;
		return parents[x] = find(parents[x]);
	}

	private static void union(int a, int b) {
		a = find(a);
		b = find(b);

		if (a == b) {
			return;
		} else {
			if (a < b) {
				parents[b] = a;
			} else {
				parents[a] = b;
			}
		}

		return;
	}
}
