package com.boj.bfs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_Gold4_14923_미로탈출 {

	static int N, M, Hy, Hx, Ey, Ex;
	static int[][] map;
	static boolean[][][] visited;
	static int[][] dirs = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };

	static class Node {
		int y, x, cnt;
		int usedMagic;

		public Node(int y, int x, int usedMagic, int cnt) {
			super();
			this.y = y;
			this.x = x;
			this.usedMagic = usedMagic;
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

		st = new StringTokenizer(br.readLine()); // 출발 y, x
		Hy = Integer.parseInt(st.nextToken()) - 1;
		Hx = Integer.parseInt(st.nextToken()) - 1;

		st = new StringTokenizer(br.readLine()); // 도착 y, x
		Ey = Integer.parseInt(st.nextToken()) - 1;
		Ex = Integer.parseInt(st.nextToken()) - 1;

		map = new int[N][M];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		visited = new boolean[2][N][M]; // 0: 마법 사용 x, 1: 마법 사용 o
		sb.append(bfs());

		bw.write(sb.toString());
		bw.flush();

		br.close();
		bw.close();
	}

	/*
	 * 개선된 코드) 652ms
	 */
	private static int bfs() {
		Queue<Node> q = new LinkedList<>();
		q.offer(new Node(Hy, Hx, 0, 0));
		visited[0][Hy][Hx] = true; // 시작점 방문 처리

		while (!q.isEmpty()) {
			Node cn = q.poll();

			int cy = cn.y;
			int cx = cn.x;

			if (cy == Ey && cx == Ex) {
				return cn.cnt;
			}

			for (int d = 0; d < 4; d++) {
				int ny = cy + dirs[d][0];
				int nx = cx + dirs[d][1];
				int nMagic = cn.usedMagic;

				if (!boundary(ny, nx)) {
					continue;
				}

				if (map[ny][nx] == 1) {
					if (nMagic == 1) { // 마법을 사용한 경우이면, continue
						continue;
					}
					nMagic = 1; // ★ boolean으로 처리하지 않고 int로 처리해서 visited에서도 값을 이어서 사용
				}

				if (!visited[nMagic][ny][nx]) {
					visited[nMagic][ny][nx] = true;
					q.offer(new Node(ny, nx, nMagic, cn.cnt + 1));
				}
			}
		}
		return -1;
	}

	private static boolean boundary(int ny, int nx) {
		return ny >= 0 && nx >= 0 && ny < N && nx < M;
	}

	/*
	 * 내 풀이) 848ms 마법 사용 유무에 따른 처리 -> 중복 코드 발생
	 */
	static class Node2 {
		int y, x, cnt;
		boolean usedMagic;

		public Node2(int y, int x, boolean usedMagic, int cnt) {
			super();
			this.y = y;
			this.x = x;
			this.usedMagic = usedMagic;
			this.cnt = cnt;
		}
	}

	private static int bfs2() {
		Queue<Node2> q = new LinkedList<>();
		q.offer(new Node2(Hy, Hx, false, 0));
		visited[0][Hy][Hx] = true; // 시작점 방문 처리

		while (!q.isEmpty()) {
			Node2 cn = q.poll();

			int cy = cn.y;
			int cx = cn.x;

			if (cy == Ey && cx == Ex) {
				return cn.cnt;
			}

			if (cn.usedMagic) { // 마법을 사용한 경우
				for (int d = 0; d < 4; d++) {
					int ny = cy + dirs[d][0];
					int nx = cx + dirs[d][1];

					if (!boundary(ny, nx)) {
						continue;
					}
					if (map[ny][nx] == 0) {
						if (!visited[1][ny][nx]) {
							visited[1][ny][nx] = true;
							q.offer(new Node2(ny, nx, true, cn.cnt + 1));
						}
					}

				}
			} else { // 마법을 사용하지 않은 경우
				for (int d = 0; d < 4; d++) {
					int ny = cy + dirs[d][0];
					int nx = cx + dirs[d][1];

					if (!boundary(ny, nx)) {
						continue;
					}

					if (map[ny][nx] == 0) {
						if (!visited[0][ny][nx]) {
							visited[0][ny][nx] = true;
							q.offer(new Node2(ny, nx, false, cn.cnt + 1));
						}
					} else {
						if (!visited[1][ny][nx]) {
							visited[1][ny][nx] = true;
							q.offer(new Node2(ny, nx, true, cn.cnt + 1));
						}
					}

				}
			}
		}
		return -1;
	}
}
