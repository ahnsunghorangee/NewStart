package com.cospro;

public class Test {

	public static void main(String[] args) {
		for(int i=0;i<5;i++) {
			System.out.println("AA");
			System.out.println(i);
			
		}
		
		System.out.println("---");
		
		for(int i=0;i<5;++i) {
			System.out.println("AA");
			System.out.println(i);
		}
		
		int idx= 0 ;
		
		System.out.println("---");
		while((idx++) < 5) {
			System.out.println(idx);
		}
		
		int idx2= 0 ;
		System.out.println("---");
		while((++idx2) < 5) {
			System.out.println(idx2);
		}	
		
		int a = 123;
		String b = String.valueOf(a);
		System.out.println(b);
		}
}
