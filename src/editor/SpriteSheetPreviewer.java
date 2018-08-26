package editor;

import java.awt.Color;

import javax.swing.JPanel;

import graphics.Renderer;
import graphics.SpriteSheet;
import util.IntRect;

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
}
