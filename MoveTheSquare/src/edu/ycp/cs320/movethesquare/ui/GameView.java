package edu.ycp.cs320.movethesquare.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import edu.ycp.cs320.movethesquare.controllers.GameController;
import edu.ycp.cs320.movethesquare.model.Game;
import edu.ycp.cs320.movethesquare.model.Square;

public class GameView extends JPanel {
	private static final long serialVersionUID = -940230005332719062L;
	private static final Color MIDNIGHT_BLUE = new Color(25, 25, 112);
	
	private Game model;
	private GameController controller;
	private Timer timer;
	
	private Point center;
	private double radius;
	private static final double D_RADIUS = 1.5;
	private static final int MAX_RADIUS = 200;
	
	public GameView(final Game model) {
		this.model = model;
		setPreferredSize(new Dimension((int) model.getWidth(), (int)model.getHeight()));
		setBackground(MIDNIGHT_BLUE);

		// djh2-KEC119-21: changed from 30 to 45
		// djh2-YCPlaptop: change from 45 to 100
		this.timer = new Timer(1000 / 100, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				handleTimerTick();
			}
		});
		
		final GameView THIS = this;
		setFocusable(true);
		
		addKeyListener(new KeyAdapter () {
			@Override
			public void keyPressed(KeyEvent e) {
				controller.handleKeyPress(THIS, e);
			}
			@Override
			public void keyReleased(KeyEvent e) {
				controller.handleKeyRelease(THIS, e);
			}
		});
		addMouseListener(new MouseAdapter () {
			@Override
			public void mousePressed(MouseEvent e) {
				controller.handleMousePress(THIS, e);
			}
		});
	}
	
	public Game getModel() {
		return model;
	}
	
	public void setController(GameController controller) {
		this.controller = controller;
	}
	
	public void startAnimation() {
		timer.start();
	}
	
	public void setCenter(Point c) {
		center = c;
	}
	
	protected void handleTimerTick() {
		if (controller == null) {
			return;
		}
		Square square = model.getSquare();
		Point mouseLoc = getMousePosition();
		if (mouseLoc != null) {
			controller.computeSquareMoveDirection(model, square, mouseLoc.getX(), mouseLoc.getY());
		}
		controller.moveSquare(model, square);
		
		if (center != null) {
			radius += D_RADIUS;
			Point squareCenter = new Point ((int)(square.getX() + square.getWidth()/2), (int)(square.getY() + square.getHeight()/2));
			int dx = center.x - squareCenter.x, dy = center.y - squareCenter.y;
			
			if (Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2)) < radius) {
				double angle = 0;
				if (dx != 0) angle = Math.atan(dy/dx);
				Point newSquareCenter = new Point((int)(squareCenter.x + radius*Math.cos(angle)), (int)(squareCenter.y + radius*Math.sin(angle)));
				newSquareCenter.translate((int)(square.getWidth()/-2), (int)(square.getHeight()/-2));
				square.setX(newSquareCenter.x);
				square.setY(newSquareCenter.y);
			}
			
			if (radius >= MAX_RADIUS) {
				radius = 0;
				center = null;
			}
		}
		
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g); // paint background
		
		// djh2-KEC110-21: changed from GREEN to RED
		// djh2-YCPlaptop: change from RED to YELLOW
		Square square = model.getSquare();
		
		g.setColor(square.getColor());

		g.fillRect((int) square.getX(), (int) square.getY(), (int) square.getWidth(), (int) square.getHeight());
		
		if (center != null) {
			Color bg = getBackground();
			g.setColor(new Color(255-bg.getRed(), 255-bg.getGreen(), 255-bg.getBlue()));
			g.drawOval((int)(center.x-radius), (int)(center.y-radius), (int)(radius*2), (int)(radius*2));
		}
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Game model = new Game();
				model.setWidth(1600.0);
				model.setHeight(900.0);
				
				Square square = new Square();
				square.setX(300.0);
				square.setY(220.0);
				square.setWidth(40.0);
				square.setHeight(40.0);
				model.setSquare(square);
				
				GameController controller = new GameController();
				
				GameView view = new GameView(model);
				view.setController(controller);
				
				JFrame frame = new JFrame("Move the Square!");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.add(view);
				frame.pack();
				frame.setVisible(true);
				
				view.startAnimation();
			}
		});
	}
}
