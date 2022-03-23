package com.boj.string;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;

public class Main_Silver5_1427_소트인사이드 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 풀이1)
		char[] input = br.readLine().toCharArray();
		Arrays.sort(input);

		StringBuilder sb = new StringBuilder();
		for (int i = input.length - 1; i >= 0; i--) {
			sb.append(input[i]);
		}
		System.out.println(sb.toString());

		/* Collections.reverseSort() 사용시 오브젝트로 */
		// 풀이2)
		String s = br.readLine();
		Integer[] arr = new Integer[s.length()];
		for (int i = 0; i < s.length(); i++) {
			arr[i] = Character.getNumericValue(s.charAt(i));
		}

		Arrays.sort(arr, Collections.reverseOrder());

		StringBuilder sb2 = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			sb2.append(arr[i]);
		}
		System.out.print(sb2);
	}
}
