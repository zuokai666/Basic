package kb.other.lintcode20200310;

import java.util.LinkedList;

import kb.other.lintcode.TreeNode;

/**
 * 
 * @author King
 */
public class Solution1 {
	
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
		new Solution1().printZST(node);
	}
	
	public void printZST(TreeNode root){
		if(root == null){
			return;
		}
		LinkedList<TreeNode> list = new LinkedList<>();
		list.add(root);
		while(!list.isEmpty()){
			/**
			 * 记下队列大小，往前滚动多少 [***]
			 */
			int size = list.size();
			/**
			 * 这里是每一层的首元素
			 */
			TreeNode head = list.peek();
			System.err.println("每一层首元素：" + head.val);
			for(int i=0;i<size;i++){
				TreeNode node = list.poll();
				if(node.left != null){
					list.add(node.left);
				}
				if(node.right != null){
					list.add(node.right);
				}
			}
		}
	}
}