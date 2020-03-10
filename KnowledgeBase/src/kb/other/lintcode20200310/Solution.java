package kb.other.lintcode20200310;

import java.util.LinkedList;

import kb.other.lintcode.TreeNode;

/**
 * 使用双队列实现二叉树的层次遍历，两个队列交替存储，就可以明确的区分边界。
 * 
 * @author King
 */
public class Solution {
	
	public static void main(String[] args) {
		TreeNode node = new TreeNode(0);
		{
			TreeNode node1 = new TreeNode(1);
			TreeNode node2 = new TreeNode(2);
			{
				TreeNode node3 = new TreeNode(3);
				TreeNode node4 = new TreeNode(4);
				node2.left = node3;
				node2.right = node4;
			}
			node.left = node1;
			node.right = node2;
		}
		new Solution().printZST(node);
	}
	
	/**
	 * 打印左视图
	 * 
	 * @param root
	 */
	public void printZST(TreeNode root){
		if(root == null){
			return;
		}
		LinkedList<TreeNode> notNullList = new LinkedList<>();
		LinkedList<TreeNode> nullList = new LinkedList<>();
		notNullList.add(root);
		while(!notNullList.isEmpty()){
			/**
			 * 这里是每一层的首元素
			 */
			TreeNode head = notNullList.peek();
			System.err.println("每一层首元素：" + head.val);
			while(!notNullList.isEmpty()){
				TreeNode node = notNullList.poll();
				if(node.left != null){
					nullList.add(node.left);
				}
				if(node.right != null){
					nullList.add(node.right);
				}
			}
			/**
			 * 交换引用
			 */
			LinkedList<TreeNode> temp = notNullList;
			notNullList = nullList;
			nullList = temp;
		}
	}
}