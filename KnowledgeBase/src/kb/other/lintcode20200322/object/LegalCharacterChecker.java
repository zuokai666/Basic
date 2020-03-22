package kb.other.lintcode20200322.object;

/**
 * 合法字符检查者
 * 
 * @author King
 *
 */
public interface LegalCharacterChecker {
	
	/**
	 * 如果当前字符合法，返回true。
	 * @param content
	 * @return
	 */
	boolean check(char content);
}