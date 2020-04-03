package kb.other.lintcode20200403;

import java.util.ArrayList;
import java.util.List;

import kb.other.lintcode.ListNode;

public class Solution3 {

	public List<Integer> reverseStore(ListNode head) {
        if(head == null){
        	return null;
        }
        int size = countSize(head);
        int[] nums = new int[size];
        ListNode node = head;
        int index = size - 1;
		while(node != null){
			nums[index] = node.val;
			node = node.next;
			index--;
		}
		ArrayList<Integer> arrayList = new ArrayList<>(size);//节省了扩容的性能损失
		for(int i=0;i<nums.length;i++){
			arrayList.add(nums[i]);
		}
		return arrayList;
    }
	
	private int countSize(ListNode head){
		int size = 0;
		ListNode node = head;
		while(node != null){
			size++;
			node = node.next;
		}
		return size;
	}
}