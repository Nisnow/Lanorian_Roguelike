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

public class AnimationPreviewer extends JPanel implements Runnable, Previewable
{
	private Renderer renderer;
	private Sprite currentSprite;
	
	// Pause-checkers for the animation thread
	private volatile boolean running = false;
	private volatile boolean paused = false;
	
	private Thread animationThread = new Thread(this);
	
	/*
	 * Top-right panel that displays the selected animation
	 */
	public AnimationPreviewer()
	{
		setLayout(new BorderLayout(0, 0));
		setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
	}
	
	/*
	 * Initializes the renderer after width and height of this
	 * panel has been determined during run-time.
	 */
	@Override
	public void initRenderer()
	{
		renderer = new Renderer(this);
		this.add(renderer.getComponent());
		
		this.validate();
		this.repaint();
		
		// Validate resets the color to white, so change that
		renderer.addScreenOverlay(Color.BLACK, 1.0f);
		// TODO: zooming and moving
		renderer.setScale(3.0f);
	}
	
	/*
	 * Displays the first frame of the animation
	 */
	public void displayAnimation(SpriteSheet p_sheet, Animation p_animation)
	{
		currentSprite = new Sprite(p_sheet);
		currentSprite.setAnimation(p_animation);
		
		currentSprite.draw(renderer);
		renderer.display();
		
		renderer.addScreenOverlay(Color.BLACK, 1.0f);
	}
	
	/*
	 * Call this method to play the animation.
	 * This method then calls Runnable's run() method.
	 */
	public void playAnimation()
	{
		running = true;
		animationThread.start();
	}

	/*
	 * Not this one!! #BadThings happen.
	 */
	@Override
	public void run() 
	{
		Clock animClock = new Clock();
		float delta = 0;
		
		while(running)
		{
			if(!paused)
			{
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
			}
		}
	}
	
	/*
	 * Stops the animation thread completely
	 */
	public void stopAnimation()
	{
		running = false;
	}
	
	public void pauseAnimation()
	{
		paused = true;
	}
	
	public void resumeAnimation()
	{
		paused = false;
	}
	
	public boolean isPaused()
	{
		return paused;
	}
	
	public boolean isRunning()
	{
		return running;
	}

	public Sprite getSprite()
	{
		return currentSprite;
	}

	@Override
	public Renderer getRenderer()
	{
		return renderer;
	}
}
