package graphics;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SpriteSheet
{
	private ArrayList<Animation> animations = new ArrayList<Animation>();
	private BufferedImage image;
	
	/**
	 * Get animation by name
	 * @param name Name of the animation
	 * @return Animation object. Null if not found.
	 */
	public Animation getAnimation(String p_name)
	{
		for(Animation a : animations)
			if(a.getName().equals(p_name))
				return a;
		return null;
	}
	
	public void openResource(String p_path)
	{
		
	}
	
	/**
	 * Get the BufferedImage object. Used internally in the renderer.
	 * @return BufferedImage object.
	 */
	public BufferedImage getImage()
	{
		return image;
	}
	
	private void openImage()
	{
		
	}
	
	private void openAtlas()
	{
		
	}
}
