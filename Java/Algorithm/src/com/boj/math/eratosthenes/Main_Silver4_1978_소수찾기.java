package com.boj.math.eratosthenes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_Silver4_1978_소수찾기 {

	static int N, max;
	static int[] input;
	static boolean[] isPrime;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());

		StringTokenizer st = new StringTokenizer(br.readLine());
		input = new int[N];
		for (int i = 0; i < N; i++) {
			input[i] = Integer.parseInt(st.nextToken());
		}

		Arrays.sort(input);
		max = input[N - 1];

		eratosthenes();

		int answer = 0;
		for (int i = 0; i < N; i++) {
			if (!isPrime[input[i]]) {
				answer++;
			}
		}

		System.out.println(answer);
	}

	private static void eratosthenes() {
		isPrime = new boolean[max + 1];

		isPrime[0] = isPrime[1] = true;

		for (int i = 2; i * i <= max; i++) {
			if (!isPrime[i]) {
				for (int j = i * i; j <= max; j += i) {
					isPrime[j] = true;
				}
			}
		}
	}

}
