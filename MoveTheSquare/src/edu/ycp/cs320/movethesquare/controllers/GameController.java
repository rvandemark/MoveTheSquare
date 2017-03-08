package edu.ycp.cs320.movethesquare.controllers;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Random;

import edu.ycp.cs320.movethesquare.model.Game;
import edu.ycp.cs320.movethesquare.model.Square;
import edu.ycp.cs320.movethesquare.ui.GameView;

public class GameController {
	private final static Random RAND = new Random();
	
	private boolean shift = false;
	
	public void computeSquareMoveDirection(Game game, Square square, double mouseX, double mouseY) {
		if (mouseX >= 0 && mouseX < game.getWidth() && mouseY >= 0 && mouseY < game.getHeight()) {
			double dx = mouseX - (square.getX() + square.getWidth()/2);
			double dy = mouseY - (square.getY() + square.getHeight()/2);
			
			double moveX = 0, moveY = 0;
			if (dx > 0) {
				moveX = Game.MOVE_DIST;
			} else {
				moveX = -Game.MOVE_DIST;
			}
			if (dy > 0) {
				moveY = Game.MOVE_DIST;
			} else {
				moveY = -Game.MOVE_DIST;
			}
			
			game.setSquareDx(moveX);
			game.setSquareDy(moveY);
		}
	}

	public void moveSquare(Game model, Square square) {
		square.setX(square.getX() + model.getSquareDx()*(shift ? 2 : 1));
		square.setY(square.getY() + model.getSquareDy()*(shift ? 2 : 1));
	}
	
	public void handleKeyPress (GameView view, KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_ESCAPE:
				System.exit(0);
				break;
			case KeyEvent.VK_ENTER:
				Color newBg = new Color(RAND.nextInt(256), RAND.nextInt(256), RAND.nextInt(256));
				Color newSq = new Color(RAND.nextInt(256), RAND.nextInt(256), RAND.nextInt(256));
				view.setBackground(newBg);
				view.getModel().getSquare().setColor(newSq);
				break;
			case KeyEvent.VK_SHIFT:
				shift = true;
				break;
		}
	}
	
	public void handleKeyRelease (GameView view, KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_SHIFT:
				shift = false;
				break;
		}
	}
	
	public void handleMousePress (GameView view, MouseEvent e) {
		view.setCenter(e.getPoint());
	}
	
	public boolean getShift() {
		return shift;
	}
}
