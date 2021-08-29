package com.boj.kruskal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_Gold3_14621_나만안되는연애 {

	static int N, M, min;
	static boolean[] isMan;
	static int[] parents;
	static PriorityQueue<Node> pq;

	static class Node implements Comparable<Node> {
		int from, to, cost;

		public Node(int from, int to, int cost) {
			super();
			this.from = from;
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
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			char sex = st.nextToken().charAt(0);
			if (sex == 'M') {
				isMan[i] = true;
			}
		}

		pq = new PriorityQueue<>();
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());

			int p1 = Integer.parseInt(st.nextToken()) - 1;
			int p2 = Integer.parseInt(st.nextToken()) - 1;
			int cost = Integer.parseInt(st.nextToken());

			if (isMan[p1] == isMan[p2]) { // 같은 성별은 저장할 필요가 없다.
				continue;
			} else {
				pq.offer(new Node(p1, p2, cost));
			}
		}

		min = 0;

		sb.append(kruskal() ? min : -1);

		bw.write(sb.toString());
		bw.flush();

		br.close();
		bw.close();
	}

	private static void makeSet() {
		parents = new int[N];
		for (int i = 0; i < N; i++) {
			parents[i] = i;
		}
	}
	
	private static int find(int x) {
		if (parents[x] == x) {
			return x;
		} else {
			return parents[x] = find(parents[x]);
		}
	}

	private static void union(int x, int y) {
		x = find(x);
		y = find(y);

		if (x != y) {
			if (x < y) {
				parents[y] = x;
			} else {
				parents[x] = y;
			}
		}
	}

	private static boolean kruskal() {
		makeSet();

		int visitedCnt = 1;

		while (!pq.isEmpty()) {
			Node cn = pq.poll();

			int x = find(cn.from);
			int y = find(cn.to);

			if (x == y) {
				continue;
			} else {
				union(x, y);
				min += cn.cost;

				visitedCnt++;
			}

			if (visitedCnt == N) {
				pq.clear();
				return true;
			}
		}
		return false;
	}
}
