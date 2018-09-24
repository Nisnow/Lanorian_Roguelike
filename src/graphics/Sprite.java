package graphics;

import java.awt.geom.AffineTransform;

import util.Clock;
import util.IntRect;
import util.Vector;

public class Sprite implements Drawable
{
	private final Vector DEFAULT_SCALE = new Vector(1, 1);
	
	private Texture Texture;
	private Animation animation;
	private Vector position = new Vector(0, 0);
	private Vector scale = DEFAULT_SCALE;
	private double rotation; // in radians
	private Clock clock = new Clock();
	
	public Sprite() {} 
	
	public Sprite(Texture p_sheet)
	{
		setTexture(p_sheet);
	}
	
	public Sprite(Texture p_sheet, String p_animation)
	{
		setTexture(p_sheet);
		setAnimation(p_animation);
	}

	@Override
	public void draw(Renderer p_renderer) 
	{
		IntRect frame;
		
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
		
		/*
		AffineTransform spriteTransform = new AffineTransform();
		
		spriteTransform.translate(position.x, position.y);
		spriteTransform.rotate(rotation);
		spriteTransform.scale(scale.x, scale.y);
		
		p_renderer.pushTransform(spriteTransform);
		p_renderer.drawSprite(Texture, frame);
		p_renderer.popTransform();*/
	}
	
	public Texture getTexture()
	{
		return Texture;
	}
	
	public void setTexture(Texture p_Texture)
	{
		Texture = p_Texture;
	}

	public Animation getAnimation() 
	{
		return animation;
	}

	public void setAnimation(String p_animationName)
	{
		if(animation == null || !p_animationName.equals(animation.getName()))
		{
			animation = Texture.getAnimation(p_animationName);
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
		scale.set(p_x, p_y);
	}
	
	public void setScale(Vector p_scale) 
	{
		scale.set(p_scale);
	}
	
	public void resetScale()
	{
		scale.set(DEFAULT_SCALE);
	}
	
	public double getRotation()
	{
		return rotation;
	}
	
	public void setRotation(double p_radians)
	{
		rotation = p_radians;
	}
}