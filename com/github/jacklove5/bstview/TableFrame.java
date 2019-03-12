package com.github.jacklove5.bstview;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 * A JFrame that holds the table of entries
 */
public class TableFrame extends JFrame {

	private TreeData treeModel;
	private String[] prevFieldText;
	private JTextField[] fieldList;
	public static final int NUM_ROWS = 20;
	
	/**
	 * Create a TableFrame object
	 * @param aTreeData a reference to the data set to be updated
	 */
	public TableFrame(TreeData aTreeData)
	{
		fieldList = new JTextField[NUM_ROWS];
		prevFieldText = new String[NUM_ROWS];
		for (int i = 0; i < NUM_ROWS; i++)
			prevFieldText[i] = "";
		
		treeModel = aTreeData;
		setSize(200, 400);
		
		Container contentPane = this.getContentPane();
		setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		ActionListener listener = new
			ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					int sourceIndex = 0;
					// Find the TextField that was updated
					JTextField source = (JTextField) e.getSource();
					for (sourceIndex = 0; sourceIndex < fieldList.length; sourceIndex++)
					{
						if (fieldList[sourceIndex] == source)
							break;
					}
					
					String currText = fieldList[sourceIndex].getText();
					
					// delete new text if it's already in the table
					boolean textFound = false;
					for (JTextField tf : fieldList)
					{
						if (tf.getText().equals(currText) && !currText.equals(""))
						{
							if (textFound == true)
							{
								tf.setText("");
								return;
							}
							else
							{
								textFound = true;
							}
						}
					}
					
					// Update data model
					// Update previous text list
					String prevText = prevFieldText[sourceIndex];
					treeModel.update(prevText,  currText);
					prevFieldText[sourceIndex] = currText;
				}
			};
			
		for (int i = 0; i < NUM_ROWS; i++)
		{
			fieldList[i] = new JTextField();
			fieldList[i].addActionListener(listener);
			add(fieldList[i]);
		}
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
}
