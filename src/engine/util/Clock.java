package engine.util;

public class Clock
{
	private long startTime;
	private long pauseTime;
	private boolean isPaused;
	
	/**
	 * Clock constructor. The clock begins at construction.
	 */
	public Clock()
	{
		startTime = System.nanoTime();
		isPaused = false;
	}
	
	/**
	 * Get elapse from the start of this clock to now in seconds.
	 * @return Elapse in seconds
	 */
	public float getElapse()
	{
		long elapseNano = System.nanoTime() - startTime;
		return (float) elapseNano * 1e-9f; // Convert to seconds
	}
	
	/**
	 * Start the clock. Use getElapse() to get the time.
	 */
	public void start()
	{
		long nowTime = System.nanoTime();
		if (isPaused)
			startTime += nowTime - pauseTime;
		isPaused = false;
	}
	
	/**
	 * Pause and reset the clock to 0.
	 */
	public void stop()
	{
		pause();
		restart();
	}
	
	/**
	 * Pause this clock
	 */
	public void pause()
	{
		pauseTime = System.nanoTime();
		isPaused = true;
	}
	
	/**
	 * Reset this clock to 0 seconds.
	 */
	public void restart()
	{
		if (isPaused)
			startTime = pauseTime; // Distance between start time and pause time becomes 0.
		else
			startTime = System.nanoTime();
	}
	
	/**
	 * Check if this clock is paused.
	 * @return True if paused.
	 */
	public boolean isPaused()
	{
		return isPaused;
	}
}
