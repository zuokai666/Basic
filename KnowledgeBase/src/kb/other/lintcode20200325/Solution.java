package kb.other.lintcode20200325;

/**
 * 1654. 出现次数最多的字母
 * 给定一个字符串str,现在你需要统计出现次数最多的字母。返回这个字母出现的次数。
 * 
 * @author King
 *
 */
public class Solution {
    /**
     * @param str: the str
     * @return: the sum that the letter appears the most
     */
    public int mostFrequentlyAppearingLetters(String str) {
        if(str == null){
            return -1;
        }
        if(str.length() == 0){
            return 0;
        }
        int[] temp = new int[26 + 26];//前小写字码，后大写字母
        for(int i=0;i<str.length();i++){
            char value = str.charAt(i);
            if('a' <= value && value <= 'z'){
                temp[value - 'a'] = temp[value - 'a'] + 1;
            }else if('A' <= value && value <= 'Z'){
                temp[value - 'A' + 26] = temp[value - 'A' + 26] + 1;
            }else{
                return -1;
            }
        }
        int result = 0;
        for(int i=0;i<temp.length;i++){
            if(result < temp[i]){
                result = temp[i];
            }
        }
        return result;
    }
}