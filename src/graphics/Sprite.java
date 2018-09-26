package graphics;

import org.joml.Matrix4f;

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
	
	public Sprite(Texture sheet)
	{
		setTexture(sheet);
	}
	
	public Sprite(Texture sheet, String animation)
	{
		setTexture(sheet);
		setAnimation(animation);
	}

	@Override
	public void draw(Renderer renderer) 
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
		
		Matrix4f spriteTransform = new Matrix4f();
		
		spriteTransform.translate((float) position.x, (float) position.y, 0.0f);
		spriteTransform.rotate((float) rotation, 0.0f, 0.0f, 1.0f);
		spriteTransform.scale((float) scale.x, (float) scale.y, 0.0f);
		/*
		p_renderer.pushTransform(spriteTransform);
		p_renderer.drawSprite(Texture, frame);
		p_renderer.popTransform();*/
	}
	
	public Texture getTexture()
	{
		return Texture;
	}
	
	public void setTexture(Texture texture)
	{
		Texture = texture;
	}

	public Animation getAnimation() 
	{
		return animation;
	}

	public void setAnimation(String animationName)
	{
		if(animation == null || !animationName.equals(animation.getName()))
		{
			animation = Texture.getAnimation(animationName);
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
	
	public void setPosition(double x, double y)
	{
		position.set(x, y);
	}
	
	public void setPosition(Vector position) 
	{
		this.position.set(position);
	}

	public Vector getScale() 
	{
		return scale;
	}
	
	public void setScale(double x, double y)
	{
		scale.set(x, y);
	}
	
	public void setScale(Vector scale) 
	{
		this.scale.set(scale);
	}
	
	public void resetScale()
	{
		scale.set(DEFAULT_SCALE);
	}
	
	public double getRotation()
	{
		return rotation;
	}
	
	public void setRotation(double radians)
	{
		rotation = radians;
	}
}