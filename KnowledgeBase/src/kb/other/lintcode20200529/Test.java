package kb.other.lintcode20200529;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class Test {
	
	public int lengthOfLongestSubstring(String s) {
		Set<Character> set = new HashSet<>();
		int max = 0;
		for(int i=0;i<s.length();i++){
			int sum = 0;
			for(int j=i;j<s.length();j++){
				if(set.contains(s.charAt(j))){
					set.clear();
					break;
				}else {
					sum++;
					set.add(s.charAt(j));
				}
			}
			if(max < sum){
				max = sum;
			}
		}
		return max;
    }
	
	public void stackSorting(Stack<Integer> stk) {
		Stack<Integer> temp = new Stack<>();
		for(int i=1;i<stk.size();i++){
			int time = i;
			while(time != 0){
				temp.push(stk.pop());
				time--;
			}
			Integer element = stk.pop();
			while(!temp.isEmpty()){
				Integer tempElement = temp.pop();
				if(tempElement.compareTo(element) >= 0){
					stk.push(element);
					element = null;// 标记
				}else {
					stk.push(tempElement);
				}
			}
			if(element != null){
				stk.push(element);
			}
			System.out.println(stk);
		}
    }
}