package com.boj.datastructure.deque;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main_Silver4_10866_덱 {

	static int N; // 명령의 수, 1 <= N <= 10,000
	static Deque<Integer> dq;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringBuilder sb = new StringBuilder();
		
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		dq = new ArrayDeque<>();

		for (int n = 0; n < N; n++) {
			st = new StringTokenizer(br.readLine());
			String command = st.nextToken();
			

			if (command.equals("push_front")) {
				int num = Integer.parseInt(st.nextToken());
				dq.addFirst(num);
			} else if (command.equals("push_back")) {
				int num = Integer.parseInt(st.nextToken());
				dq.addLast(num);
			} else if (command.equals("pop_front")) {
				if(dq.size() == 0) {
					sb.append(-1).append("\n");
				} else {
					sb.append(dq.pollFirst()).append("\n");
				}
			} else if (command.equals("pop_back")) {
				if(dq.isEmpty()) {
					sb.append(-1).append("\n");
				} else {
					sb.append(dq.pollLast()).append("\n");
				}
			} else if (command.equals("size")) {
				sb.append(dq.size()).append("\n");
			} else if (command.equals("empty")) {
				if(dq.isEmpty()) {
					sb.append(1).append("\n");
				} else {
					sb.append(0).append("\n");
				}
			} else if (command.equals("front")) {
				if(dq.isEmpty()) {
					sb.append(-1).append("\n");
				} else {
					sb.append(dq.peekFirst()).append("\n");
				}
			} else if (command.equals("back")) {
				if(dq.isEmpty()) {
					sb.append(-1).append("\n");
				} else {
					sb.append(dq.peekLast()).append("\n");
				}
			}
			
		}
		
		bw.write(sb.toString());
		bw.flush();

        br.close(); 
        bw.close();
	}
}
