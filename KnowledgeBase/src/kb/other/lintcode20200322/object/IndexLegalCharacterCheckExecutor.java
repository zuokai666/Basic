package kb.other.lintcode20200322.object;

import java.util.ArrayList;
import java.util.List;

public class IndexLegalCharacterCheckExecutor extends LegalCharacterCheckExecutor{
	
	private List<LegalCharacterChecker> indexCheckers = new ArrayList<>();
	
	public IndexLegalCharacterCheckExecutor() {
		super();
		this.indexCheckers.add(new NumberLegalCharacterChecker());
	}
	
	public boolean isLegalIdentifierChar(String str, int index){
		if(index == 0){
			for(LegalCharacterChecker checker : indexCheckers){
				if(checker.check(str.charAt(index))){
					return false;
				}
			}
		}
		return super.isLegalIdentifierChar(str.charAt(index));
	}
}