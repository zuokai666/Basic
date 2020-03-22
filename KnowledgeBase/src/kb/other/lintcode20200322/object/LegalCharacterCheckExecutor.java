package kb.other.lintcode20200322.object;

import java.util.ArrayList;
import java.util.List;

public class LegalCharacterCheckExecutor {
	
	private List<LegalCharacterChecker> checkers = new ArrayList<>();
	
	public LegalCharacterCheckExecutor() {
		this.checkers.add(new NumberLegalCharacterChecker());
		this.checkers.add(new SpecialCharacterLegalCharacterChecker());
		this.checkers.add(new LetterLegalCharacterChecker());
	}
	
	public boolean isLegalIdentifierChar(char content){
		for(LegalCharacterChecker checker : checkers){
			if(checker.check(content)){
				return true;
			}
		}
		return false;
	}
}