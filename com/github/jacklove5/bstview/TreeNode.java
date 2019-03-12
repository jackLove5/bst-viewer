package com.github.jacklove5.bstview;

/**
 * A class representing a node in the BST
 */
public class TreeNode implements Cloneable{

	public TreeNode right;
	public TreeNode left;
	public String data;
	
	/**
	 * Constructs a TreeNode object
	 * @param data the node's data
	 */
	public TreeNode(String data)
	{
		this.data = data;
		right = left = null;
	}
	
	public TreeNode clone()
	{
		try
		{
			TreeNode cloned = (TreeNode) super.clone();
			cloned.right = right != null ? (TreeNode) right.clone() : null;
			cloned.left = left != null ? (TreeNode) left.clone() : null;
			cloned.data = data;
			return cloned;
		}
		catch (CloneNotSupportedException e)
		{
			return null;
		}
	}
}
