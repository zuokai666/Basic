package kb.other.lintcode20200322;

import kb.other.lintcode20200322.object.IndexLegalCharacterCheckExecutor;

public class Solution2 {
	
	public static void main(String[] args) {
		new Solution2().isLegalIdentifier("_023");
	}
	
    /**
     * @param str: The identifier need to be judged.
     * @return: Return if str is a legal identifier.
     */
    public boolean isLegalIdentifier(String str) {
        if(str == null || str.length() == 0){
            return false;
        }
        IndexLegalCharacterCheckExecutor checkExecutor = new IndexLegalCharacterCheckExecutor();
        for(int i=0;i<str.length();i++){
            if(!checkExecutor.isLegalIdentifierChar(str, i)){
            	return false;
            }
        }
        return true;
    }
}