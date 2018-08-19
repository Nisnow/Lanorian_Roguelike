package graphics;

import javax.print.attribute.standard.PrinterLocation;

import util.Clock;
import util.IntRect;
import util.Vector;

public class Sprite implements Drawable
{
	private SpriteSheet spriteSheet;
	private Animation animation;
	private Vector position = new Vector(0,0 );
	private Vector scale = new Vector(1, 1);
	private Clock clock = new Clock();
	
	public Sprite() {} 
	
	public Sprite(SpriteSheet p_sheet)
	{
		setSpriteSheet(p_sheet);
	}
	
	public Sprite(SpriteSheet p_sheet, String p_animation)
	{
		setSpriteSheet(p_sheet);
		setAnimation(p_animation);
	}

	@Override
	public void draw(Renderer p_renderer) 
	{
		IntRect frame;
		System.out.println("sdfjslkdfj");
		if(animation.getFrameCount() > 1 
				&& animation.getInterval() > 0)
		{
			// Display current frame of an animation
			int frameIdx = (int) (clock.getElapse() / animation.getInterval());
			frame = animation.getFrame(frameIdx);
		}
		else
		{
			// Unmoving image
			frame = animation.getFrame();
		}
		
		p_renderer.drawSprite(spriteSheet, frame);
	}
	
	public SpriteSheet getSpriteSheet()
	{
		return spriteSheet;
	}
	
	public void setSpriteSheet(SpriteSheet p_spriteSheet)
	{
		spriteSheet = p_spriteSheet;
	}

	public Animation getAnimation() 
	{
		return animation;
	}

	public void setAnimation(String p_animationName)
	{
		if(animation == null || !p_animationName.equals(animation.getName()))
		{
			animation = spriteSheet.getAnimation(p_animationName);
			clock.restart();
		}
	}
	
	public void setAnimation(Animation p_animation)
	{
		if(animation == null || animation != p_animation)
		{
			animation = p_animation;
			clock.restart();
		}
	}
	
	public void playAnimation()
	{
		clock.start();
	}
	
	public void pauseAnimation()
	{
		clock.pause();
	}
	
	public void stopAnimation()
	{
		clock.stop();
	}
	
	public void restartAnimation()
	{
		clock.restart();
	}

	public Vector getPosition() 
	{
		return position;
	}
	
	public void setPosition(double p_x, double p_y)
	{
		position.set(p_x, p_y);
	}
	
	public void setPosition(Vector p_position) 
	{
		position.set(p_position);
	}

	public Vector getScale() 
	{
		return scale;
	}
	
	public void setScale(double p_x, double p_y)
	{
		position.set(p_x, p_y);
	}
	
	public void setScale(Vector p_scale) 
	{
		p_scale.set(p_scale);
	}
}
