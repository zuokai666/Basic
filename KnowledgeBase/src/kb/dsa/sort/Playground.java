package kb.dsa.sort;

import java.util.Arrays;

/**
 * 练习用的公共场所
 * 
 * @author King
 *
 */
public class Playground {
	
	public static void main(String[] args) {
		int[] arr = {5,9,1,7,2};
		mergeSort(arr);
		System.err.println(Arrays.toString(arr));
	}
	
	public static void mergeSort(int[] arr){
		if(arr == null || arr.length == 0){
			return;
		}
		mergeSortInternal(arr, 0, arr.length - 1);
	}
	
	private static void mergeSortInternal(int[] arr, int s, int e){
		if(e <= s){
			return;
		}
		int m = (s + e) >> 1;
		mergeSortInternal(arr, s, m);
		mergeSortInternal(arr, m + 1, e);
		//接下来两有序变一有序
		int[] copy = new int[e - s + 1];
		for(int i=0;i<copy.length;i++){
			copy[i] = arr[s + i];
		}
		int copy_s = 0;
		int copy_e = copy.length - 1;
		int copy_m = (copy_s + copy_e) >> 1;
		int i = copy_s;//copy数组前半段的哨兵
		int j = copy_m + 1;//copy数组后半段的哨兵
		int arr_i = s;//主数组的哨兵
		while((i <= copy_m) || (j <= copy_e)){
			if(copy_e < j){
				arr[arr_i++] = copy[i++];
			}else if(copy_m < i){
				arr[arr_i++] = copy[j++];
			}else if(copy[i] <= copy[j]){
				arr[arr_i++] = copy[i++];
			}else {
				arr[arr_i++] = copy[j++];
			}
		}
	}
}