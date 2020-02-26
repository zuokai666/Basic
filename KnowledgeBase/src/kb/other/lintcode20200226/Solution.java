package kb.other.lintcode20200226;

import java.util.TreeMap;

public class Solution {

	public int[] twoSum(int[] numbers, int target) {
    	int[] result=new int[2];
    	result[0]=-1;
    	result[1]=-1;
    	if(numbers == null || numbers.length < 2){
    		return result;
    	}
    	TreeMap<Integer, Integer> treeMap = new TreeMap<>();//key:数组元素，value:数组下标
    	for(int i=0;i<numbers.length;i++){
    		int temp = target - numbers[i];
    		Integer index = null;
    		if((index = treeMap.get(temp)) != null){
    			if(index != i){
    				result[0] = i > index ? index: i;
    				result[1] = i < index ? index: i;
    				break;
    			}
    		}
    		treeMap.put(numbers[i], i);
    	}
    	return result;
	}
}