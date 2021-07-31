package com.boj.implementation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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

		List<Integer> list = new ArrayList<>();

		for (int i = 1; i <= N; i++) { // List에 사람 넣기
			list.add(i);
		}

		int idx = K - 1;

		while (list.size() != 1) {
			int num = list.remove(idx);
			sb.append(num).append(", ");
			idx = (idx + K - 1) % list.size(); // ★. 외우자
		}

		sb.append(list.get(0)).append(">"); // 마지막 사람 넣기

		bw.write(sb.toString());
		bw.flush();

		bw.close();
		br.close();
	}
}
