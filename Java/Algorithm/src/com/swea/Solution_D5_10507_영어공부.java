package com.swea;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Solution_D5_10507_영어공부 {

	static int T,N,P, ans;
	static int[] date;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("src\\com\\swea\\testcase\\10507.txt")));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringBuilder sb = new StringBuilder();

		StringTokenizer st = new StringTokenizer(br.readLine());
		T = Integer.parseInt(st.nextToken());
		
		for(int t=1;t<=T;t++) {
			sb.append("#").append(t).append(" ");
			
			st = new StringTokenizer(br.readLine());
			// 1 ≤ n, p ≤ 200,000
			N = Integer.parseInt(st.nextToken()); // 공부한 날의 수
			P = Integer.parseInt(st.nextToken()); // 수정 가능 날의 수
			
			st = new StringTokenizer(br.readLine());
			date = new int[N];
			for(int i=0;i<N;i++) {
				date[i] = Integer.parseInt(st.nextToken());
			}
			
			ans = 0;
			
			
			
			sb.append(ans).append("\n");
		}
		
		bw.write(sb.toString());
        bw.flush();

        br.close(); 
        bw.close();
	}
}
