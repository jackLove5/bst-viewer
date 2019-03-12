package com.github.jacklove5.bstview;

import java.util.ArrayList;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/** 
 * A class representing a data set implemented w/ a BST
 */
public class TreeData {
	
	private BinarySearchTree bst;
	private ArrayList<ChangeListener> listeners;
	
	/**
	 * Constructs new TreeData object
	 */
	public TreeData()
	{
		listeners = new ArrayList<ChangeListener>();
		bst = new BinarySearchTree();
	}
	
	/**
	 * Get the set's Icon representation
	 * @param width the width of the icon to return
	 * @param height the height of the icon to return
	 * @return An icon representing the BST
	 */
	public BSTIcon getIcon(int width, int height)
	{
		return new BSTIcon(bst, width, height);
	}
	
	/**
	 * Attaches a change listener to the tree data set
	 * @param l the change listener to attach
	 */
	public void attachListener(ChangeListener l)
	{
		listeners.add(l);
	}
	
	/**
	 * Updates the data set.
	 * @param oldData data to remove from the tree
	 * @param newData data to add to the tree
	 */
	public void update(String oldData, String newData)
	{
		// If oldData is in the tree, remove it
		if (bst.isInTree(oldData))
			bst.remove(oldData);
		
		// If newData is not in the tree, insert it
		if (!bst.isInTree(newData) && !newData.equals(""))
			bst.insert(newData);
		
		// Notify change listeners
		for (ChangeListener l : listeners)
			l.stateChanged(new ChangeEvent(this));
	}
}
