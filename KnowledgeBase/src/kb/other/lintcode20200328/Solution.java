package kb.other.lintcode20200328;

public class Solution {

	public static void main(String[] args) {
		int[] nums = {0,1,2,3};
		new Solution().findNum(nums);
	}
	
	/**
	 * 
	 * @param nums
	 * @return 如果输入不符合规范，返回-1；否咋返回正确的缺失数字
	 */
	public int findNum(int[] nums){
		if(nums == null || nums.length == 0){
			return -1;
		}
		if(nums[0] != 0){
			return 0;
		}
		if(nums[nums.length - 1] != nums.length){
			return nums.length;
		}
		int start = 0;
		int end = nums.length - 1;
		while(start <= end && (end - start != 1)){
			int middle = (start + end) >> 1;
			if(nums[middle] == middle){//前半部有序，不缺
				start = middle;
			}else {//前半部分，缺
				end = middle;
			}
		}
		int result = nums[start] + 1;
		return result;
	}
}