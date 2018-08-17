package graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
	
	/**
	 * Open an image
	 * @param p_stream Stream to JSON file atlas
	 * @throws IOException throws if image isn't formatted correctly
	 */
	private void openImage(InputStream p_stream) throws IOException
	{
		try
		{
			image = ImageIO.read(p_stream);
		}
		catch(IOException e)
		{
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * Open an image
	 * @param p_stream Stream to JSON file atlas
	 * @throws IOException throws if parsing fails
	 */
	private void openAtlas(InputStream p_stream) throws Exception
	{
		JSONParser parser = new JSONParser();
	}
}
