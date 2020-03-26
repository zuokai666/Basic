package kb.other.lintcode20200326;

/**
 * 60. 搜索插入位置
 * 
 * 给定一个排序数组和一个目标值，如果在数组中找到目标值则返回索引。如果没有，返回到它将会被按顺序插入的位置。你可以假设在数组中无重复元素。
 * 
 * @author King
 * 
 */
public class Solution {
	
	public static void main(String[] args) {
		
	}
	
	public int searchInsert(int[] A, int target) {
        if(A == null){
            return -1;
        }
        if(A.length == 0 || target <= A[0]){
            return 0;
        }
        if(A[A.length - 1] < target){
            return A.length;
        }
        return searchInsertInternal(A, 0, A.length - 1, target);
    }
    
    private int searchInsertInternal(int[] A, int begin, int end, int target) {
        if(end < begin){
            return -1;
        }
        int middle = (begin + end) >> 1;
        if(A[middle] == target){
            return middle;
        }else if(target < A[middle]){
            if(0 <= middle - 1 && A[middle - 1] < target){
                return middle;
            }
            return searchInsertInternal(A, begin, middle - 1, target);
        }else {
            return searchInsertInternal(A, middle + 1, end, target);
        }
    }
}