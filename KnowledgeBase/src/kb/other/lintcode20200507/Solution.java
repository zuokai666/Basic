package kb.other.lintcode20200507;

import java.util.Arrays;

public class Solution {
	
	public static void main(String[] args) {
		int k = 3;
		int[] nums = {3,4,1,2,5};
		new Solution().kthSmallest(k, nums);
	}
	
    /**
     * @param k: An integer
     * @param nums: An integer array
     * @return: kth smallest element
     */
    public int kthSmallest(int k, int[] nums) {
        if(nums == null || nums.length == 0){
            return -1;
        }
        if(k < 0 || nums.length < k){
            return -1;
        }
        return kthSmallestInternal(nums, 0, nums.length - 1, k - 1);
    }
    
    private int kthSmallestInternal(int[] nums, int begin, int end, int k){
        if(end <= begin){
            return -1;
        }
        int b = begin;
        int e = end;
        int pivot = nums[b];
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
            System.out.println(Arrays.toString(nums));
        }
        nums[begin] = nums[b];
        nums[b] = pivot;
        System.out.println(Arrays.toString(nums));
        if(b == k){
            return nums[k];
        }else if(b < k){
            return kthSmallestInternal(nums, b + 1, end, k);
        }else {
            return kthSmallestInternal(nums, begin, b - 1, k);
        }
    }
}