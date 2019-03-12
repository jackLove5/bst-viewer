package com.github.jacklove5.bstview;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Icon;

/**
 * A class that represents an Icon depicting a visual representation of a BST
 */
public class BSTIcon implements Icon {

	private ArrayList<NodeShape> shapeList;
	private ArrayList<Line2D.Double> lineList;
	private int width;
	private int height;
	private BinarySearchTree bst;
	
	/**
	 * Constructs a new BSTIcon object
	 * @param bst A binary search tree holding the raw data to be painted
	 * @param width The icon's width
	 * @param height The icon's height
	 */
	public BSTIcon(BinarySearchTree bst, int width, int height)
	{	
		this.bst = bst;
		this.width = width;
		this.height = height;
		shapeList = new ArrayList<NodeShape>();
		lineList = new ArrayList<Line2D.Double>();
	}
	
	/**
	 * Get the icon's height
	 * @return the icon's height
	 */
	public int getIconHeight() { return height; }
	
	/**
	 * Get the icon's width
	 * @return the icon's width
	 */
	public int getIconWidth() { return width; }
	
	/**
	 * The icon's paintIcon method. Automatically invoked to display the icon
	 */
	public void paintIcon(Component c, Graphics g, int x, int y)
	{
		updatePaintData(x, y);
		Graphics2D g2 = (Graphics2D) g;
		
		// Paint all the edges
		for (Line2D.Double line : lineList)
			g2.draw(line);
		
		// Paint all the nodes
		for (NodeShape s : shapeList)
			s.draw(g2);
	}
	
	/**
	 * Traverse the BST to update the lists containing the node shapes and edge lines
	 * @param x the x position to paint the icon
	 * @param y the y position to paint the icon
	 */
	private void updatePaintData(int x, int y)
	{
		shapeList.clear();
		lineList.clear();
		
		int nodeSize = bst.getNodeCount() > 0 ? width / bst.getNodeCount() : 0;
		TreeNode[] dfs = bst.getInorder();
		TreeNode[][] levelArr = bst.getLevels();
		
		// Hold the x positions of the nodes to be painted
		HashMap<String, Integer> xPosMap = new HashMap<String, Integer>();
		
		// Map nodes' data to their x paint location
		for (int i = 0; i < dfs.length; i++)
			xPosMap.put(dfs[i].data, Integer.valueOf(x + i*nodeSize));
		
		// Get y location from level order traversal
		for (int i = 0; i < levelArr.length; i++)
		{
			for (int j = 0; j < levelArr[i].length; j++)
			{
				TreeNode currentNode = levelArr[i][j];
				
				// The coordinates to paint the node shape
				int xPos = xPosMap.get(currentNode.data);
				int yPos = y + i * nodeSize;
				
				NodeShape newShape = new NodeShape(currentNode.data, nodeSize, xPos, yPos);
				shapeList.add(newShape);
				
				// Create lines to represent edges from current node to its children
				Point currCenterPoint = new Point(newShape.getCenterX(), newShape.getCenterY());
				
				// If currentNode has a left child, store a line connecting the center points of the current node and its left node
				if (currentNode.left != null)
				{
					int leftCenterX = xPosMap.get(currentNode.left.data) + nodeSize / 2;
					int leftCenterY = yPos + nodeSize + nodeSize / 2;
					Point leftCenterPoint = new Point(leftCenterX, leftCenterY);
					
					lineList.add(new Line2D.Double(currCenterPoint, leftCenterPoint));
				}
				
				// If currentNode has a right child, store a line connecting the center points of the current node and its right node
				if (currentNode.right != null)
				{
					int rightCenterX = xPosMap.get(currentNode.right.data)+ nodeSize / 2;
					int rightCenterY = yPos + nodeSize + nodeSize / 2;
					Point rightCenterPoint = new Point(rightCenterX, rightCenterY);
					
					lineList.add(new Line2D.Double(currCenterPoint, rightCenterPoint));
				}				
			}
		}
	}
}
