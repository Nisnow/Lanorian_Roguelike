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
	
	public Animation(String p_name)
	{
		name = p_name;
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
	
	/*
	 * Sets the room frame.
	 * Used in the editor.
	 */
	public void setFrame(IntRect p_frame)
	{
		frame = p_frame;
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
	
	/*
	 * Set number of frames in this animation.
	 * Used in the editor.
	 */
	public void setFrameCount(int p_count)
	{
		frameCount = p_count;
	}

	/**
	 * Get the amount of time in between frames in seconds
	 */
	public float getInterval() 
	{
		return interval;
	}
	
	/*
	 * Set the amount of time between frames in seconds.
	 * Used in the editor;
	 */
	public void setInterval(float p_interval)
	{
		interval = p_interval;
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
	
	public void setLoop(boolean p_loop)
	{
		loop = p_loop;
	}

	public String getName() 
	{
		return name;
	}
	
	public void setName(String p_name)
	{
		name = p_name;
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
	
	public String toString()
	{
		return name;
	}
}
