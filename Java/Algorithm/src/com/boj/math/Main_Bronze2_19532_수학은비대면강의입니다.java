package com.boj.math;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main_Bronze2_19532_수학은비대면강의입니다 {

	static int a, b, c, d, e, f; // -999 <= a,b,c,d,e,f <= 999

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringBuilder sb = new StringBuilder();

		StringTokenizer st = new StringTokenizer(br.readLine());

		// 연립 일차방정식
		a = Integer.parseInt(st.nextToken());
		b = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());
		d = Integer.parseInt(st.nextToken());
		e = Integer.parseInt(st.nextToken());
		f = Integer.parseInt(st.nextToken());

		
		
		
		// 가감법
		int x = (c * e - b * f) / (a * e - b * d);
		int y = (c * d - a * f) / (b * d - a * e);

		sb.append(x).append(" ").append(y);

		bw.write(sb.toString());
		bw.flush();

		br.close();
		bw.close();
	}

}
