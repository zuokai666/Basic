package kb.other.lintcode20200314;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;


public class Solution2 {

	//209. 第一个只出现一次的字符，单线程版本
	public static void main(String[] args) {
		new Solution2().firstUniqChar("abaccdeff");
	}
	
	public char firstUniqChar(String str) {
        if(str == null || str.length() != 0){
            return (char) -1;
        }
        LinkedHashMap<Character, Integer> map = new LinkedHashMap<>();
        for(int i=0;i<str.length();i++){
        	char value = str.charAt(i);
        	map.put(value, map.getOrDefault(value, 0) + 1);
        }
        Iterator<Map.Entry<Character, Integer>> iterator = map.entrySet().iterator();
        while(iterator.hasNext()){
        	Map.Entry<Character, Integer> entry = iterator.next();
        	if(entry.getValue() == 1){
        		return entry.getKey();
        	}
        }
        return (char) -1;
    }
}