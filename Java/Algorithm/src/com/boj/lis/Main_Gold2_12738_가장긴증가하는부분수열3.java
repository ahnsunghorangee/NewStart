package com.boj.lis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_Gold2_12738_가장긴증가하는부분수열3 {
	static int N;
	static int[] arr;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		arr = new int[N];
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}

		// LIS 준비
		int[] LIS = new int[N];
		int size = 0;
		LIS[size++] = arr[0];

		for (int i = 1; i < N; i++) {
			if (LIS[size - 1] < arr[i]) { // LIS의 마지막이랑만 비교, 들어오는 값이 더 크면
				LIS[size++] = arr[i]; // 추가만 한다.
			} else { // 들어오는 값이 더 작으면
				int temp = Arrays.binarySearch(LIS, 0, size, arr[i]); // LIS 배열에서 0부터 size-1까지 arr[i]를 찾는다.
				if (temp < 0) { // 리턴이 음수라는 의미: -삽입합 위치 -1로 리턴
					temp = -temp - 1;
				}
				LIS[temp] = arr[i];
			}
		}
		System.out.println(Arrays.toString(LIS));
		System.out.println(size);
	}

}