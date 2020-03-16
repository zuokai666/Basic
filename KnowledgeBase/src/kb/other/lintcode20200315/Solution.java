package kb.other.lintcode20200315;

public class Solution {

	public static void main(String[] args) {
		new Solution().strStr("abcdabcdefg", "bcd");
	}

	public int strStr(String source, String target) {
        if(source == null || target == null || 
            source.length() != 0 || target.length() != 0){
            return -1;
        }
        if(source.length() < target.length()){
            return -1;
        }
        for(int i=0;i<source.length();i++){
            boolean found = true;
            int first = i;
            for(int j=0;j<target.length();j++,first++){
                if(source.charAt(first) != target.charAt(j)){
                    found = false;
                    break;
                }
            }
            if(found){
                return i;
            }
        }
        return -1;
    }
}
