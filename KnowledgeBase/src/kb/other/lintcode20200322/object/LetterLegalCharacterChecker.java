package kb.other.lintcode20200322.object;

public class LetterLegalCharacterChecker implements LegalCharacterChecker{
	
	@Override
	public boolean check(char content) {
		if('a' <= content && content <= 'z'){
            return true;
        }
        if('A' <= content && content <= 'Z'){
            return true;
        }
        return false;
	}
}