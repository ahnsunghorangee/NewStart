package com.boj.string;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Main_Silver4_9012_괄호 { // idea 더 많다. 참조하자

	static int N;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < N; i++) {
			String input = br.readLine();

			Stack<Character> stack = new Stack();

			boolean flag = false;
			for (int j = 0; j < input.length(); j++) {
				char current = input.charAt(j);

				if (stack.isEmpty()) {
					stack.add(current);
				} else {
					char tmp = stack.peek();

					if (tmp == '(') {
						if (current == ')') {
							stack.pop();
						} else {
							stack.add(current);
						}
					} else { // ')'
						sb.append("NO").append("\n");
						flag = true;
						break;
					}
				}
			}

			if (flag) {
				continue;
			}

			if (stack.isEmpty()) {
				sb.append("YES").append("\n");
			} else {
				sb.append("NO").append("\n");
			}
		}

		System.out.println(sb.toString());
	}
}
