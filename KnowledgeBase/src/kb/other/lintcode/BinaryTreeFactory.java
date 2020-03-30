package kb.other.lintcode;

public class BinaryTreeFactory {
	
	public TreeNode createBST(){
		TreeNode node = new TreeNode(5);
		{
			TreeNode node1 = new TreeNode(3);
			{
				TreeNode node3 = new TreeNode(2);
				TreeNode node4 = new TreeNode(4);
				node1.left = node3;
				node1.right = node4;
			}
			TreeNode node2 = new TreeNode(7);
			{
				TreeNode node3 = new TreeNode(6);
				TreeNode node4 = new TreeNode(8);
				node2.left = node3;
				node2.right = node4;
			}
			node.left = node1;
			node.right = node2;
		}
		return node;
	}
}