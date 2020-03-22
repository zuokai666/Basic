package kb.other.lintcode20200322.object;

import java.util.HashSet;
import java.util.Set;

public class SpecialCharacterLegalCharacterChecker implements LegalCharacterChecker{
	
	private static Set<Character> set = new HashSet<>();
	
	static{
		set.add('_');
	}
	
	@Override
	public boolean check(char content) {
		if(set.contains(content)){
			return true;
		}
        return false;
	}
}