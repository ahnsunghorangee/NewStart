package com.boj.topology;

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

public class Main_Gold2_2252_줄세우기 {

	static int N, M;
	static StringBuilder sb;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		sb = new StringBuilder();

		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // 학생 수, 1 ≤ N ≤ 32,000
		M = Integer.parseInt(st.nextToken()); // 비교 횟수, 1 ≤ M ≤ 100,000

		// 위상정렬 Point! 간선 수 배열
		int[] cnt = new int[N + 1];

		// 비교한 키 순서에 맞게 정렬하기
		List<List<Integer>> graph = new ArrayList<>();
		for (int i = 0; i < N + 1; i++) {
			graph.add(new ArrayList<Integer>());
		}

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken()); // A가 반드시
			int b = Integer.parseInt(st.nextToken()); // B 앞에
			graph.get(a).add(b);
			cnt[b]++;
		}

		topologicalSort(graph, cnt);

		bw.write(sb.toString());
		bw.flush();

		br.close();
		bw.close();
	}

	private static void topologicalSort(List<List<Integer>> graph, int[] cnt) {
		Queue<Integer> q = new LinkedList<>();

		// 진입 차수가 0인 정, 즉 먼저 실행될 점이 없는 점을 Queue에 먼저 넣는다.
		for (int i = 1; i <= N; i++) {
			if (cnt[i] == 0) {
				q.add(i);
			}
		}

		// 정점 수 만큼 반복
		for (int i = 1; i <= N; i++) {
			int v = q.poll(); // 이미 줄을 선 학생이면
			sb.append(v).append(" ");

			for (int next : graph.get(v)) {
				cnt[next]--; // 그를 가지고 있는 학생의 진입 차수를 낮춰준다.
				
				if(cnt[next] == 0) {
					q.add(next);
				}
			}
		}

	}
}
