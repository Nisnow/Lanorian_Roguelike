package graphics;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class SpriteSheet
{
	private ArrayList<Animation> animationList = new ArrayList<Animation>();
	private BufferedImage image;
	
	public SpriteSheet() {}
	
	public SpriteSheet(String p_path)
	{
		openResource(p_path);
	}
	/**
	 * Get animation by name
	 * @param name Name of the animation
	 * @return Animation object. Null if not found.
	 */
	public Animation getAnimation(String p_name)
	{
		for(Animation a : animationList)
			if(a.getName().equals(p_name))
				return a;
		return null;
	}
	
	/**
	 * Open an image from the resources folder. Note that it only opens png and json files
	 * @param p_path The name of the image without the extension
	 */
	public void openResource(String p_path)
	{
		try
		{
			InputStream imageStream = getClass().getClassLoader().getResourceAsStream(p_path + ".png");
			if (imageStream == null)
				throw new FileNotFoundException("Could not find image resource for \"" + p_path + ".png\"");
			openImage(imageStream);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		try
		{	
			InputStream atlasStream = getClass().getClassLoader().getResourceAsStream(p_path + ".json");
			if (atlasStream == null)
				throw new FileNotFoundException("Could not find atlas resource for \"" + p_path + ".json\"");
			openAtlas(atlasStream);	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
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
	 * @throws IOException if parsing fails
	 */
	private void openAtlas(InputStream p_stream) throws Exception
	{
		JSONParser parser = new JSONParser();
		
		try
		{
			// Parse the document
			Object obj = parser.parse(new InputStreamReader(p_stream));
			JSONObject jsonObject = (JSONObject) obj;

			// Get the array of animation data
			JSONArray atlas = (JSONArray) jsonObject.get("animations");
			Iterator i = atlas.iterator();
			
			// Create an Animation object for each animation JSON object
			while(i.hasNext())
			{
				JSONObject data = (JSONObject) i.next();
				Animation animation = new Animation();
				animation.parse(data);
				animationList.add(animation);
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}
}
