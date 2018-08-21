package editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;

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
		imagePreviewer.setPreferredSize(new Dimension((int)(width*2/3), height));

		JPanel dataContainer = new JPanel();
		dataContainer.setBackground(Color.WHITE);
		dataContainer.setPreferredSize(new Dimension((int)(width*1/3), height));
		dataContainer.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
		dataContainer.setLayout(new BoxLayout(dataContainer, BoxLayout.Y_AXIS));

		
		//JPanel panel1 = new JPanel(new BorderLayout());
		JPanel panel1 = new JPanel();
		panel1.setBackground(Color.GRAY);
		
		//String[] random = {"adfsf"," sdfs", "FDG"};
		//JList animationList = new JList(random);
		//panel1.add(new JScrollPane(animationList), BorderLayout.CENTER);
		
		dataContainer.add(panel1);
		

		
		JPanel panel2 = new JPanel();
		panel2.setBackground(Color.RED);
		dataContainer.add(panel2);
		
		JPanel panel3 = new JPanel();
		panel3.setBackground(Color.BLUE);
		dataContainer.add(panel3);
		//JList animationList = new JList();
		//animationList.setPreferredSize(new Dimension((int)(width*1/3), (int)(height*0.25)));
		
		/*
		JPanel dataInputArea = new JPanel();
		dataInputArea.setBackground(Color.GRAY);
		dataInputArea.setPreferredSize(new Dimension((int)(width*1/3), (int)(height*0.5)));
		dataInputArea.setLayout(new BoxLayout(dataInputArea, BoxLayout.Y_AXIS));*/
		
		//dataContainer.add(animationList);
		//dataContainer.add(dataInputArea);
		
		container.add(imagePreviewer);
		container.add(dataContainer);
		//dataInputArea.add(listPanel);
		
		JTextField frameField = new JTextField();
		JTextField intervalField = new JTextField();
		
		window.setLocationRelativeTo(null);
		window.add(container);
	}
}
