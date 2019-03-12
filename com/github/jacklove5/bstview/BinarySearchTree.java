package com.github.jacklove5.bstview;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * A class that implements a Binary Search Tree
 */
public class BinarySearchTree {
	
	private TreeNode head;
	
	/**
	 * Creates a BinarySearchTree object
	 */
	public BinarySearchTree()
	{
		head = null;
	}
	
	/**
	 * Counts the number of nodes in the tree
	 * @return the number of nodes in the tree
	 */
	public int getNodeCount()
	{
		return getNodeCountRec(head);
	}
	
	/**
	 * Helper function: recursively calculates the number of nodes in the Binary Search Tree
	 * @param root the root of the tree
	 * @return the number of nodes in the tree
	 */
	private int getNodeCountRec(TreeNode root)
	{
		if (root == null)
			return 0;
		
		return getNodeCountRec(root.left) + getNodeCountRec(root.right) + 1;
	}
	
	/**
	 * Performs an inorder traversal of the tree
	 * @return An array of Nodes representing the inorder traversal of the tree
	 */
	public TreeNode[] getInorder()
	{
		ArrayList<TreeNode> list = new ArrayList<TreeNode>();
		inorderRec(list, head);
		
		TreeNode[] retArr = new TreeNode[list.size()];
		retArr = (TreeNode[]) list.toArray(retArr);
		
		return retArr;
	}
	
	/**
	 * Helper function: Recursively performs an inorder traversal on the tree
	 * @param list The list to store the nodes in
	 * @param root The root of the tree
	 */
	private void inorderRec(ArrayList<TreeNode> list, TreeNode root)
	{
		if (root == null)
			return;
		
		inorderRec(list, root.left);
		list.add((TreeNode) root.clone());
		inorderRec(list, root.right);
	}
	
	/**
	 * Performs a level order traversal of the tree
	 * @return A 2-dimensional array containing the nodes in level order
	 */
	public TreeNode[][] getLevels()
	{
		ArrayList<TreeNode[]> levels = new ArrayList<TreeNode[]>();
		Queue<TreeNode> nodeQueue = new LinkedList<TreeNode>();
		nodeQueue.add(head);
		
		// BFS: keeping track of size of each level
		while (!nodeQueue.isEmpty())
		{
			ArrayList<TreeNode> currLevel = new ArrayList<TreeNode>();
			int queueSize = nodeQueue.size();
			
			for (int i = 0; i < queueSize; i++)
			{
				TreeNode frontNode = nodeQueue.remove();
				if (frontNode != null)
				{
					nodeQueue.add(frontNode.left != null ? (TreeNode) frontNode.left.clone() : null);
					nodeQueue.add(frontNode.right != null ? (TreeNode) frontNode.right.clone() : null);
					currLevel.add((TreeNode) frontNode.clone());
				}
			}
			
			// If there was at least one node on the level that was not null
			if (!nodeQueue.isEmpty())
			{
				TreeNode [] currLevelArr = new TreeNode[currLevel.size()];
				currLevelArr = currLevel.toArray(currLevelArr);
				levels.add(currLevelArr);
			}
		}
		
		TreeNode[][] levelsArr = new TreeNode[levels.size()][];
		levelsArr = levels.toArray(levelsArr);
		return levelsArr;
	}
	
	/**
	 * Inserts data into the Binary Search Tree
	 * @param toInsert a String representing the data to insert
	 */
	public void insert(String toInsert)
	{
		if (toInsert.equals(""))
			return;
		
		TreeNode nodeToAdd = new TreeNode(toInsert);
		TreeNode parent = null;
		TreeNode destination = head;
		
		// Find the node's destination while keeping track of the destination's parent
		while (destination != null)
		{
			parent = destination;
			if (nodeToAdd.data.equals(destination.data))
				return;
			else if (nodeToAdd.data.compareTo(destination.data) < 0)
				destination = destination.left;
			else
				destination = destination.right;
		}
		
		if (parent == null)
			head = nodeToAdd;
		else if (nodeToAdd.data.compareTo(parent.data) < 0)
			parent.left = nodeToAdd;
		else
			parent.right = nodeToAdd;
	}
	
	/**
	 * Remove data from the Binary Search Tree
	 * @param toRemove a string representing the data to remove
	 */
	public void remove(String toRemove)
	{
		TreeNode removeLocation = head;
		TreeNode parent = null;
		
		// Find the node to remove while keeping track of node's parent
		while (removeLocation != null && !removeLocation.data.equals(toRemove))
		{
			parent = removeLocation;
			if (toRemove.compareTo(removeLocation.data) < 0)
				removeLocation = removeLocation.left;
			else
				removeLocation = removeLocation.right;
		}
		
		// No node to remove
		if (removeLocation == null)
			return;
		
		// Case 1: remove a leaf node
		if (removeLocation.left == null && removeLocation.right == null)
		{
			if (parent == null)
				head = null;
			else if (parent.left == removeLocation)
				parent.left = null;
			else
				parent.right = null;
		}
		// Case 2: Remove node w/ one child
		else if (removeLocation.left == null || removeLocation.right == null)
		{
			// removing head
			if (parent == null)
			{
				if (head.left != null)
					head = head.left;
				else
					head = head.right;
			}
			else if (parent.left == removeLocation)
			{
				if (removeLocation.left != null)
					parent.left = removeLocation.left;
				else
					parent.left = removeLocation.right;
			}
			else
			{
				if (removeLocation.left != null)
					parent.right = removeLocation.left;
				else
					parent.right = removeLocation.right;
			}
		}
		// Case 3: Remove node w/ 2 children
		else
		{
			String successorData = getSuccessor(toRemove);
			remove(successorData);
			removeLocation.data = successorData;
		}
	}
	
	/**
	 * Find the node with the next greatest data
	 * @param dataToFind The data to find the successor of
	 * @return A string representing the parameter's successor
	 */
	public String getSuccessor(String dataToFind)
	{
		TreeNode foundNode = head;
		TreeNode parent = null;
		
		// Find the node
		while (foundNode != null && !foundNode.data.equals(dataToFind))
		{
			parent = foundNode;
			if (dataToFind.compareTo(foundNode.data) < 0)
				foundNode = foundNode.left;
			else
				foundNode = foundNode.right;
		}

		// Return if specified node isn't in the tree
		if (foundNode == null)
			return "";
		
		// If node has a right child, go right, then dive all the way left
		if (foundNode.right != null)
		{
			foundNode = foundNode.right;
			while (foundNode.left != null)
				foundNode = foundNode.left;
			
			return foundNode.data;
		}
		// Otherwise, return parent's data or empty if there is no parent
		else
		{
			return parent == null ? "" : parent.data;
		}
	}
	
	/**
	 * Check if particular entry exists in the Binary Search Tree
	 * @param toFind the data to find
	 * @return true if and only if a node w/ the specified data exists in the tree
	 */
	public boolean isInTree(String toFind)
	{
		TreeNode foundNode = head;
		while (foundNode != null && !foundNode.data.equals(toFind))
		{
			if (toFind.compareTo(foundNode.data) < 0)
				foundNode = foundNode.left;
			else
				foundNode = foundNode.right;
		}
		
		return foundNode != null;
	}
}

