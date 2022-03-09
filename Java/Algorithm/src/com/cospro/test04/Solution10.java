package com.cospro.test04;

import java.util.ArrayList;

// 구현
public class Solution10 {

	static boolean[] isPrime;
	static ArrayList<Integer> prime;

	public int solution(int a, int b) {
		// 여기에 코드를 작성해주세요.
		int answer = 0;

		// 소수 구하기 - 에라토스네스의 체
		eratosthenes(a, b);

		for (int i = 0; i < prime.size(); i++) {
			int checkNum = prime.get(i);

			boolean isPossible1 = false, isPossible2 = false;

			if (Math.pow(checkNum, 2) >= a && Math.pow(checkNum, 2) <= b) {
				answer++;
				isPossible1 = true;
			}

			if (Math.pow(checkNum, 3) >= a && Math.pow(checkNum, 3) <= b) {
				answer++;
				isPossible2 = true;
			}

			if (!isPossible1 && !isPossible2) {
				break;
			}
		}

		return answer;
	}

	private static void eratosthenes(int start, int end) {
		isPrime = new boolean[end + 1];

		// prime[0]과 prime[1]은 소수가 아니다.
		isPrime[0] = isPrime[1] = true;

		for (int i = 2; i * i <= end; i++) { // 2부터 자기 자신을 제외한 배수를 제외시킨다.
			if (!isPrime[i]) {
				for (int j = i * i; j <= end; j += i) { // 2의 배수, 3의 배수, ...를 지운다.
					isPrime[j] = true;
				}
			}
		}

		// prime[]이 false이면 소수
		prime = new ArrayList<>();
		for (int i = 2; i <= end; i++) {
			if (!isPrime[i]) {
				prime.add(i);
			}
		}
	}

	// 아래는 테스트케이스 출력을 해보기 위한 main 메소드입니다.
	public static void main(String[] args) {
		Solution10 sol = new Solution10();
		int a = 6;
		int b = 30;
		int ret = sol.solution(a, b);

		// [실행] 버튼을 누르면 출력 값을 볼 수 있습니다.
		System.out.println("solution 메소드의 반환 값은 " + ret + " 입니다.");
	}
}