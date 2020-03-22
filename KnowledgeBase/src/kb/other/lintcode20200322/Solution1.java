package kb.other.lintcode20200322;

public class Solution1 {
	
    /**
     * @param str: The identifier need to be judged.
     * @return: Return if str is a legal identifier.
     */
    public boolean isLegalIdentifier(String str) {
        if(str == null || str.length() == 0){
            return false;
        }
        for(int i=0;i<str.length();i++){
            if(!isLegalIdentifierChar(str, i)){
                return false;
            }
        }
        return true;
    }
    
    /**
     * 判断一个字符是否是合法字符，如果是，返回true，否则返回false。
     */
    private boolean isLegalIdentifierChar(String str, int index) {
        char value = str.charAt(index);
        if(index == 0 && isNum(value)){
            return false;
        }
        if(isNum(value) || isLetter(value) || value == '_'){
            return true;
        }
        return false;
    }
    
    private boolean isNum(char value){
        if('0' <= value && value <= '9'){
            return true;
        }
        return false;
    }
    
    private boolean isLetter(char value){
        if('a' <= value && value <= 'z'){
            return true;
        }
        if('A' <= value && value <= 'Z'){
            return true;
        }
        return false;
    }
}