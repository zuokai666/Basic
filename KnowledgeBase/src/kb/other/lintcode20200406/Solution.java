package kb.other.lintcode20200406;

@Deprecated
public class Solution {

	public static void main(String[] args) {
		int[] nums = {2,3,4,2,6,2,5,1};
		int windowSize = 3;
		new Solution().search(nums, windowSize);
	}
	
	public void search(int[] nums, int windowSize){
		if(nums == null || nums.length == 0 || nums.length < windowSize){
			return;
		}
		int maxIndex = 0;
		int maxValue = nums[maxIndex];
		for(int i=0;i<nums.length - windowSize + 1;i++){
			if(i == 0){
				for(int j=i;j<i+windowSize;j++){
					if(maxValue <= nums[j]){//就算有相同值，也要记录后面的
						maxValue = nums[j];
						maxIndex = j;
					}
				}
				System.out.println(maxValue);
			}else {
				if(maxIndex == (i - 1)){//正好移除最大值
					System.err.println("err");
				}else {
					if(maxValue <= nums[i + windowSize - 1]){
						maxValue = nums[i + windowSize - 1];
						maxIndex = i + windowSize - 1;
					}else {
						// do nothing.
					}
				}
				System.out.println(maxValue);
			}
		}
	}
}