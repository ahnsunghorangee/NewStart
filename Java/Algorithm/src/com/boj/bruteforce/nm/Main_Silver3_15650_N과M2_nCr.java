package com.boj.bruteforce.nm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main_Silver3_15650_N과M2_nCr {
	
	static int N,M;
	static int[] output;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		M = sc.nextInt();
		output = new int[M];
		
		ArrayList<Integer> list = new ArrayList<>();
		list.add(0);
		list.add(1);
		list.add(2);
		System.out.println("list: " + list);
		
		// nCr: 조랍
		nCr(0,1);
	}

	private static void nCr(int cnt,int cur) {
		if(cnt== M) {
			for(int i=0;i<M;i++) {
				System.out.print(output[i]+" ");
			}
			System.out.println();
			return;
		}
		
		for(int i=cur;i<=N;i++) {
			
			output[cnt] = i;
			System.out.println(cnt+" " +cur+" "+i+" " +Arrays.toString(output));
			nCr(cnt+1,i+1);
		}
		
	}
}
