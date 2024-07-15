package com.berko.colorSweep;

import java.awt.*;

import javax.swing.*;

import pixelDisplay.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

public class ColorSweep extends JFrame implements ActionListener {
	
	int height;
	int length;
	int score = -1;
	PixelDisplay board;
	int[][] boardValues;
	JLabel scoreLabel = new JLabel("Score: 0");
	ArrayList<Point> conquered = new ArrayList<Point>();
	ArrayList<Point> newConquered = new ArrayList<Point>();
	
	JButton r = new JButton("     ");
	JButton o = new JButton("     ");
	JButton y = new JButton("     ");
	JButton g = new JButton("     ");
	JButton b = new JButton("     ");
	JButton p = new JButton("     ");
	JButton[] colorButtons = {r, o, y, g, b, p};
	
	public ColorSweep(int x, int y) {
		setTitle("Color Sweep");
		setSize(x*50, y*50+100);
		setVisible(true);
		height = x;
	    length = y;
		conquered.add(new Point(0, 0));
	    
        //Titlebar area
        Box titlebar = Box.createHorizontalBox();
        titlebar.add(Box.createVerticalStrut(50));
        titlebar.add(Box.createHorizontalGlue());
        titlebar.add(scoreLabel);
        titlebar.add(Box.createHorizontalGlue());
        add(titlebar, BorderLayout.NORTH);
        
		//Make Board
		board = new PixelDisplay(x, y, Color.WHITE);
		boardValues = new int[x][y];
		for(int i = 0; i < x; i++) {
			for(int j = 0; j < y; j++) {
				//picks a random color for each tile
				int colorVal = (int)(Math.random() * 6);
				boardValues[i][j] = colorVal;
				Color c = null;
				switch(colorVal) {
					case 0:
						c = Color.RED;
						break;
					case 1:
						c = Color.ORANGE;
						break;
					case 2:
						c = Color.YELLOW;
						break;
					case 3:
						c = Color.GREEN;
						break;
					case 4:
						c = Color.BLUE;
						break;
					case 5:
						c = Color.MAGENTA;
						break;
					default:
						c = Color.WHITE;
						break;
				}
				board.setPixel(new Point(i, j), c);
			}
		}
		add(board, BorderLayout.CENTER);
		
		//add buttons
		Box buttonBox = Box.createHorizontalBox();
		buttonBox.add(Box.createVerticalStrut(50));
    	buttonBox.add(Box.createHorizontalGlue());
	  	for(JButton button : colorButtons) {
	  		button.setVisible(true);
	  		button.setBorder(BorderFactory.createEmptyBorder());
			button.addActionListener(this);
			button.setSize(new Dimension(10, 10));
			buttonBox.add(button);
			buttonBox.add(Box.createHorizontalGlue());
	  	}
		//set buttons color
		r.setBackground(Color.RED);
		o.setBackground(Color.ORANGE);
		this.y.setBackground(Color.YELLOW);
		g.setBackground(Color.GREEN);
		b.setBackground(Color.BLUE);
		p.setBackground(Color.MAGENTA);
    	add(buttonBox, BorderLayout.SOUTH);
    	
    	buttonPressed(boardValues[0][0]);
	}
	
	@Override
    public void actionPerformed(ActionEvent arg0) {
        boolean gameOver = false;
        JButton jb = (JButton)arg0.getSource();
        if(jb == r)
        	gameOver = buttonPressed(0);
        else if(jb == o)
        	gameOver = buttonPressed(1);
        else if(jb == y)
        	gameOver = buttonPressed(2);
        else if(jb == g)
        	gameOver = buttonPressed(3);
        else if(jb == b)
        	gameOver = buttonPressed(4);
        else if(jb == p)
        	gameOver = buttonPressed(5);
        if(gameOver) { gameWon(); }
 	}
	
	public boolean buttonPressed(int val) {
		newConquered = new ArrayList<Point>();
		ArrayList<Point> newConq = new ArrayList<Point>();
		for(Point p : conquered) {
			for(Point pp : checkSurrounding(p, val)) {
				newConq.add(pp);
			}
			boolean noneLeft = false;
			ArrayList<Point> tempConq = new ArrayList<Point>();
			while(!noneLeft) {
				for(Point pp : newConq) {
					tempConq.add(pp);
					newConquered.add(pp);
				}
				newConq = new ArrayList<Point>();
				for(Point pp : tempConq)
					 for(Point ppp : checkSurrounding(pp, val))
						 newConq.add(ppp);
				if(newConq.size()==0) noneLeft = true;
			}
		}
		for(Point p : newConquered) conquered.add(p);
		refreshScreen(val);
		score++;
		scoreLabel.setText("Score: " + score);
		System.out.println(conquered.size());
		return (conquered.size()-1==height*length);
	}
	
	public ArrayList<Point> checkSurrounding(Point p, int val) {
		int px = (int)p.getX();
		int py = (int)p.getY();
		int x = height;
		int y = length;
		ArrayList<Point> surroundingPoints = new ArrayList<Point>();
		if(px>0)
			if(!conquered.contains(new Point(px-1, py))&&!newConquered.contains(new Point(px-1, py))&&boardValues[px-1][py]==val)
				surroundingPoints.add(new Point(px-1, py));
		if(px<x-1)
			if(!conquered.contains(new Point(px+1, py))&&!newConquered.contains(new Point(px+1, py))&&boardValues[px+1][py]==val)
				surroundingPoints.add(new Point(px+1, py));
		if(py>0)
			if(!conquered.contains(new Point(px, py-1))&&!newConquered.contains(new Point(px, py-1))&&boardValues[px][py-1]==val)
				surroundingPoints.add(new Point(px, py-1));
		if(py<y-1)
			if(!conquered.contains(new Point(px, py+1))&&!newConquered.contains(new Point(px, py+1))&&boardValues[px][py+1]==val)
				surroundingPoints.add(new Point(px, py+1));
		return surroundingPoints;
	}
	
	public void refreshScreen(int val) {
		for(Point p : conquered) boardValues[(int)p.getX()][(int)p.getY()] = val;
		Color c;
		for(int i = 0; i < height; i++)
			for(int j = 0; j < length; j++) {
				switch(boardValues[i][j]) {
					case 0:
						c = Color.RED;
						break;
					case 1:
						c = Color.ORANGE;
						break;
					case 2:
						c = Color.YELLOW;
						break;
					case 3:
						c = Color.GREEN;
						break;
					case 4:
						c = Color.BLUE;
						break;
					case 5:
						c = Color.MAGENTA;
						break;
					default:
						c = Color.WHITE;
				}
				board.setPixel(new Point(i, j), c);
			}
	}
	
	public void gameWon() {
		scoreLabel.setText("You won in " + score + " moves!");
	}
	
}