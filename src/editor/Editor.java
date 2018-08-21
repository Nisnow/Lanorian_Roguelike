package editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

public class Editor
{
	private JFrame window;
	private int width = 1250;
	private int height = 750;
	
	public Editor()
	{
		window = new JFrame("Atlas Editor");
		window.setSize(width, height);
		window.setResizable(false);
		window.setVisible(true); 
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
		
		JPanel imagePreviewer = new JPanel();
		imagePreviewer.setBackground(Color.BLACK); //TODO: toggle between black and white 
		imagePreviewer.setPreferredSize(new Dimension((int)(width*0.75), height));

		JPanel dataInputArea = new JPanel(new FlowLayout());
		dataInputArea.setBackground(Color.GRAY);
		dataInputArea.setPreferredSize(new Dimension((int)(width *0.25), height));

		container.add(imagePreviewer);
		container.add(dataInputArea);
		
		window.setLocationRelativeTo(null);
		window.add(container);
		
		JTextField frameField = new JTextField();
		JTextField intervalField = new JTextField();
	}
}
