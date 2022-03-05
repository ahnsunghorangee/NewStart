package com.cospro.test06;

import java.util.LinkedList;
import java.util.Queue;

class Solution01 {

	static int yLen, xLen;
	static int[][] dirs = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
	static boolean[][] isVisited;
	static Queue<Flower> q;

	static class Flower {
		int y, x, day;

		Flower(int y, int x, int day) {
			this.y = y;
			this.x = x;
			this.day = day;
		}
	}

	public int solution(int n, int[][] garden) { // BFS
		yLen = garden.length;
		xLen = n;

		isVisited = new boolean[yLen][xLen];
		q = new LinkedList<>();

		for (int i = 0; i < yLen; i++) {
			for (int j = 0; j < xLen; j++) {
				if (garden[i][j] == 1) {
					q.offer(new Flower(i, j, 0));
					isVisited[i][j] = true;
				}
			}
		}

		return bfs(garden);
	}

	public static int bfs(int[][] garden) {
		int answer = 0;

		while (!q.isEmpty()) {

			Flower cur = q.poll();

			for (int d = 0; d < 4; d++) {
				int ny = cur.y + dirs[d][0];
				int nx = cur.x + dirs[d][1];
				int nDay = cur.day + 1;

				if (!isPossible(ny, nx)) {
					continue;
				}

				if (!isVisited[ny][nx]) {
					isVisited[ny][nx] = true;
					answer = nDay;
					q.offer(new Flower(ny, nx, nDay));
				}
			}
		}

		return answer;
	}

	public static boolean isPossible(int cy, int cx) {
		return cy < yLen && cy >= 0 && cx < xLen && cx >= 0;
	}

	// 아래는 테스트케이스 출력을 해보기 위한 main 메소드입니다.
	public static void main(String[] args) {
		Solution01 sol = new Solution01();
		int n1 = 3;
		int[][] garden1 = { { 0, 0, 0 }, { 0, 1, 0 }, { 0, 0, 0 } };
		int ret1 = sol.solution(n1, garden1);

		// [실행] 버튼을 누르면 출력 값을 볼 수 있습니다.
		System.out.println("solution 메소드의 반환 값은 " + ret1 + " 입니다.");

		int n2 = 2;
		int[][] garden2 = { { 1, 1 }, { 1, 1 } };
		int ret2 = sol.solution(n2, garden2);

		// [실행] 버튼을 누르면 출력 값을 볼 수 있습니다.
		System.out.println("solution 메소드의 반환 값은 " + ret2 + " 입니다.");

	}
}