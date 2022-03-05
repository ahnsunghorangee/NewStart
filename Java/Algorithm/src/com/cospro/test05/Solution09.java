package com.cospro.test05;

import java.util.LinkedList;
import java.util.Queue;

// 처음부터 구현하기
public class Solution09 {

	static class Node {
		int num, cnt;

		public Node(int num, int cnt) {
			this.num = num;
			this.cnt = cnt;
		}
	}

	static Queue<Node> q;
	static boolean[] isVisited;

	public int solution(int number, int target) {
		// 여기에 코드를 작성해주세요.
		int answer = 0;

		q = new LinkedList<Node>();
		isVisited = new boolean[10001];

		q.offer(new Node(number, 0));
		isVisited[number] = true;

		// BFS
		while (!q.isEmpty()) {
			Node cn = q.poll();
			int num = cn.num;
			int cnt = cn.cnt;

			if (num == target) {
				answer = cnt;
				break;
			}

			if (num + 1 <= 10000 && !isVisited[num + 1]) {
				q.offer(new Node(num + 1, cnt + 1));
				isVisited[num + 1] = true;
			}

			if (num - 1 >= 0 && !isVisited[num - 1]) {
				q.offer(new Node(num - 1, cnt + 1));
				isVisited[num - 1] = true;
			}

			if (num * 2 <= 10000 && !isVisited[num * 2]) {
				q.offer(new Node(num * 2, cnt + 1));
				isVisited[num * 2] = true;
			}

		}

		return answer;
	}

	// 아래는 테스트케이스 출력을 해보기 위한 main 메소드입니다.
	public static void main(String[] args) {
		Solution09 sol = new Solution09();
		int number1 = 5;
		int target1 = 9;
		int ret1 = sol.solution(number1, target1);

		// [실행] 버튼을 누르면 출력 값을 볼 수 있습니다.
		System.out.println("solution 메소드의 반환 값은 " + ret1 + " 입니다.");

		int number2 = 3;
		int target2 = 11;
		int ret2 = sol.solution(number2, target2);

		// [실행] 버튼을 누르면 출력 값을 볼 수 있습니다.
		System.out.println("solution 메소드의 반환 값은 " + ret2 + " 입니다.");
	}
}