package kb.other.lintcode20200401;

public class Solution {
	
	public static void main(String[] args) {
		int[] nums = {2, 8, 3, 6, 3, 6, 5, 5};
		new Solution().print2Nums(nums );
	}
	
	public void print2Nums(int[] nums){
		if(nums == null || nums.length < 2){
			return;
		}
		int xor = 0;
		for(int i=0;i<nums.length;i++){
			xor = xor ^ nums[i];
		}
		System.err.println(Integer.toBinaryString(xor));// 1010
		int diff = 1;
		while((diff & xor) == 0){
			diff = diff << 1;
		}
		System.err.println(Integer.toBinaryString(diff));// 10
		int xor_copy = xor;
		for(int i=0;i<nums.length;i++){
			if((nums[i] & diff) == 0){
				xor_copy = xor_copy ^ nums[i];
			}
		}
		System.err.println(xor_copy);// 2
		System.err.println(xor_copy ^ xor);// 8
	}
}