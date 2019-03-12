package com.github.jacklove5.bstview;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * A frame that displays a visual representation of a binary search tree
 */
public class DisplayFrame extends JFrame implements ChangeListener{
	
	private TreeData dataSet;
	public static int FRAME_HEIGHT = 500;
	public static int FRAME_WIDTH = 500;
	
	/**
	 * Creates new DisplayFrame
	 * @param the data set to read from
	 */
	public DisplayFrame(TreeData aDataSet)
	{
		dataSet = aDataSet;
		setLayout(new BorderLayout());
		setLocation(0, 200);
		
		BSTIcon bst = dataSet.getIcon(FRAME_WIDTH, FRAME_HEIGHT);
		
		JLabel treeLabel = new JLabel(bst);
		
		add(treeLabel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	/**
	 * Updates the DisplayFrame
	 */
	public void stateChanged(ChangeEvent e)
	{
		repaint();
	}
}
