package kb.other.lintcode20200330;

import java.util.ArrayList;
import java.util.List;

import kb.other.lintcode.TreeNode;

public class Solution {

	public static void main(String[] args) {

	}
	
	/**
	 * 二叉搜索树的第K大节点
	 * 
	 * @param root
	 * @param k
	 * @return
	 */
	public TreeNode fun(TreeNode root, int k){
		if(root == null || k <= 0){//还需要判断k是否大于树的size
			return null;
		}
		List<TreeNode> list = new ArrayList<TreeNode>();
		midOrder(root, list);
		if(list.size() < k){
			return null;
		}else {
			return list.get(list.size() - k);
		}
	}
	
	private void midOrder(TreeNode root, List<TreeNode> list){
		if(root != null){
			midOrder(root.left, list);
			list.add(root);
			midOrder(root.right, list);
		}
	}
}