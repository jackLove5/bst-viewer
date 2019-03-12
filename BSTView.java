import com.github.jacklove5.bstview.*; 

/**
 * Program that accepts string input from the user and displays
 * the data as a Binary Search Tree
 * 
 * Observer Pattern implementation adapted from Cay Horstmann's exercise solution
 * http://horstmann.com/oodp2/solutions/solutions.html
 */
public class BSTView {

	public static void main(String args[])
	{
		TreeData dataSet = new TreeData();
		TableFrame tableFrame = new TableFrame(dataSet);
		DisplayFrame displayFrame = new DisplayFrame(dataSet);
		
		dataSet.attachListener(displayFrame);
	}
}
