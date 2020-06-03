package kb.other.lintcode20200529;

import java.util.Iterator;
import java.util.LinkedList;

public class Solution {
	
	public static void main(String[] args) {
		char[] tasks = {'A', 'A', 'A', 'B', 'B', 'B'};
		new Solution().leastInterval(tasks, 2);
	}
	
    /**
     * @param tasks: the given char array representing tasks CPU need to do
     * @param n: the non-negative cooling interval
     * @return: the least number of intervals the CPU will take to finish all the given tasks
     */
    public int leastInterval(char[] tasks, int n) {
        int[] countChar = new int[26];
        for(int i=0;i<tasks.length;i++){
            countChar[tasks[i] - 'A']++;
        }
        int length = tasks.length;
        LinkedList<Character> linkedList = new LinkedList<>();
        int sum = 0;
        while(0 < length){
            for(int i=0;i<countChar.length;){
                if(countChar[i] != 0){
                    Character value = Character.valueOf((char) (i + 'A'));
                    int internal = look(linkedList, value);
                    if(internal == -1){
                    	length--;
                    	sum++;
                    	countChar[i]--;
                    	linkedListAdd(linkedList, value, n);
                    	i++;
                    }else {
                    	for(int j=0;j<(n - internal);j++){
                    		sum++;
                    		linkedListAdd(linkedList, Character.valueOf(' '), n);
                    	}
					}
                }else {
                	i++;
				}
            }
        }
		return sum;
    }
    
    // 从后向前遍历，直到遇到相同元素停止
	private int look(LinkedList<Character> linkedList, Character value) {
		int internal = 0;
		Iterator<Character> iterator = linkedList.descendingIterator();
		while(iterator.hasNext()){
			Character listVal = iterator.next();
			if(listVal.equals(value)){
				return internal;
			}else {
				internal++;
			}
		}
		return -1;
	}
    
    // N个桶，当超过指定容量时，删除首元素，加入末元素
    private void linkedListAdd(LinkedList<Character> linkedList, Character character, int capacity){
    	if(capacity <= linkedList.size()){
    		linkedList.removeFirst();
    	}
    	linkedList.addLast(character);
    	System.out.println(character);
    }
}