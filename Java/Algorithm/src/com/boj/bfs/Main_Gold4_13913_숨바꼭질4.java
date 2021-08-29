package com.boj.bfs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main_Gold4_13913_숨바꼭질4 {

	static int N, K;
	static boolean[] visited;
	static int[] path;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringBuilder sb = new StringBuilder();

		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // 수빈, 0 ≤ N ≤ 100,000
		K = Integer.parseInt(st.nextToken()); // 동생, 0 ≤ K ≤ 100,000

		visited = new boolean[100001]; // 0 ~ 100000
		path = new int[100001]; // ★ 이전에 출발한 점 저장
		Arrays.fill(path, -1);

		sb.append(bfs()).append("\n");

		Stack<Integer> stack = new Stack<Integer>();
		int temp = K;
		while (temp != -1) {
			stack.add(temp);
			temp = path[temp];
		}

		while (!stack.isEmpty()) {
			sb.append(stack.pop()).append(" ");
		}

		bw.write(sb.toString());
		bw.flush();

		br.close();
		bw.close();
	}

	private static int bfs() {
		Queue<int[]> q = new LinkedList<>();
		visited[N] = true;
		q.offer(new int[] { N, 0 }); // {현재좌표, 카운트}

		while (!q.isEmpty()) {
			int[] cur = q.poll();

			if (cur[0] == K) {
				return cur[1];
			}

			int nx = cur[0] - 1;
			if (nx >= 0 && !visited[nx]) {
				visited[nx] = true;
				path[nx] = cur[0];
				q.offer(new int[] { nx, cur[1] + 1 });
			}

			nx = cur[0] + 1;
			if (nx <= 100000 && !visited[nx]) {
				visited[nx] = true;
				path[nx] = cur[0];
				q.offer(new int[] { nx, cur[1] + 1 });
			}

			nx = cur[0] * 2; // 순간이동
			if (nx <= 100000 && !visited[nx]) {
				visited[nx] = true;
				path[nx] = cur[0];
				q.offer(new int[] { nx, cur[1] + 1 });
			}

		}

		return -1;
	}
}
