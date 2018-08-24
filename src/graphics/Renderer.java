package graphics;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JPanel;

import util.DoubleRect;
import util.IntRect;

public class Renderer
{
	private BufferedImage frontBuffer, backBuffer;
	private Graphics2D graphics;
	public JPanel panel;
	
	private int width, height;
	private float scale = 1.0f;
	
	//private Lock frameSwapLock = new ReentrantLock(); //later for Thread
	
	/*
	 * Creates a renderer for displaying all entities. Add to every GameState
	 */
	public Renderer()
	{
		// Get the dimensions of the screen
		width = Toolkit.getDefaultToolkit().getScreenSize().width;
		height = Toolkit.getDefaultToolkit().getScreenSize().height;
		
		// Calculate the scale according to the screen size
		scale = (width * scale) / 800;
		
		// Create the buffers to draw stuff on
		frontBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		backBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		graphics = backBuffer.createGraphics();
		
		initialize();
	}
	
	public Renderer(JPanel p_panel)
	{
		width = p_panel.getWidth();
		height = p_panel.getHeight();
		
		// Create the buffers to draw stuff on
		frontBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		backBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		graphics = backBuffer.createGraphics();
		
		initialize();
	}
	
	/**
	 * Creates the JPanel canvas. Called in constructor only.
	 */
	private void initialize()
	{
		// Create the JPanel to hold the canvas
		panel = new JPanel() {
			@Override
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				g.drawImage(frontBuffer, 0, 0, this);
				repaint();
			}
		};
		
		panel.setFocusable(true);
	}
	
	/**
	 * Switches the buffers. 
	 * Ensures the current frame is being displayed
	 */
	public void display()
	{
		BufferedImage temp = backBuffer;
		backBuffer = frontBuffer;
		frontBuffer = temp;
		
		graphics = backBuffer.createGraphics();
	}
	
	/**
	 * Clears the screen before anything is drawn every frame
	 */
	public void clear()
	{
		addScreenOverlay(Color.BLACK, 1.0f);
	}
	
	/*
	 * Set scale that only the size of images are affected by
	 */
	public void setScale(float p_scale)
	{
		scale = p_scale;
	}
	
	/**
	 * Draws a sprite
	 * @param p_sheet the sprite sheet of the image
	 * @param p_frame the current frame to draw
	 */
	public void drawSprite(SpriteSheet p_sheet, IntRect p_frame)
	{
		//reset opacity
		graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		
		//scale image according to the current scale
		AffineTransform scaleTransform = new AffineTransform();
		scaleTransform.scale(scale, scale);
		
		//temporary
		graphics.setTransform(scaleTransform);
		
		graphics.drawImage(p_sheet.getImage(), 0, 0, p_frame.w, p_frame.h,
				p_frame.x, p_frame.y, p_frame.x + p_frame.w, p_frame.y + p_frame.h, panel);
	}
	
	/**
	 * Draws a solid rectangle
	 * @param p_rect the dimensions of the rectangle
	 * @param p_color the color of the rectangle (Color.RED, Color.BLUE, etc)
	 * @param p_opacity 0.0f transparent to 1.0f (opaque)
	 */
	public void drawRect(DoubleRect p_rect, Color p_color, float p_opacity)
	{
		graphics.setColor(p_color);
		graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, p_opacity));
		graphics.draw(new Rectangle2D.Double(p_rect.x, p_rect.y, p_rect.w, p_rect.h));
	}
	
	/**
	 * Draws a rectangle border
	 * @param p_rect the dimensions of the rectangle
	 * @param p_color the color of the rectangle (Color.RED, Color.BLUE, etc.)
	 * @param p_opacity 0.0f transparent to 1.0f (opaque)
	 * @param p_thickness THICCness in pixels
	 */
	public void drawRect(DoubleRect p_rect, Color p_color, float p_opacity, float p_thickness)
	{
		graphics.setColor(p_color);
		graphics.setStroke(new BasicStroke(p_thickness));
		graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, p_opacity));
		graphics.draw(new Rectangle2D.Double(p_rect.x, p_rect.y, p_rect.w, p_rect.h));
	}
	
	/**
	 * Cover the canvas with a color
	 * @param p_color The color to overlay the screen
	 * @param p_opacity 0.0f transparent to 1.0f (opaque)
	 */
	public void addScreenOverlay(Color p_color, float p_opacity)
	{
		graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, p_opacity));
		
		graphics.setColor(p_color);
		graphics.fillRect(0, 0, width, height);
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public JPanel getComponent()
	{
		return panel;
	}
}
