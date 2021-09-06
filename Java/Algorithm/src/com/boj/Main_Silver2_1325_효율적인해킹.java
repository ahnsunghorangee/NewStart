package com.boj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;

public class Main_Silver2_1325_효율적인해킹 {

	static int N, M, max;
	static boolean[][] map;
	static Set<Integer> list;

	static class Node {
		int point;
		int cnt; // 몇 다리 건넜는지

		public Node(int point, int cnt) {
			this.point = point;
			this.cnt = cnt;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringBuilder sb = new StringBuilder();

		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		map = new boolean[N][N];
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken()) - 1; // A가
			int b = Integer.parseInt(st.nextToken()) - 1; // B를 신뢰하는 경우, B를 해킹하면 A도 해킹할 수 있다.

			map[b][a] = true;
		}

		max = Integer.MIN_VALUE;
		list = new HashSet<>();
		for (int i = 0; i < N; i++) { // 순서대로 검사
			bfs(i);
		}
		
		for(int node : list) {
			sb.append(node+1).append(" ");
		}

		bw.write(sb.toString());
		bw.flush();

		br.close();
		bw.close();
	}

	private static void bfs(int start) {
		Queue<Node> q = new LinkedList<>();
		boolean[] visited = new boolean[N];
		q.offer(new Node(start, 0));
		visited[start] = true;

		while (!q.isEmpty()) {
			Node cn = q.poll();
			
			if(max < cn.cnt) {
				list = new HashSet<>();
				list.add(start);
				
				max=  cn.cnt;
			} else if(max == cn.cnt) {
				list.add(start);
			}

			for (int i = 0; i < N; i++) {
				if (map[cn.point][i] && !visited[i]) {
					q.offer(new Node(i, cn.cnt + 1));
					visited[i] = true;
				}
			}
		}
	}
}
