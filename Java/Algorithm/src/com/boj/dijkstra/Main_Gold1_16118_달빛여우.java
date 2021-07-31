package com.boj.dijkstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_Gold1_16118_달빛여우 {

	static int N, M;
	static ArrayList<Node>[] adj;
	static int[] fox;
	static int[][] wolf;

	static class Node implements Comparable<Node> {
		int dest, value, dir;

		public Node(int dest, int value) {
			super();
			this.dest = dest;
			this.value = value;
		}

		public Node(int dest, int value, int dir) {
			super();
			this.dest = dest;
			this.value = value;
			this.dir = dir;
		}

		@Override
		public int compareTo(Node o) {
			return Integer.compare(this.value, o.value);
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		adj = new ArrayList[N + 1];
		for (int i = 0; i < N + 1; i++) {
			adj[i] = new ArrayList<>();
		}

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());

			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken()) * 2; // ★ 2배 느리게 할 때 double을 막기 위해 x2해서 사용한다. 

			adj[a].add(new Node(b, c));
			adj[b].add(new Node(a, c));
		}

		fox = new int[N + 1];
		wolf = new int[N + 1][2];

		// 다익스트라: 하나의 정점에서 모든 정점까지의 최소 거리
		foxDijkstra(); // 일반적인 다익스트라: 달빛여우
		wolfDijkstra(); // 어려운 다익스트라: 달빛늑내

		// Ans: 달빛여우가 달빛늑대보다 먼저 도착 가능한 그루터 개수
		int Ans = 0;
		for (int i = 1; i < N + 1; i++) {
			if (fox[i] < Math.min(wolf[i][0], wolf[i][1])) {
				Ans++;
			}
		}

		System.out.println(Ans);
	}

	// 0: 짝수번째 도착, 1: 홀수번째 도착
	private static void wolfDijkstra() {
		PriorityQueue<Node> pq = new PriorityQueue<>();

		pq.offer(new Node(1, 0, 0));
		for (int i = 0; i < N + 1; i++) {
			Arrays.fill(wolf[i], Integer.MAX_VALUE);
		}
		wolf[1][0] = 0; // 왜 wolf[1][0] = wolf[1][1] = 0; 하면 틀릴까

		while (!pq.isEmpty()) {
			Node cn = pq.poll();

			// ★ 방문처리 하지 않는 것이 중요하다.
//			if (visited[cn.dest]) {
//				continue;
//			}

			if (wolf[cn.dest][cn.dir] < cn.value) { // (필수) 효율성!
				continue;
			}

			for (Node next : adj[cn.dest]) {
				int dist = 0;
				int nextDir = 0;

				if (cn.dir == 1) { // 현재 홀수번째 일 때,
					dist = next.value * 2; // 2배 느리게
					nextDir = 0;
				} else { // 현재 짝수번째 일 때,
					dist = next.value / 2; // 2배 빠르게
					nextDir = 1;
				}

				if (wolf[next.dest][nextDir] > wolf[cn.dest][cn.dir] + dist) {
					wolf[next.dest][nextDir] = wolf[cn.dest][cn.dir] + dist;
					pq.offer(new Node(next.dest, wolf[next.dest][nextDir], nextDir));
				}
			}

//			visited[cn.dest] = true;
		}
	}

	private static void foxDijkstra() {
		PriorityQueue<Node> pq = new PriorityQueue<>();
//		boolean[] visited = new boolean[N + 1];

		// 출발은 항상 1번 그루터
		pq.offer(new Node(1, 0));
		Arrays.fill(fox, Integer.MAX_VALUE);
		fox[1] = 0;

		while (!pq.isEmpty()) {
			Node cn = pq.poll();

//			if(visited[cn.dest]) {
//				continue;
//			}

			if (fox[cn.dest] < cn.value) { // (필수) 효율성!
				continue;
			}

			for (Node next : adj[cn.dest]) {
				if (fox[next.dest] > fox[cn.dest] + next.value) {
					fox[next.dest] = fox[cn.dest] + next.value;
					pq.offer(new Node(next.dest, fox[next.dest]));
				}
			}

//			visited[cn.dest] = true;
		}
	}
}
