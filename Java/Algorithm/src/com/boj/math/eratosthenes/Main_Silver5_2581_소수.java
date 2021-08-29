package com.boj.math.eratosthenes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main_Silver5_2581_소수 {

	static int N, M;
	static boolean[] isPrime;
	static ArrayList<Integer> prime;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringBuilder sb = new StringBuilder();

		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // N 이상
		st = new StringTokenizer(br.readLine());
		M = Integer.parseInt(st.nextToken()); // M 이하

		eratosthenes();
		
		if(prime.size() == 0) {
			sb.append(-1);
		} else {
			int sum = 0;
			for(int i=0;i<prime.size();i++) {
				sum += prime.get(i);
			}
			sb.append(sum).append("\n").append(prime.get(0));
		}
		
		bw.write(sb.toString());
		bw.flush();

		br.close();
		bw.close();
	}

	private static void eratosthenes() {
		isPrime = new boolean[M + 1];

		// prime[0]과 prime[1]은 소수가 아니다.
		isPrime[0] = isPrime[1] = true;

		for (int i = 2; i*i <= M; i++) { // 2부터 자기 자신을 제외한 배수를 제외시킨다.
			if (!isPrime[i]) {
				for (int j = i*i; j <= M; j += i) { // 2의 배수, 3의 배수, ...를 지운다.
					isPrime[j] = true;
				}
			}
		}

		// prime[]이 false이면 소수
		prime = new ArrayList<>();
		for (int i = N; i <= M; i++) {
			if (!isPrime[i]) {
				prime.add(i);
			}
		}

	}
}
