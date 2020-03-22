package kb.other.lintcode20200322.object;

public class NumberLegalCharacterChecker implements LegalCharacterChecker{
	
	@Override
	public boolean check(char content) {
		if('0' <= content && content <= '9'){
            return true;
        }
        return false;
	}
}