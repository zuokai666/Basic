package kb.other.lintcode20200219;

public class Solution {

	public static void main(String[] args) {
		char[] aaa = {'a','b','A','c','D'};
		new Solution().sortLetters(aaa);
		System.err.println(aaa);
	}
	
	public void sortLetters(char[] arr) {
		int s = 0;
		int e = arr.length - 1;
		while(s < e){
			while((s < e) && (isUp(arr[e]))){
				e--;
			}
			while((s < e) && (isLow(arr[s]))){
				s++;
			}
			if(s < e){
				char temp = arr[s];
				arr[s] = arr[e];
				arr[e] = temp;
			}
		}
	}
	
	private boolean isLow(char c){
		if('a' <= c && c <= 'z'){
			return true;
		}else {
			return false;
		}
	}
	
	private boolean isUp(char c){
		if('A' <= c && c <= 'Z'){
			return true;
		}else {
			return false;
		}
	}
}