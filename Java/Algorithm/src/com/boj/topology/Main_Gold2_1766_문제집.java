package com.boj.topology;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_Gold2_1766_문제집 {
	
	static class Node implements Comparable<Node>{
		int num;
		
		public Node(int num) {
			this.num = num;
		}
		
		@Override
		public int compareTo(Node o) {
			return Integer.compare(this.num, o.num);
		}
	}
	
	static int N, M;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringBuilder sb = new StringBuilder();

		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // 총 문제의 수, 1 ≤ N ≤ 32,000
		M = Integer.parseInt(st.nextToken()); // 먼저 푸는 것이 좋은 문제에 대한 정보의 수, (1 ≤ M ≤ 100,000)

		List<List<Integer>> list = new ArrayList<>();
		for(int i=0;i<=N;i++) {
			list.add(new ArrayList<>());
		}
		
		int[] cnt = new int[N+1];
		
		for(int i=0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			list.get(a).add(b);
			cnt[b]++;
		}
		
		PriorityQueue<Node> pq = new PriorityQueue<>();
		
		for(int i=1;i<=N;i++) {
			if(cnt[i] == 0) {
				pq.offer(new Node(i));
			}
		}
		
		for(int i=1;i<=N;i++) {
			int v = pq.poll().num;
			sb.append(v).append(" ");
			
			for(int next : list.get(v)) {
				cnt[next]--;
				
				if(cnt[next] == 0) {
					pq.add(new Node(next));
				}
			}
		}
		
		
		bw.write(sb.toString());
        bw.flush();

        br.close(); 
        bw.close();
	}
}
