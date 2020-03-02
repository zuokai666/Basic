package kb.other.lintcode20200302;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Solution {

	public static void main(String[] args) {

	}

	public List<List<Integer>> threeSum(int[] numbers) {
		if(numbers == null || numbers.length < 3){
			return null;
		}
		List<List<Integer>> result = new ArrayList<>();
		for(int firstIndex=0;firstIndex<numbers.length;firstIndex++){
			int firstValue = numbers[firstIndex];
			int[] innerResult = twoSumInternal(numbers, 0 - firstValue, firstIndex + 1, numbers.length - 1);
			if(innerResult != null){
				List<Integer> _result = new ArrayList<>();
				_result.add(firstIndex);
				_result.add(innerResult[0]);
				_result.add(innerResult[1]);
				result.add(_result);
			}
		}
		return result;
	}
	
    public int[] twoSumInternal(int[] numbers, int target, int begin, int end) {
    	if(numbers == null || numbers.length < 2 || (end - begin + 1) < 2){
    		return null;
    	}
    	HashMap<Integer, Integer> map=new HashMap<>();
    	for(int i=begin;i<=end;i++){
    		map.put(numbers[i], i);
    	}
    	for(int i=begin;i<=end;i++){
    		int cha=target-numbers[i];
    		if(map.containsKey(cha)){
    		    int v=map.get(cha);
    		    if(v != i){
    		    	int[] result=new int[2];
    		        result[0]=i;
    			    result[1]=v;
    			    return result;
    		    }
    		}
    	}
		return null;
    }
}