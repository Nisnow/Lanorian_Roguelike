package graphics;

import org.json.simple.JSONObject;

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
	public IntRect getFrame(int p_frame)
	{
		int frameIdx = 0;
		if(loop)
			frameIdx = (p_frame % frameCount + frameCount) % frameCount;
		else
			frameIdx = Math.max(Math.min(p_frame, frameCount - 1), 0);
		
		IntRect currentFrame = new IntRect(frame);
		currentFrame.x += frame.w * frameIdx;
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
	 * @return
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
	 * @param p_object the JSONObject to retrieve data from
	 */
	public void parse(JSONObject p_object)
	{
		// TODO: refactor so there aren't so many casts?
		name = (String) p_object.get("name");
		
		frame.x = Integer.parseInt((String) p_object.get("x"));
		frame.y = Integer.parseInt((String) p_object.get("y"));
		frame.w = Integer.parseInt((String) p_object.get("w"));
		frame.h = Integer.parseInt((String) p_object.get("h"));
		
		if(hasObject(p_object, "frames"))
			frameCount = Integer.parseInt((String) p_object.get("frames"));
		else
			frameCount = 1;
		
		if(hasObject(p_object, "interval"))
			interval =  Float.parseFloat((String) p_object.get("interval")) /1000f; // convert from milliseconds to seconds
		
		if(hasObject(p_object, "loop"))
			loop =  Boolean.parseBoolean((String) p_object.get("loop"));
	}
	
	/**
	 * Checks if an animation has a certain value from a JSON key
	 */
	private boolean hasObject(JSONObject p_object, String p_key)
	{
		return ((String) p_object.get(p_key)) != null;
	}
}
