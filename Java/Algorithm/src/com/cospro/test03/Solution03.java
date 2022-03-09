package com.cospro.test03;

import java.util.LinkedList;
import java.util.Queue;

// 구현
public class Solution03 {

	static class Node {
		int y, x, d;

		public Node(int y, int x, int d) {
			this.y = y;
			this.x = x;
			this.d = d; // 방향
		}
	}

	static int[][] dirs = { { 1, 1 }, { -1, 1 }, { 1, -1 }, { -1, -1 } };
	static boolean[][] isImpossible;

	public int solution(String[] bishops) {
		// 여기에 코드를 작성해주세요.
		int answer = 0;
		isImpossible = new boolean[8][8];

		for (int i = 0; i < bishops.length; i++) {
			int sy = bishops[i].charAt(0) - 'A';
			int sx = bishops[i].charAt(1) - '1';
			isImpossible[sy][sx] = true;
			checked(sy, sx);
		}

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (!isImpossible[i][j]) {
					answer++;
				}
			}
		}

		return answer;
	}

	private static void checked(int y, int x) {
		Queue<Node> q = new LinkedList<>();
		q.offer(new Node(y, x, 0));
		q.offer(new Node(y, x, 1));
		q.offer(new Node(y, x, 2));
		q.offer(new Node(y, x, 3));

		while (!q.isEmpty()) {
			Node cn = q.poll();

			for (int d = 0; d < 4; d++) {
				int ny = cn.y + dirs[cn.d][0];
				int nx = cn.x + dirs[cn.d][1];

				if (boundary(ny, nx)) {
					isImpossible[ny][nx] = true;
					q.offer(new Node(ny, nx, cn.d));
				}
			}
		}
	}

	private static boolean boundary(int y, int x) {
		return y >= 0 && y < 8 && x >= 0 && x < 8;
	}

	// 아래는 테스트케이스 출력을 해보기 위한 main 메소드입니다.
	public static void main(String[] args) {
		Solution03 sol = new Solution03();
		String[] bishops1 = { new String("D5") };
		int ret1 = sol.solution(bishops1);

		// [실행] 버튼을 누르면 출력 값을 볼 수 있습니다.
		System.out.println("solution 메소드의 반환 값은 " + ret1 + " 입니다.");

		String[] bishops2 = { new String("D5"), new String("E8"), new String("G2") };
		int ret2 = sol.solution(bishops2);

		// [실행] 버튼을 누르면 출력 값을 볼 수 있습니다.
		System.out.println("solution 메소드의 반환 값은 " + ret2 + " 입니다.");
	}
}
