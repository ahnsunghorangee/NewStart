package com.boj.prim;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_Gold3_14621_나만안되는연애 {

	static int N, M, manCnt, min;
	static boolean[] isMan;
	static ArrayList<Node>[] adj;

	static class Node implements Comparable<Node> {
		int to, cost;

		public Node(int to, int cost) {
			this.to = to;
			this.cost = cost;
		}

		@Override
		public int compareTo(Node o) {
			return Integer.compare(this.cost, o.cost);
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringBuilder sb = new StringBuilder();

		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		isMan = new boolean[N];
		manCnt = 0;
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			char sex = st.nextToken().charAt(0);
			if (sex == 'M') {
//				manCnt++;
				isMan[i] = true;
			}
		}

		adj = new ArrayList[N];
		for (int i = 0; i < N; i++) {
			adj[i] = new ArrayList<>();
		}
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());

			int p1 = Integer.parseInt(st.nextToken()) - 1;
			int p2 = Integer.parseInt(st.nextToken()) - 1;
			int cost = Integer.parseInt(st.nextToken());

			if (isMan[p1] == isMan[p2]) { // 같은 성별은 저장할 필요가 없다.
				continue;
			} else {
				adj[p1].add(new Node(p2, cost));
				adj[p2].add(new Node(p1, cost));
			}
		}

		min = 0;

		sb.append(prim() ? min : -1);

		bw.write(sb.toString());
		bw.flush();

		br.close();
		bw.close();
	}

	private static boolean prim() {
		PriorityQueue<Node> pq = new PriorityQueue<>();
		boolean[] visited = new boolean[N];

		// 처음에는 남자가 많으면 남자부터 시작해야 될 줄 알았는데 생각해보니, 이어지는 간선을 모두 검사함으로 필요가 없다.
		/*
		int womanCnt = N - manCnt;
		if (manCnt > womanCnt) { // 남자부터 시작
			for (int i = 0; i < N; i++) {
				if (isMan[i]) {
					pq.addAll(adj[i]);
					visited[i] = true;
					break;
				}
			}
		} else {
			for (int i = 0; i < N; i++) {
				if (!isMan[i]) {
					pq.addAll(adj[i]);
					visited[i] = true;
					break;
				}
			}
		}
		*/
		pq.addAll(adj[0]);
		visited[0] = true;

		int visitedCnt = 1;

		while (!pq.isEmpty()) {
			Node cn = pq.poll();

			if (visited[cn.to]) {
				continue;
			}

			visited[cn.to] = true;
			min += cn.cost;

			for (Node next : adj[cn.to]) {
				if (!visited[next.to]) {
					pq.add(next);
				}
			}

			visitedCnt++;

			if (visitedCnt == N) {
				pq.clear();
				return true;
			}
		}
		return false;
	}
}
