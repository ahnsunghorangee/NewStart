package com.cospro.test01;

import java.util.LinkedList;
import java.util.Queue;

// 구현
public class Solution02 {

	static int[][] dirs = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };

	class Node {
		int y, x, d;

		public Node(int y, int x, int d) {
			this.y = y;
			this.x = x;
			this.d = d;
		}
	}

	public int solution(int n) {
		// Write code here.
		int answer = 0;

		int[][] map = new int[n][n];
		int num = 1;

		Queue<Node> q = new LinkedList<>();
		q.offer(new Node(0, 0, 0));
		map[0][0] = num++;

		while (!q.isEmpty()) {
			Node cn = q.poll();
			int cy = cn.y;
			int cx = cn.x;
			int cd = cn.d;

			int v = 1;
			while (true) {
				int ny = cy + dirs[cd][0] * v;
				int nx = cx + dirs[cd][1] * v;

				if (!isBoundary(ny, nx, n) || map[ny][nx] != 0) {
					int d = (cd + 1) % 4;
					int y = ny - dirs[cd][0] + dirs[d][0];
					int x = nx - dirs[cd][1] + dirs[d][1];

					if (map[y][x] == 0) {
						q.offer(new Node(ny - dirs[cd][0], nx - dirs[cd][1], d));
					}

					break;
				}

				map[ny][nx] = num++;
				v++;
			}

		}

		for (int i = 0; i < n; i++) {
			answer += map[i][i];
		}
		return answer;
	}

	public boolean isBoundary(int y, int x, int n) {
		return y >= 0 && y < n && x >= 0 && x < n;
	}

	// The following is main method to output testcase.
	public static void main(String[] args) {
		Solution02 sol = new Solution02();
		int n1 = 4;
		int ret1 = sol.solution(n1);

		// Press Run button to receive output.
		System.out.println("Solution: return value of the method is " + ret1 + " .");

		int n2 = 5;
		int ret2 = sol.solution(n2);

		// Press Run button to receive output.
		System.out.println("Solution: return value of the method is " + ret2 + " .");
	}
}