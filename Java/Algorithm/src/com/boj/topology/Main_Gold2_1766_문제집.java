package com.boj.topology;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main_Gold2_1766_문제집 {
	
	static int N, M;
	static int[][] pre;
	static int[] checked;
	
	static class Node{
		int pre;
		boolean cant;
		
		Node(int pre, boolean cant){
			this.pre= pre;
			this.cant=cant;
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringBuilder sb = new StringBuilder();

		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // 총 문제의 수, 1 ≤ N ≤ 32,000
		M = Integer.parseInt(st.nextToken()); // 먼저 푸는 것이 좋은 문제에 대한 정보의 수, (1 ≤ M ≤ 100,000)

		pre = new int[M][2];
		for(int i=0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			pre[i][0] = Integer.parseInt(st.nextToken()); // 이 문제는
			pre[i][1] = Integer.parseInt(st.nextToken()); // 이 문제보다 먼저 푸는 것이 좋다.
			
			
		}
		
		for(int i=1;i<=N;i++) {
			if(checked[i] == 0) {
				sb.append(i);
			} else if(checked[i] == 1) {
				
			}
			
			
		}
		
		
		bw.write(sb.toString());
        bw.flush();

        br.close(); 
        bw.close();
	}
}
