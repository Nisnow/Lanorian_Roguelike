package graphics;

import util.Clock;
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
		
	}

	@Override
	public void draw(Renderer p_renderer) 
	{
		
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
