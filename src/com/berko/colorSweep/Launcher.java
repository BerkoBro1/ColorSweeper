package com.berko.colorSweep;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Launcher extends JPanel implements ActionListener{

	static JFrame frame = new JFrame("Board Settings");
	JPanel panel = new JPanel(new GridLayout(0, 1));
	ButtonGroup bg;
	JRadioButton easy = new JRadioButton("Easy (7x7)");
	JRadioButton medium = new JRadioButton("Medium (10x10)");
	JRadioButton hard = new JRadioButton("Hard (15x15)");
	JRadioButton custom = new JRadioButton("Custom. Enter the height and width below:");
	JButton confirm = new JButton("Start");
	JTextField height = new JTextField(10);
	JTextField width = new JTextField(10);
	
	Launcher() {
		bg = new ButtonGroup();
		bg.add(easy);
		bg.add(medium);
		bg.add(hard);
		bg.add(custom);
		panel.add(easy);
		panel.add(medium);
		panel.add(hard);
		panel.add(custom);
		add(panel);
		add(height);
		add(width);
		add(confirm);
		confirm.addActionListener(this);
	}
	
	public static void main(String[] args) {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 300);
		Launcher window = new Launcher();
		frame.add(window);
		frame.setVisible(true);
		frame.setResizable(false);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		frame.dispose();
		if(easy.isSelected()) {
			new ColorSweep(7, 7);
		} else if(medium.isSelected()) {
			new ColorSweep(10, 10);
		} else if(hard.isSelected()) {
			new ColorSweep(15, 15);
		} else if(custom.isSelected()){
			new ColorSweep(Integer.parseInt(height.getText()), Integer.parseInt(width.getText()));
		}
	}
}