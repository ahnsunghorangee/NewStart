package com.cospro.programmers.part03;

import java.util.LinkedList;
import java.util.Queue;

// 구현
public class Solution01 {

	static int[][] dirs = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };

	public int solution(int[][] garden) {
		// 여기에 코드를 작성해주세요.
		int answer = 0;
		int cnt = 0;

		Queue<int[]> q = new LinkedList<>();

		for (int i = 0; i < garden.length; i++) {
			for (int j = 0; j < garden[i].length; j++) {
				if (garden[i][j] == 1) {
					q.offer(new int[] { i, j });
					cnt++;
				}
			}
		}

		while (!q.isEmpty() && cnt < garden.length * garden.length) {
			int size = q.size();

			while (size > 0) {
				int[] cn = q.poll();

				for (int d = 0; d < 4; d++) {
					int ny = cn[0] + dirs[d][0];
					int nx = cn[1] + dirs[d][1];
					if (!inBoundary(ny, nx, garden.length)) {
						continue;
					}

					if (garden[ny][nx] == 0) {
						garden[ny][nx] = 1;
						q.offer(new int[] { ny, nx });
						cnt++;
					}
				}
				size--;
			}
			answer++;
		}

		return answer;
	}

	public boolean inBoundary(int y, int x, int n) {
		return y >= 0 && y < n && x >= 0 && x < n;
	}

	// 아래는 테스트케이스 출력을 해보기 위한 main 메소드입니다.
	public static void main(String[] args) {
		Solution01 sol = new Solution01();
		int[][] garden1 = { { 0, 0, 0 }, { 0, 1, 0 }, { 0, 0, 0 } };
		int ret1 = sol.solution(garden1);

		// [실행] 버튼을 누르면 출력 값을 볼 수 있습니다.
		System.out.println("solution 메소드의 반환 값은 " + ret1 + " 입니다.");

		int[][] garden2 = { { 1, 1 }, { 1, 1 } };
		int ret2 = sol.solution(garden2);

		// [실행] 버튼을 누르면 출력 값을 볼 수 있습니다.
		System.out.println("solution 메소드의 반환 값은 " + ret2 + " 입니다.");

	}
}