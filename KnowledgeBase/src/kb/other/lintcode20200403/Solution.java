package kb.other.lintcode20200403;

public class Solution {

	public static void main(String[] args) {
		int[] nums = {1,2,4,7,11,15};
		new Solution().find2Num(nums, 20);
	}
	
	public void find2Num(int[] nums, int sum){
		if(nums == null || nums.length < 3){
			return;
		}
		int start = 0;
		int end = nums.length - 1;
		while(start < end){
			if(nums[start] + nums[end] == sum){
				System.out.println(nums[start]);
				System.out.println(nums[end]);
				return;
			}else if(nums[start] + nums[end] < sum){
				start = start + 1;
			}else {
				end = end - 1;
			}
		}
		System.err.println("not found");
	}
}