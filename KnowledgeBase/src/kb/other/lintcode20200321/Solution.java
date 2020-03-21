package kb.other.lintcode20200321;

/**
 * 
 * 13. 字符串查找
 * 
 * 对于一个给定的 source 字符串和一个 target 字符串，
 * 你应该在 source 字符串中找出 target 字符串出现的第一个位置(从0开始)。
 * 如果不存在，则返回 -1。
 * 
 * @author King
 * 
 */
public class Solution {
	
	public static void main(String[] args) {
		String source = "0123456789abcd";
		String target = "abcd";
		new Solution().strStr_v1(source, target);
		new Solution().strStr_v2(source, target);
	}
	
	// 空间复杂度O(1) 时间复杂度O(n * m)
	public int strStr_v1(String source, String target) {
        if(source == null || target == null){
            return -1;
        }
        for(int i=0;i<source.length() - target.length() + 1;i++){
            boolean success = true;
            int iCopy = i;
            for(int j=0;j<target.length();j++,iCopy++){
                if(source.charAt(iCopy) != target.charAt(j)){
                    success = false;
                }
            }
            if(success){
                return i;
            }
        }
        return -1;
    }
	
	// 暂估空间复杂度O(1) 时间复杂度O(n)
	public int strStr_v2(String source, String target) {
        if(source == null || target == null){
            return -1;
        }
        int targetHashCode = hash(target, 0, target.length() - 1);
        int sourceHashCode = 0;
        for(int i=0;i<source.length() - target.length() + 1;i++){
        	if(i == 0){
                sourceHashCode = hash(source, i, i + target.length() - 1);
			}else {
				sourceHashCode = sourceHashCode - source.charAt(i - 1) + source.charAt(i + target.length() - 1);
			}
        	if(sourceHashCode == targetHashCode){
            	boolean success = true;
                int iCopy = i;
                for(int j=0;j<target.length();j++,iCopy++){
                    if(source.charAt(iCopy) != target.charAt(j)){
                        success = false;
                    }
                }
                if(success){
                    return i;
                }
            }
        }
        return -1;
    }
	
	private int hash(String str, int begin, int end){
		int hash = 0;
		for(int i=begin;i<=end;i++){
			hash = hash + str.charAt(i);
		}
		return hash;
	}
}

/**

java.lang.String.hashCode()

例如：abcd

计算：abc.hashCode()
a * 31^2 + b * 31^1 + c

计算：bcd.hashCode()
b * 31^2 + c * 31^1 + d = (abc.hash - a * 31^2) * 31 + d

可行，可以从上一个hashcode计算下一个hashcode

*/