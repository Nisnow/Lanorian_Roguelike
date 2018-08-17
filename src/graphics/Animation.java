package graphics;

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
		return null;
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
	
	public void parse()
	{
		// TODO: parse JSON files
	}
}
