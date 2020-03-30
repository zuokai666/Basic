package kb.other.lintcode20200330;

import kb.other.lintcode.TreeNode;

public class Solution2 {
	
	public static void main(String[] args) {

	}
	
	public boolean isBalanced(TreeNode root){
		return countTreeDepth(root) != -1;
	}
	
	private int countTreeDepth(TreeNode root){
		if(root == null){
			return 0;
		}
		int leftDepth = countTreeDepth(root.left);
		int rightDepth = countTreeDepth(root.right);
		if(leftDepth == -1 || rightDepth == -1){
			return -1;
		}
		if(Math.abs(leftDepth - rightDepth) <= 1){
			return leftDepth < rightDepth ? rightDepth + 1 : leftDepth + 1;
		}else {
			return -1;
		}
	}
}