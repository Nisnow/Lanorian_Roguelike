package components;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import graphics.Animation;
import graphics.Renderer;
import graphics.Texture;
import util.JsonFile;

public class GraphicsComponent implements Component, JsonFile
{
	private ArrayList<Animation> animationList = new ArrayList<Animation>();
	private Texture texture;
	private Animation currentAnimation;
	
	/*
	 * Create a batch for the renderer to use
	 */
	public void render(Renderer renderer)
	{

	}
	
	public Texture getTexture()
	{
		return texture;
	}
	
	public void setTexture(Texture texture)
	{
		this.texture = texture;
	}

	@Override
	public void openJson(String path) throws Exception
	{
		JsonParser parser = new JsonParser();
		
		try
		{
			// Parse the document
			Object obj = parser.parse(path);
			JsonObject jsonObject = (JsonObject) obj;

			// Get the array of animation data
			JsonArray atlas = (JsonArray) jsonObject.get("atlas");
			Iterator i = atlas.iterator();
			
			// Create an Animation object for each animation JSON object
			while(i.hasNext())
			{
				JsonObject data = (JsonObject) i.next();
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
