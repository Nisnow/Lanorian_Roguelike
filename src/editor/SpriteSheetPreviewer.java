package editor;

import java.awt.Color;

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
	}
	
	/*
	 * Displays the entire sprite sheet
	 */
	public void displayImage(SpriteSheet p_sheet)
	{
		int width = p_sheet.getImage().getWidth();
		int height = p_sheet.getImage().getHeight();
		
		renderer.drawSprite(p_sheet, new IntRect(0, 0, width, height));
		renderer.display();
		
		renderer.addScreenOverlay(Color.BLACK, 1.0f);
	}
	
	/*
	 * Indicates the current animations. Called whenever the
	 * user types something into the x, y, w, and h fields in Editor
	 */
	public void displayAnimationIndicators(AnimationList p_list)
	{
		for(int i = 0; i < p_list.getSize(); i++)
		{
			Animation a = p_list.getElementAt(i);
			
			// Draw number of rectangles for each frame of the animation
			for(int j = 0; j < a.getFrameCount(); i++)
			{
				DoubleRect rect = new DoubleRect(a.getFrame().x + (i * a.getFrame().w),
												 a.getFrame().y,
												 a.getFrame().w,
												 a.getFrame().h);
				renderer.drawRect(rect, Color.GREEN, 0.5f);
				Log.p("HEYA!");
			}

		}
	}
}
