package com.github.jacklove5.bstview;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

/**
 * A class providing a visual representation of a Node
 * in a Binary Search Tree
 */
public class NodeShape {
	
	private int size;
	private String nodeText;
	private int xPos;
	private int yPos;
	
	/**
	 * Construct a new NodeShape
	 * @param text The node's text
	 * @param size The node's size
	 * @param x The x position to draw the node
	 * @param y The y position to draw the node
	 */
	public NodeShape(String text, int size, int x, int y)
	{
		this.size = size;
		nodeText = text;
		this.xPos = x;
		this.yPos = y;
	}
	
	/**
	 * Get the x position of the shape's center point
	 * @return the x position of the shape's center point
	 */
	public int getCenterX()
	{
		return xPos + size / 2;
	}
	
	/**
	 * Get the y position of the shape's center point
	 * @return the y position of the shape's center point
	 */
	public int getCenterY()
	{
		return yPos + size / 2;
	}
	
	/**
	 * Draw the node given the graphics context
	 * @param g The graphics context with which to draw the node
	 */
	public void draw(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		Ellipse2D.Double outerCircle = new Ellipse2D.Double(xPos, yPos, size, size);
		
		int innerCircleSize = (int)(size * 0.9);
		int innerCircleX = (int) (outerCircle.getCenterX() - innerCircleSize / 2);
		int innerCircleY = (int) (outerCircle.getCenterY() - innerCircleSize / 2);
		
		Ellipse2D.Double innerCircle = new Ellipse2D.Double(innerCircleX, innerCircleY, innerCircleSize, innerCircleSize);

		// paint main node shape (white circle inside black circle)
		g2.setColor(Color.BLACK);
		g2.fill(outerCircle);
		g2.setColor(Color.WHITE);
		g2.fill(innerCircle);
		g2.setColor(Color.BLACK);
		
		int currFontSize = size;
		
		Rectangle2D strBounds;
		
		// Take linear time to get a font size that fits in the inner circle (slow)
		FontMetrics fm;
		do
		{
			g2.setFont(new Font("Times new Roman", Font.PLAIN, currFontSize--));
			fm = g2.getFontMetrics();
			strBounds = fm.getStringBounds(nodeText, g2);
		} while ((strBounds.getWidth() > innerCircle.getWidth() || strBounds.getHeight() > innerCircle.getHeight()) && currFontSize > 1);
		
		strBounds.setRect(innerCircle.getCenterX() - strBounds.getWidth() / 2, innerCircle.getCenterY() - strBounds.getHeight() / 2, strBounds.getWidth(), strBounds.getHeight());
		
		// Center text w/ respect to inner Circle's center point
		int textX = (int) (innerCircle.getCenterX() - strBounds.getWidth() / 2);
		int textY = (int) (innerCircle.getCenterY() + strBounds.getHeight() / 2);
		
		g2.drawString(nodeText, textX, textY - fm.getMaxDescent());
	}
}
