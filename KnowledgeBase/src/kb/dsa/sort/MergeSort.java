package kb.dsa.sort;

public class MergeSort {
	
	public void sort(int[] arr){
		sort(arr,0,arr.length-1);
	}
	
	// T[n] = 2 * T[n/2] + O(n)
	// T[n] = O(nlogn)
	private void sort(int[] arr, int b, int e) {
		if(e <= b) return;
		int m = (e + b) >> 1;
		sort(arr, b, m);
		sort(arr, m+1, e);
		int[] copy = new int[e - b + 1];//辅助数组
		for(int i=0;i<copy.length;i++){
			copy[i] = arr[b + i];
		}
		int bb=0;
		int ee=e-b;
		int mm=(bb+ee) >> 1;
		int i=bb;
		int j=mm+1;
		int f=b;
		while(i<=mm || j<=ee){
			if(mm < i){//前半块已经使用完毕
				arr[f++]=copy[j++];
			}else if(ee < j){
				arr[f++]=copy[i++];
			}else if(copy[i] > copy[j]){//不加等号，要不然不稳定
				arr[f++]=copy[j++];
			}else {
				arr[f++]=copy[i++];
			}
		}
	}
}