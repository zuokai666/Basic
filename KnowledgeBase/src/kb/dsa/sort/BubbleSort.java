package kb.dsa.sort;

/**
 * 冒泡算法：算法稳定
 * 
 * 最好时间复杂度O(n) 和输入数据相关
 * 最差时间复杂度O(n^2)
 * 
 * 
 * 
 * @author King
 *
 */
public class BubbleSort {
	
	//时间复杂度O(n^2)原地排序，没有空间复杂度，基于交换，所以有大量的1次比较，3次赋值基操
	//暴力算法，无任何优化
	public void sortV1(int[] arr){
		for(int i=0;i<arr.length;i++){
			for(int j=0;j<arr.length-i-1;j++){//-i是减而治之策略的提现，前无序，后有序，每次循环减少一个规模
											  //-1是是为了下方有j+1，防止数组越界
				if(arr[j+1] < arr[j]){//升序-窗口滑动，不加=，保证算法稳定性
					int temp = arr[j+1];
					arr[j+1] = arr[j];
					arr[j] = temp;
				}
			}
		}
	}
	
	/**
	 * 最好时间复杂度O(n) 和输入数据相关
	 * 最差时间复杂度O(n^2)
	 * 开始加入优化，解决的是重复检查，减少了最后面几次循环
	 */
	public void sortV2(int[] arr){
		for(int i=0;i<arr.length;i++){
			boolean sorted = true;
			for(int j=0;j<arr.length-i-1;j++){
				if(arr[j+1] < arr[j]){
					int temp = arr[j+1];
					arr[j+1] = arr[j];
					arr[j] = temp;
					sorted = false;
				}
			}
			if(sorted){
				break;
			}
		}
	}
}