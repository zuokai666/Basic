package kb.other.lintcode20200307;

import java.util.ArrayList;
import java.util.List;

public class Solution {

	public static void main(String[] args) {
		System.out.println(new Solution().getNarcissisticNumbers(4));
	}
	
	public List<Integer> getNarcissisticNumbers(int n) {
        if(n < 1){
            return null;
        }
        int min = (int)Math.pow(10, n - 1);
        if(n == 1){
        	min = 0;
        }
        int max = (int)Math.pow(10, n) - 1;
        List<Integer> result = new ArrayList<>();
        for(int i=min;i<=max;i++){
            int sum = 0;
            int temp = i;
            while(temp != 0){
                sum = sum + (int)Math.pow(temp % 10, n);
                temp = temp / 10;
            }
            if(sum == i){
                result.add(i);
            }
        }
        return result;
    }
}