package com.boj.unionfind;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_Silver1_1325_효율적인해킹 {

	static int N, M, max;
	static int[] count;
	static int[] visited;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken()); // N개의 컴퓨터 <= 10,000
		M = Integer.parseInt(st.nextToken()); // M개의 관계, <= 100,000

		max = 0;
		visited = new int[N];
		count = new int[N];
		for (int i = 0; i < N; i++) {
			visited[i] = i;
		}

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			dfs(Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1, 1);
		}
		
		System.out.println(Arrays.toString(visited));
		System.out.println(Arrays.toString(count));

		for (int i = 0; i < N; i++) {
			if (max == visited[i]) {
				System.out.print((i + 1) + " ");
			}
		}

	}

	private static void dfs(int end, int start, int depth) {
		if (visited[end] != start) {
			visited[end] = start;
			if(count[start] < depth) {
				count[start] = depth;
				max = max < depth ? depth : max;
			}
		} else {
			dfs(visited[end], start, depth++);
		}
	}

}
