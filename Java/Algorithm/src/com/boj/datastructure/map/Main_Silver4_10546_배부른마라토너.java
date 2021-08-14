package com.boj.datastructure.map;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main_Silver4_10546_배부른마라토너 {

	static int N;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringBuilder sb = new StringBuilder();

		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());

		Map<String, Integer> map = new HashMap<>();
		for (int i = 0; i < N; i++) {
			String name = br.readLine();
			map.put(name, map.getOrDefault(name, 0) + 1);
		}

//		map.values().stream().forEach(s -> System.out.println(s));

		for (int i = 0; i < N - 1; i++) {
			String name = br.readLine();
			if (map.get(name) - 1 == 0) {
				map.remove(name);
			} else {
				map.put(name, map.get(name) - 1);
			}
		}

		for (String s : map.keySet()) {
			if (map.get(s) != 0) {
				System.out.println(s);
				break;
			}
		}

		bw.write(sb.toString());
		bw.flush();

		br.close();
		bw.close();
	}
}
