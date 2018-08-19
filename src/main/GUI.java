package main;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

import graphics.Renderer;

public class GUI
{
	private JFrame window;
	
	// default window ratios calculated from my desktop computer
	private final float xRatio = .9f;
	private final float yRatio = .6f;
	private final float scale  = .8f;
	
	public Renderer renderer = new Renderer(); //temporary until I implement GameState
	
	public GUI()
	{
		// get the resolution of the user's computer
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		
		// get ratio of the current dimensions versus the default
		float proportionalWidth  = gd.getDisplayMode().getWidth()  / xRatio;
		float proportionalHeight = gd.getDisplayMode().getHeight() / yRatio;
		
		int width;
		int height;
		
		// calculate correct dimensions for constant aspect ratio
		if(proportionalWidth > proportionalHeight)
		{
			height = (int) (gd.getDisplayMode().getHeight() * scale);
			width  = (int) (height * xRatio/yRatio);
		}
		else
		{
			width  = (int) (gd.getDisplayMode().getWidth() * scale);
			height = (int) (width * yRatio/xRatio);
		}		
		
		window = new JFrame("Lanorian Roguelike");
		
		window.setSize(width, height);
		window.setResizable(true);
		window.setVisible(true);
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(renderer.getComponent());
	}
	
	public JFrame getWindow()
	{
		return window;
	}
}
