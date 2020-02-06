package kb.dsa.sort;

import java.util.Arrays;

public class HeapSort {

	public static void main(String[] args) {
		int[] arr = {9,8,7,6,5,4,3,20};
		System.err.println(Arrays.toString(arr));
		new HeapSort().sort(arr);
		System.out.println(Arrays.toString(arr));
	}
	
	//建立一个最大堆，将最大值放到末尾假装抹除，利用减而治之的思想一步一步将堆变小，最终达到递增排序的结果
	public void sort(int[] arr){
		//1.堆化
		int size = arr.length;
		for(int i=(size >>> 1) - 1;i>=0;i--){
			siftDown(i, arr, size);
		}
		//2.原地递增排序
		for(;size > 1;){
			int max = arr[0];
			arr[0] = arr[size - 1];
			arr[size - 1] = max;//交换首元素与末元素，这时最大值到了数组最后
			size--;//假装清除了一个值
			siftDown(0, arr, size);//将首元素下滤，这样又是一个最大堆
		}
	}
	
	private void siftDown(int i, int[] arr, int size) {
		int index = i;
		int key = arr[i];
		int half = size >>> 1;
		while(index < half){
			int child = (index << 1) + 1;//假定左孩子最大
			int value = arr[child];
			int right = child + 1;
			if(right < size && arr[child] < arr[right]){
				child = right;
				value = arr[right];
			}
			if(key < value){
				arr[index] = value;
				index = child;
			}else {
				break;
			}
		}
		arr[index] = key;
	}
}