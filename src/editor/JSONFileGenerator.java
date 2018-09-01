package editor;

import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import graphics.Animation;

public class JSONFileGenerator 
{
	private AnimationList animationList;
	private String name;
	
	public void setAnimationList(AnimationList p_list)
	{
		animationList = p_list;
	}
	
	public void setName(String p_name)
	{
		name = p_name;
	}
	
	/*
	 * Reference:
	 * https://www.mkyong.com/java/json-simple-example-read-and-write-json/
	 */
	@SuppressWarnings("unchecked")
	public void generate()
	{
		JsonObject obj = new JsonObject();
		
		// Store all animation data in a JSON Array
		JsonArray list = new JsonArray();
		for(int i = 0; i < animationList.getSize(); i++)
		{
			JsonObject animObj = new JsonObject();
			Animation a = animationList.getElementAt(i);
			
			animObj.addProperty("name", a.getName());
			animObj.addProperty("x", a.getFrame().x); 
			animObj.addProperty("y", a.getFrame().y);
			animObj.addProperty("w", a.getFrame().w);
			animObj.addProperty("h", a.getFrame().h);
			
			if(a.getFrameCount() != 0)
				animObj.addProperty("frames", a.getFrameCount());
			
			if(a.getInterval() != 0)
				animObj.addProperty("interval", a.getInterval()*1000f);
			
			if(a.isLoop())
				animObj.addProperty("loop", "true");
			
			list.add(animObj);
		}
		obj.add("animations", list);
		
		// I know this is bad practice but ehhhhh
		String path = "E:\\Lanorian Roguelike\\src\\resources\\images\\" + name + ".json";
		
		try(FileWriter file = new FileWriter(path))
		{
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			
			gson.toJson(obj, file);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		System.out.println("Saved file " + path);
	}
}
