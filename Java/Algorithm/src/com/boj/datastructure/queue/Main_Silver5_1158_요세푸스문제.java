package com.boj.datastructure.queue;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_Silver5_1158_요세푸스문제 {

	static int N, K;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringBuilder sb = new StringBuilder();

		sb.append("<");

		StringTokenizer st = new StringTokenizer(br.readLine());

		// 1 ≤ K ≤ N ≤ 5,000
		N = Integer.parseInt(st.nextToken()); // 1번부터 N번까지 N명
		K = Integer.parseInt(st.nextToken()); // 원 모양으로 K간격으로 빼내기

		Queue<Integer> q = new LinkedList<>();

		for (int i = 1; i <= N; i++) { // 1. Queue에 사람 넣기
			q.offer(i);
		}

		int cnt = 0;

		while (!q.isEmpty()) {
			int num = q.poll();

			if (cnt == K - 1) { // 2. K번째이면 빼내기
				if (q.isEmpty()) {
					sb.append(num);
				} else {
					sb.append(num).append(", ");
					cnt = 0;
				}
			} else {
				q.offer(num);
				cnt++;
			}
		}

		sb.append(">");

		bw.write(sb.toString());
		bw.flush();

		bw.close();
		br.close();
	}
}
