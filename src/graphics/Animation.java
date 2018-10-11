package graphics;

import com.google.gson.JsonObject;

import util.IntRect;

public class Animation
{
	private IntRect frame;
	private int frameCount;
	private float interval;
	private boolean loop;
	private String name;
	
	public Animation()
	{
		frame = new IntRect(0, 0, 0, 0);
		loop = false;
		frameCount = 0;
		interval = 0;
	}

	/*
	 * Get root frame
	 */
	public IntRect getFrame() 
	{
		return frame;
	}
	
	/**
	 * Get frame at an index. Calculates looping
	 */
	public IntRect getFrame(int frame)
	{
		int frameIdx = 0;
		if(loop)
			frameIdx = (frame % frameCount + frameCount) % frameCount;
		else
			frameIdx = Math.max(Math.min(frame, frameCount - 1), 0);
		
		IntRect currentFrame = new IntRect(this.frame);
		currentFrame.x += this.frame.w * frameIdx;
		return currentFrame;
	}

	/*
	 * Get the number of frames in this animation
	 */
	public int getFrameCount() 
	{
		return frameCount;
	}

	/**
	 * Get the amount of time in between frames in seconds
	 */
	public float getInterval() 
	{
		return interval;
	}
	
	/**
	 * Returns the duration from the start of the first frame
	 * to the end of the last frame.
	 */
	public float getDuration()
	{
		return interval * frameCount;
	}

	public boolean isLoop() 
	{
		return loop;
	}
	
	public String getName() 
	{
		return name;
	}
	
	/**
	 * Parse animation information from a JSON Object
	 * @param object the JSONObject to retrieve data from
	 */
	public void parse(JsonObject object)
	{
		name = object.get("name").getAsString();
		
		frame.x = object.get("x").getAsInt();
		frame.y = object.get("y").getAsInt();
		frame.w = object.get("w").getAsInt();
		frame.h = object.get("h").getAsInt();
		
		if(hasObject(object, "frames"))
			frameCount = object.get("frames").getAsInt();
		else
			frameCount = 1;
		
		if(hasObject(object, "interval"))
			interval =  object.get("interval").getAsFloat() /1000f; // convert from milliseconds to seconds
		
		if(hasObject(object, "loop"))
			loop =  object.get("loop").getAsBoolean();
	}
	
	/**
	 * Checks if an animation has a certain value from a JSON key
	 */
	private boolean hasObject(JsonObject object, String key)
	{
		return object.get(key) != null;
	}
}