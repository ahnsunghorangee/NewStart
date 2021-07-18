package com.boj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main_Silver1_13910_개업 {
	
	static int N,M;
	static int[] pan;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringBuilder sb = new StringBuilder();

		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // 주문 받은 짜장면 수, 1 <= N <= 10,000
		M = Integer.parseInt(st.nextToken()); // 웍의 개수, 1 <= M <= 100
		
		pan = new int[M];
		st = new StringTokenizer(br.readLine());
		pan[0] = Integer.parseInt(st.nextToken());
		pan[1] = Integer.parseInt(st.nextToken());
		
		

		bw.write(sb.toString());
        bw.flush();

        br.close(); 
        bw.close();
	}
}
