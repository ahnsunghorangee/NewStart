package com.cospro.test06;


class Solution02 {
    public int solution(int K, String[] words) {
        int answer = 0;
        
        int checkedWord = 0;
        int idx = 0;
        while(checkedWord < words.length) {
        	int curRow = 0;
        	for(;idx<words.length;) {
        		if(curRow + words[idx].length()+1 <= K) {
        			idx++;
        		}
        	}
        }
        
        return answer;
    }

    public static void main(String[] args) {
        Solution02 sol = new Solution02();
        int K = 10;
        String[] words = {new String("nice"), new String("happy"), new String("hello"), new String("world"), new String("hi")};
        int ret = sol.solution(K, words);

        // [실행] 버튼을 누르면 출력 값을 볼 수 있습니다.
        System.out.println("solution 메소의 반환 값은 " + ret + " 입니다.");
    }
}