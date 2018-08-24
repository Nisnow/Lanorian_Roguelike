package editor;

import graphics.*;
import util.Clock;

public class AnimationThread extends Thread
{
	private volatile boolean running = true;
	private volatile boolean paused = false;
	
	private final Object pauseLock = new Object();
	
	private Renderer renderer;
	private RenderList list;
	
	public AnimationThread() {}
	
	public AnimationThread(Renderer p_renderer, RenderList p_list)
	{
		renderer = p_renderer;
		list = p_list;
	}
	
	@Override
	public void run() 
	{
		Clock animClock = new Clock();
		float delta = 0;
		
		while(running)
		{
			synchronized (pauseLock)
			{
				if(!running)
					break;
				if(paused)
				{
					try
					{
						pauseLock.wait();
					}
					catch (InterruptedException e)
					{
						break;
					}
					if (!running)
						break;
				}
			}
			
			//code here
			delta = animClock.getElapse();
			animClock.restart();
			
			renderer.clear();
			
			list.draw(renderer);
			
			renderer.display();
			
			try
			{
				long totalNanos = (int)(1e9/60) - (int)(animClock.getElapse()*1e9f);
				if(totalNanos > 0)
				{
					int nanos = (int) (totalNanos % 1000000);
					long milis = (totalNanos - nanos) / 1000000;
					Thread.sleep(milis, (int)nanos);
				}
			} catch (InterruptedException e)
			{
				break;
			}
			
		}
	}
	
	public void stopThread()
	{
		running = false;
		resume();
	}
	
	public void pauseThread()
	{
		paused = true;
	}
	
	public void resumeThread()
	{
		synchronized (pauseLock)
		{
			paused = false;
			pauseLock.notifyAll();
		}
	}
	
	public boolean isPaused()
	{
		return paused;
	}
	
	public void setRenderer(Renderer p_renderer)
	{
		renderer = p_renderer;
	}
	
	public void setRenderList(RenderList p_list)
	{
		list = p_list;
	}
}
