package engine.components;

import org.joml.Vector4f;

import engine.graphics.Animation;
import engine.graphics.Renderer;
import engine.graphics.Texture;
import engine.graphics.graphicsUtil.Vertex;
import engine.util.Clock;
import engine.util.IntRect;

public class GraphicsComponent implements Component
{
	private TransformComponent transform;
	
	private Texture texture;
	private Animation currentAnimation;
	private Clock clock = new Clock();
	
	public GraphicsComponent() {}
	
	public GraphicsComponent(Texture texture)
	{
		this.texture = texture;
	}
	
	public GraphicsComponent(Texture texture, String animation)
	{
		this.texture = texture;
		setAnimation(animation);
	}
	
	/*
	 * Create a batch for the renderer to use
	 */
	public void render(Renderer renderer)
	{
		if(this.texture == null)
			throw new NullPointerException("Must have a texture to draw!");
		
		if(this.transform == null)
			throw new NullPointerException("Must have a transform component to draw!");
		
		IntRect frame = getCurrentFrame();
		
		// Get the texture coordinates of the current frame
		float s = (float) frame.x / texture.getWidth();
		float t = (float) frame.y / texture.getHeight();
		float s1 = (float) (frame.x + frame.w) / texture.getWidth();
		float t1 = (float) (frame.y + frame.h) / texture.getHeight();

		Vertex[] verts = new Vertex[4];
		verts[0] = new Vertex().setPosition(0, 0, 0).setColor(1, 0, 0, 0).setST(s, t);
		verts[1] = new Vertex().setPosition(0, frame.h, 0).setColor(0, 1, 0, 0).setST(s, t1);
		verts[2] = new Vertex().setPosition(frame.w, frame.h, 0).setColor(0, 0, 1, 0).setST(s1, t1);
		verts[3] = new Vertex().setPosition(frame.w, 0, 0).setColor(1, 1, 1, 1).setST(s1, t);

		// Transform the vertices to the current transformation
		for(int i = 0; i < 4; i++)
		{
			Vector4f vec = transform.getParentTransform().transform(new Vector4f(verts[i].position, 1.0f));
			verts[i].setPosition(vec.x, vec.y, vec.z);
		}
		
		// Prepare a batch to send to the renderer
		Renderer.Batch batch = renderer.new Batch();
		batch.setTexture(texture);
		batch.addVertices(verts);
		batch.addQuad();
		
		renderer.getBatches().add(batch);
	}
	
	public void setTransformComponent(TransformComponent transform)
	{
		this.transform = transform;
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
		return currentAnimation;
	}

	public void setAnimation(String animationName)
	{
		if(currentAnimation == null || !animationName.equals(currentAnimation.getName()))
		{
			currentAnimation = texture.getAnimation(animationName);
			clock.restart();
		}
	}
	
	public void setAnimation(Animation animation)
	{
		if(currentAnimation == null || currentAnimation != animation)
		{
			currentAnimation = animation;
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
	
	private IntRect getCurrentFrame()
	{
		IntRect frame;
		
		if(currentAnimation  == null)
			throw new NullPointerException("Please specify an animation!");
		
		if(currentAnimation.getFrameCount() > 1 
				&& currentAnimation.getInterval() > 0)
		{
			// Display current frame of an animation
			int frameIdx = (int) (clock.getElapse() / currentAnimation.getInterval());
			frame = currentAnimation.getFrame(frameIdx);
		}
		else
		{
			// Unmoving image
			frame = currentAnimation.getFrame();
		}
		
		return frame;
	}
}
