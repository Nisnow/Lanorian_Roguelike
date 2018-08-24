package editor;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import graphics.Animation;
import graphics.Renderer;
import graphics.Sprite;
import graphics.SpriteSheet;
import util.Clock;
import util.Log;

public class AnimationPreviewer extends JPanel implements Runnable
{
	private Renderer renderer;
	private Sprite currentSprite;
	
	// Pause-checkers for the animation thread
	private final Object pauseLock = new Object();
	
	private volatile boolean running = true;
	private volatile boolean paused = false;
	
	public AnimationPreviewer()
	{
		setLayout(new BorderLayout(0, 0));
		setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
		setBackground(Color.RED);
	}
	
	public void initRenderer()
	{
		renderer = new Renderer(this);
		
		this.validate();
		this.repaint();
		
		renderer.setScale(3.0f);
	}
	
	public void displayAnimation(SpriteSheet p_sheet, Animation p_animation)
	{
		currentSprite = new Sprite(p_sheet);
		currentSprite.setAnimation(p_animation);
		
		currentSprite.draw(renderer);
		renderer.display();
	}

	/**
	 * Plays the animation.
	 */
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
			delta = animClock.getElapse();
			animClock.restart();
			
			renderer.clear();
			
			currentSprite.draw(renderer);
			
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
			Log.p("Cheese and rice");
		}
	}
	
	public void stopAnimation()
	{
		running = false;
		Log.p("stopped");
	}
	
	public void pauseAnimation()
	{
		Log.p("paused");
		paused = true;
	}
	
	public void resumeAnimation()
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
}
