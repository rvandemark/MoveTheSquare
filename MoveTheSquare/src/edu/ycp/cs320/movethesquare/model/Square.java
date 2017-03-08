package edu.ycp.cs320.movethesquare.model;

import java.awt.Color;

public class Square {
	private double x, y, width, height;
	private Color color;
	
	public Square() {
		color = Color.yellow;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public double getX() {
		return x;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public double getY() {
		return y;
	}
	
	public void setColor(Color c) {
		color = c;
	}
	
	public void setWidth(double width) {
		this.width = width;
	}
	
	public double getWidth() {
		return width;
	}
	
	public void setHeight(double height) {
		this.height = height;
	}
	
	public double getHeight() {
		return height;
	}
}
