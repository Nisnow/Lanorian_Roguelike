package editor;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JPanel;

import graphics.Animation;
import graphics.Renderer;
import graphics.SpriteSheet;
import util.DoubleRect;
import util.IntRect;
import util.Log;

public class SpriteSheetPreviewer extends JPanel implements Previewable
{
	// TODO: zooming, scrolling, rectangle display of animation frames
	private Renderer renderer;
	private AnimationList list;
	private SpriteSheet spriteSheet;
	
	private final float SCALE_FACTOR = 0.5f;
	// http://blog.sodhanalibrary.com/2015/04/zoom-in-and-zoom-out-image-using-mouse_9.html#.W4B4dM5Kj6o
	public SpriteSheetPreviewer() {}
	
	@Override
	public void initRenderer()
	{
		renderer = new Renderer(this);
		this.add(renderer.getComponent());
		
		this.validate();
		this.repaint();
		
		renderer.addScreenOverlay(Color.BLACK, 1.0f);
		renderer.setScale(3.0f);
		
		renderer.getComponent().addMouseWheelListener(new MouseWheelListener()
		{
			@Override
			public void mouseWheelMoved(MouseWheelEvent mwe)
			{
				int notches = mwe.getWheelRotation();
				if(notches < 0) //scroll down; zoom out
				{
					renderer.setScale(renderer.getScale() - SCALE_FACTOR);
					updateImage();
				}
				else //scroll up; zoom in
				{
					renderer.setScale(renderer.getScale() + SCALE_FACTOR);
					updateImage();
				}
			}
			
			public void updateImage()
			{
				renderer.clear();
				displayImage(spriteSheet);
			}
		});
		renderer.getComponent().addMouseMotionListener(new MouseMotionListener()
		{
			@Override
			public void mouseDragged(MouseEvent arg0) 
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseMoved(MouseEvent arg0) 
			{
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public void setAnimationList(AnimationList p_list)
	{
		list = p_list;
	}
	
	/*
	 * Displays the entire sprite sheet
	 */
	public void displayImage(SpriteSheet p_sheet)
	{
		spriteSheet = p_sheet;
		
		int width = p_sheet.getImage().getWidth();
		int height = p_sheet.getImage().getHeight();
		
		renderer.drawSprite(p_sheet, new IntRect(0, 0, width, height));
		displayFrameIndicators();
		renderer.display();
		
		renderer.addScreenOverlay(Color.BLACK, 1.0f);
	}
	
	/*
	 * Indicates the current animations. Called whenever the
	 * user types something into the x, y, w, and h fields in Editor
	 */
	public void displayFrameIndicators()
	{
		for(int i = 0; i < list.getSize(); i++)
		{
			Animation a = list.getElementAt(i);
			
			// Draw number of rectangles for each frame of the animation
			for(int j = 0; j < a.getFrameCount(); j++)
			{
				DoubleRect rect = new DoubleRect(a.getFrame().x + (j * a.getFrame().w),
												 a.getFrame().y,
												 a.getFrame().w,
												 a.getFrame().h);
				renderer.drawRect(rect, Color.GREEN, 0.5f);
			}
		}
	}

	public SpriteSheet getSpriteSheet() 
	{
		return spriteSheet;
	}

	@Override
	public Renderer getRenderer()
	{
		return renderer;
	}
}
