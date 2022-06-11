package com.boj.graph;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_Silver2_11725_트리의부모찾기 {

	static int N;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringBuilder sb = new StringBuilder();

		N = Integer.parseInt(st.nextToken()); // 노드의 개수, 2 <= N <= 100,000

		List<Integer> list[] = new ArrayList[N + 1];
		for (int i = 0; i < N + 1; i++) {
			list[i] = new ArrayList<>();
		}

		for (int i = 0; i < N - 1; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			list[a].add(b);
			list[b].add(a);
		}

		Queue<Integer> q = new LinkedList<>();
		boolean visited[] = new boolean[N + 1];

		// 트리의 최상위 노드는 1로 고정
		q.add(1);
		visited[1] = true;

		int ans[] = new int[N + 1];
		while (!q.isEmpty()) {
			Integer cur = q.poll();
			for (Integer i : list[cur]) {
				if (!visited[i]) {
					visited[i] = true;
					ans[i] = cur;
					q.add(i);
				}
			}
		}

		// 2번 노드부터 부모 노드 출력
		for (int i = 2; i < N + 1; i++) {
			sb.append(ans[i]).append("\n");
		}

		bw.write(sb.toString());
		bw.flush();
		bw.close();
	}
}
