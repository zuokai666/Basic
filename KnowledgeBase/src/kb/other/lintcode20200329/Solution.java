package kb.other.lintcode20200329;

public class Solution {

	public static void main(String[] args) {
		int[] nums = {0,3,4,5,6};
		new Solution().search(nums );
	}
	
	public int search(int[] nums){
		if(nums == null || nums.length == 0){
			return -1;
		}
		if(nums[0] == 0){
			return 0;
		}
		if(nums[nums.length - 1] == nums.length - 1){
			return nums.length - 1;
		}
		int start = 0;
		int end = nums.length - 1;
		while(start <= end){
			int middle = (start + end) >> 1;
			if(nums[middle] == middle){
				return middle;
			}else if(nums[middle] < middle){
				start = middle + 1;
			}else {
				end = middle - 1;
			}
		}
		return -1;
	}
}