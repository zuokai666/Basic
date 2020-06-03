package kb.other.lintcode20200601;

import java.util.HashMap;

public class Solution {

	public int[] getAns(int[] op, String[] name, int[] w) {
        if(op == null || name == null || w == null){
            return null;
        }
        int[] result = new int[w.length];
        HashMap<String, Integer> map = new HashMap<>();
        for(int i=0;i<w.length;i++){
            Integer balance = map.getOrDefault(name[i], 0);
            if(op[i] == 0){
            	balance = balance + w[i];
            	map.put(name[i], balance);
            	result[i] = balance;
            }else if(w[i] <= balance){
            	balance = balance - w[i];
            	map.put(name[i], balance);
            	result[i] = balance;
            }else {
            	result[i] = -1;
			}
        }
        return result;
    }
}