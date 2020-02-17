package kb.other.lintcode20200217;

public class Solution {

	public static void main(String[] args) {
		int[] nums = {6, 1, 2, 7, 9, 3, 4, 5, 10, 8};
		new Solution().kthLargestElement(1, nums);
	}
    
    public int kthLargestElement(int n, int[] nums) {
    	if(nums == null || nums.length == 0 || nums.length < n){
    		return -1;
    	}
    	return kthLargestElementByQuickSort(n, nums, 0, nums.length - 1);
    }
    
	private int kthLargestElementByQuickSort(int n, int[] nums, int s, int e) {
		int ss = s;
		int ee = e;
		int pivot = nums[s];//取首作轴点
		while(ss < ee){
			while((ss < ee) && (pivot <= nums[ee])){
				ee--;
			}
			while((ss < ee) && (nums[ss] <= pivot)){
				ss++;
			}
			if(ss < ee){
				int temp = nums[ss];
				nums[ss] = nums[ee];
				nums[ee] = temp;
			}
		}
		nums[s] = nums[ss];
		nums[ss] = pivot;
		if(nums.length - n == ss){
			return pivot;
		}else if(nums.length - n < ss){
			return kthLargestElementByQuickSort(n, nums, s, ss - 1);
		}else {
			return kthLargestElementByQuickSort(n, nums, ss + 1, e);
		}
	}
}