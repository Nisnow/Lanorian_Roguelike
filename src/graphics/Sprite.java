package graphics;

import org.joml.Matrix4f;
import org.joml.Vector2f;

import util.Clock;
import util.IntRect;

public class Sprite implements Renderable
{
	private final Vector2f DEFAULT_SCALE = new Vector2f(1.0f, 1.0f);
	
	private Texture texture;
	private Animation animation;
	private Vector2f position = new Vector2f(0.0f, 0.0f);
	private Vector2f scale = DEFAULT_SCALE;
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
		
		if(animation == null)
			throw new NullPointerException("Please specify an animation!");
		
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
		
		renderer.pushMatrix(spriteTransform);
		renderer.drawTexture(texture, frame);
		renderer.popMatrix();
	}
	
	public Texture getTexture()
	{
		return texture;
	}
	
	public void setTexture(Texture texture)
	{
		this.texture = texture;
	}

	public Animation getAnimation() 
	{
		return animation;
	}

	public void setAnimation(String animationName)
	{
		if(animation == null || !animationName.equals(animation.getName()))
		{
			animation = texture.getAnimation(animationName);
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

	public Vector2f getPosition() 
	{
		return position;
	}
	
	public void setPosition(float x, float y)
	{
		position.set(x, y);
	}
	
	public void setPosition(Vector2f position) 
	{
		this.position.set(position);
	}

	public Vector2f getScale() 
	{
		return scale;
	}
	
	public void setScale(float x, float y)
	{
		scale.set(x, y);
	}
	
	public void setScale(Vector2f scale) 
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