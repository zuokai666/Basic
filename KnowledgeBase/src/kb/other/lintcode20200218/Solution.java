package kb.other.lintcode20200218;

public class Solution {
	
	public static void main(String[] args) {
		int[] nums = {9,9,9,8,9,8,7,9,8,8,8,9,8,9,8,8,6,9};
		int k = 9;
		new Solution().partitionArray(nums, k );
	}
	
	public int partitionArray(int[] nums, int k) {
		if(nums == null){
			return -1;
		}
		if(nums.length == 0){
			return 0;
		}
		return partitionArrayInternal(nums, k, 0, nums.length - 1);
	}
	
	private int partitionArrayInternal(int[] nums, int k, int s, int e){
		if(e <= s){
			return -1;
		}
		int ss = s;
		int ee = e;
		int pivot = k;
		while(ss < ee){
			while((ss < ee) && (pivot <= nums[ee])){//3 2 1 
				ee--;
			}
			while((ss < ee) && (nums[ss] < pivot)){
				ss++;
			}
			if(ss < ee){
				int temp = nums[ss];
				nums[ss] = nums[ee];
				nums[ee] = temp;
			}
		}
		return ss + 1;
	}
}