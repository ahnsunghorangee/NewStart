package test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Test {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("src\\\\test\\\\test.txt")));
		
		
		Map<Integer, ArrayList<String>> map = new HashMap<>();
		StringTokenizer st = new StringTokenizer(br.readLine());
		while(br.readLine() != null) {
			st = new StringTokenizer(br.readLine());
			while(st.hasMoreTokens()) {
				String word = st.nextToken();
				
				if(map.get(word.length()) == null) {
					ArrayList<String> list = new ArrayList<>();
					list.add(word);
					map.put(word.length(), list);
				} else {
					ArrayList<String> list = map.get(word.length());
					list.add(word);
					map.put(word.length(), list);
				}
			}
		}
		
		
		System.out.println(map.toString());
	}
	
//	private void problem1() throws IOException {
//		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("src\\\\test\\\\test.txt")));
//		StringTokenizer st = new StringTokenizer(br.readLine());
//		
//		Map<Integer, String> map = new HashMap<>();
//		
//		
//		while(st.hasMoreTokens()) {
//			String word = st.nextToken();
//			
//			if(!map.containsKey(word)) {
//				map.put(1, word); // 처음 발생한 단어의 key는 1
//			}else {
//				map.put(map.getOrDefault(1,word ), word);
//			}
//		}
//		
//		
//		
//		
//	}

}
