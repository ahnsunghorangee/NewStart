package com.boj.implementation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main_Bronze1_10988_팰린드롬인지확인하기 {

	public static void main(String[] args) throws IOException {
		// 풀이1. 124ms
		/*
		BufferedReader br2 = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb2 = new StringBuilder(br2.readLine());

		if (sb2.toString().equals(sb2.reverse().toString())) {
			System.out.println("1");
		} else {
			System.out.println("0");
		}
		*/

		// 풀이2. 120ms
		/*
		BufferedReader br3 = new BufferedReader(new InputStreamReader(System.in));

		String a = br3.readLine();
		int result = 1;

		int i;
		for (i = 0; i < a.length(); i++) {
			char A = a.charAt(i);
			char B = a.charAt(a.length() - 1 - i);

			if (A != B) {
				result = 0;
			}
		}
		System.out.println(result);
		*/

		// 내 풀이, 184ms
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringBuilder sb = new StringBuilder();

		String in = br.readLine();

		int len = in.length();

		boolean isTrue = false;

		if (len % 2 == 0) { // 길이가 짝수일 때
			String a = in.substring(0, len / 2);
			String b = "";
			for (int i = len - 1; i >= len / 2; i--) {
				b += in.charAt(i);
			}

			System.out.println(a);
			System.out.println(b);

			if (a.equals(b)) {
				isTrue = true;
			}
		} else { // 길이가 홀수일 때
			String a = in.substring(0, len / 2 + 1);
			String b = "";
			for (int i = len - 1; i >= len / 2; i--) {
				b += in.charAt(i);
			}

			System.out.println(a);
			System.out.println(b);

			if (a.equals(b)) {
				isTrue = true;
			}
		}

		sb.append(isTrue ? 1 : 0);

		bw.write(sb.toString());
		bw.flush();

		br.close();
		bw.close();
	}
}
