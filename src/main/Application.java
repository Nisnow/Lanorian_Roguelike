package main;

import java.awt.geom.AffineTransform;

import graphics.*;
import util.Clock;

public class Application
{
	// temporary stuff til I implement GameState
	private static boolean running = true;
	private static final int FPS = 60;
	
	private static AffineTransform tempMat = new AffineTransform();
	
	public static void main(String[] args)
	{
		/* T E M P O R A R Y */
		GUI gui = new GUI();
		
		Renderer renderer = gui.renderer;
		RenderList renderList = new RenderList();
		
		SpriteSheet sheet1 = new SpriteSheet("resources/images/narry");
		Sprite mountainDew = new Sprite(sheet1, "default");
		mountainDew.setPosition(100, 64);
		mountainDew.playAnimation();
		
		renderList.addDrawable(mountainDew);
		
		SpriteSheet floorSheet = new SpriteSheet("resources/images/tilemap");
		Sprite floor = new Sprite(floorSheet, "base");
		floor.setPosition(100, 115);
		
		renderList.addDrawable(floor);
		
		SpriteSheet sheet2 = new SpriteSheet("resources/images/birboi");
		Sprite pixie = new Sprite(sheet2, "fly");
		pixie.setPosition(300, 100);
		pixie.playAnimation();
		
		renderList.addDrawable(pixie);
		
		//tempMat.translate(100, 0);
		
		System.out.println("AAAAAAAAAAAA");
		
		Clock gameClock = new Clock();
		float delta = 0;
		
		int i = 0;
		// Temporary game loop
		while(running)
		{
			delta = gameClock.getElapse();
			gameClock.restart();
			
			// temp thingy to test shader movement for later
			if(mountainDew.getScale().x == 1) //facing left
				i++;
			else
				i--;
			
			if(i == 200)
				mountainDew.setScale(-1, 1);
			if(i == 0)
				mountainDew.setScale(1, 1);
			
			mountainDew.setPosition(100 + i, 64);
			
			// Clear the buffer
			renderer.clear();
			
			// Draw stuff
			renderList.draw(renderer);
			
			// Ensure the current frame is being displayed
			renderer.display();
			
			// Delay
			try
			{
				long totalNanos = (int)(1e9/FPS) - (int)(gameClock.getElapse()*1e9f);
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
