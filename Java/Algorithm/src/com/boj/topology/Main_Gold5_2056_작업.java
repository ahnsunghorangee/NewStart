package com.boj.topology;

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

public class Main_Gold5_2056_작업 {

	static int N, ans;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringBuilder sb = new StringBuilder();

		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // 수행해야 할 작업 개수, 3 ≤ N ≤ 10000

		// 작업이 하나도 없는 작업이 반드시 하나 이상 존재한다. (1번 작업이 항상 그러하다)
		int[] time = new int[N + 1];
		int[] cnt = new int[N + 1];
		List<List<Integer>> list = new ArrayList<>();
		for (int i = 0; i <= N; i++) {
			list.add(new ArrayList<>());
		}

		int[] topologyPoint = new int[N + 1];
		for (int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken()); // 작업 걸리는 시간
			int b = Integer.parseInt(st.nextToken()); // 이전에 선행되어야 할 작업의 수
			time[i] = a;
			cnt[i] = b;
			for (int j = 0; j < b; j++) {
				int c = Integer.parseInt(st.nextToken());
				list.get(i).add(c);
				topologyPoint[c]++;
			}
		}

		Queue<Integer> q = new LinkedList<>();
		for (int i = 1; i <= N; i++) {
			if (topologyPoint[i] == 0) {
				q.offer(i);
			}
		}

		ans = 0;
		for (int i = 1; i <= N; i++) {
			int v = q.poll();
			ans += time[v];

			for (int next : list.get(v)) {
				cnt[next]--;

				if (cnt[next] == 0) {
					q.offer(next);
				}
			}
		}

		sb.append(ans);

		bw.write(sb.toString());
		bw.flush();

		br.close();
		bw.close();
	}
}
