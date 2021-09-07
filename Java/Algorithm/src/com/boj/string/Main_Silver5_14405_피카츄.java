package com.boj.string;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main_Silver5_14405_피카츄 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringBuilder sb = new StringBuilder();

		String in = br.readLine();

		int idx = 0;
		boolean isImpossible = false;

		if (in.length() == 1) {
			isImpossible = true;
		} else {
			while (idx < in.length()) {
				if (in.charAt(idx) == 'p') {
					if (idx + 1 < in.length()) {
						if (in.charAt(idx + 1) == 'i') {
							idx = idx + 2;
							continue;
						} else {
							isImpossible = true;
							break;
						}
					} else {
						isImpossible = true;
						break;
					}

				} else if (in.charAt(idx) == 'k') {
					if (idx + 1 < in.length()) {
						if (in.charAt(idx + 1) == 'a') {
							idx = idx + 2;
							continue;
						} else {
							isImpossible = true;
							break;
						}
					} else {
						isImpossible = true;
						break;
					}

				} else if (in.charAt(idx) == 'c') {
					if (idx + 2 < in.length()) {
						if (in.charAt(idx + 1) == 'h' && in.charAt(idx + 2) == 'u') {
							idx = idx + 3;
							continue;
						} else {
							isImpossible = true;
							break;
						}
					} else {
						isImpossible = true;
						break;
					}

				} else {
					isImpossible = true;
					break;
				}
			}
		}

		if (isImpossible) {
			sb.append("NO");
		} else {
			sb.append("YES");
		}

		bw.write(sb.toString());
		bw.flush();

		br.close();
		bw.close();
	}
}
