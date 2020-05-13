package kb.other.lintcode20200513;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

public class Solution {

	public static void main(String[] args) {
		int[] nums = {-5,0,1,4,5};
		System.out.println(new Solution().threeSum(nums));
	}
	
	public List<List<Integer>> threeSum(int[] numbers) {
        if(numbers == null || numbers.length < 3){
            return null;
        }
        int target = 0;//厉害
        quickSort(numbers, 0, numbers.length - 1);
        System.err.println(Arrays.toString(numbers));
        List<List<Integer>> result = new ArrayList<>();
        TreeSet<String> set = new TreeSet<>();
        int firstIndex = 0;
        int thirdIndex = numbers.length - 1;
        while(firstIndex < thirdIndex){
            int secondIndex = firstIndex + 1;
            while(secondIndex < thirdIndex){
            	int sum = numbers[firstIndex] + numbers[secondIndex] + numbers[thirdIndex];
				if(sum == target){
                    List<Integer> seq = new ArrayList<>(3);
                    seq.add(numbers[firstIndex]);
                    seq.add(numbers[secondIndex]);
                    seq.add(numbers[thirdIndex]);
                    String strSum = numbers[firstIndex] + "" + numbers[secondIndex] + "" + numbers[thirdIndex];
                    if(set.add(strSum)){
                    	result.add(seq);
                    }
                    secondIndex++;
                    thirdIndex--;
                }else if(sum < target){
                	secondIndex++;
                }else {
                	thirdIndex--;
				}
            }
            firstIndex++;
            thirdIndex = numbers.length - 1;//复位
        }
        return result;
    }
    
    private void quickSort(int[] nums, int begin, int end){
        if(end <= begin){
            return;
        }
        int b = begin;
        int e = end;
        int pivot = nums[begin];
        while(b < e){
            while((b < e) && (pivot <= nums[e])){
                e--;
            }
            while((b < e) && (nums[b] <= pivot)){
                b++;
            }
            if(b < e){
                int temp = nums[b];
                nums[b] = nums[e];
                nums[e] = temp;
            }
        }
        nums[begin] = nums[b];
        nums[b] = pivot;
        quickSort(nums, begin, b - 1);
        quickSort(nums, b + 1, end);
    }
}