package com.boj.lis;

import java.util.Arrays;

public class Basic1_이진탐색 {
	public static void main(String[] args) {
		int[] a = { 1, 8, 9, 2, 4, 3, 6, 11, 7, 10, 14, 5 };
		int[] c = new int[a.length]; // LIS로 사용 가능한 숫자를 저장
		int size = 0; // LIS의 개수 관리할 변수

		c[size++] = a[0]; // 첫번쨰 숫자는 그냥 넣어놓기
		for (int i = 1; i < a.length; i++) {
			System.out.println(i + ": " + Arrays.toString(c));

			// c배열의 마지막 숫자와 수열값을 비교
			if (c[size - 1] < a[i]) {
				c[size++] = a[i]; // 맨 뒤에 붙인다.
			} else { // c[size - 1] >= a[i]
				// 알고리즘의 시간을 줄이는 핵심! LIS의 마지막 숫자보다 크지 않으면 LIS 값을 업데이트 한다.(이진탐색: O(logn))
				// 찾으면 배열에서 찾은 위치 음수-1로 리턴, 못 찾으면 삽입할 위치를 리턴
				int temp = binarySearch0(c, 0, size, a[i]); // c배열에서 0번부터 size-1까지 a[i]가 있는지 검사를 한다.
				System.out.println("temp: "+temp);
				if (temp < 0) {
					temp = -temp - 1;
				}
				System.out.println("temp :" + temp + " a[i]: " + a[i]);
				c[temp] = a[i]; // 수열의 값을 LIS에 삽입 할 위치에 덮어쓰기
				System.out.println(" c: " + Arrays.toString(c));
			}
		}
		System.out.println(Arrays.toString(c)); // 여기서 만들어지는 c는 LIS가 아니다.
		System.out.println("LIS 개수: " + size);

	}

	private static int binarySearch0(int[] a, int fromIndex, int toIndex, int key) {
		int low = fromIndex;
		int high = toIndex - 1;

		while (low <= high) {
			int mid = (low + high)/2;
			int midVal = a[mid];

			if (midVal < key)
				low = mid + 1;
			else if (midVal > key)
				high = mid - 1;
			else
				return mid; // key found
		}
		return -(low + 1); // key not found.
	}
}
