package components;

import org.joml.Matrix4f;

import com.sun.javafx.geom.Vec2f;

public class TransformComponent 
{
	private Matrix4f currentTransform;
	
	private Vec2f position;
	private Vec2f scale = new Vec2f(1.0f, 1.0f);
	private float rotation = 0.0f; // in radians
	
	public TransformComponent() {}
	
	public TransformComponent(Matrix4f transform)
	{
		currentTransform = transform;
	}
	
	public void update()
	{
		
	}

	public Matrix4f getCurrentTransform()
	{
		return currentTransform;
	}

	public Vec2f getPosition()
	{
		return position;
	}

	public void setPosition(Vec2f position)
	{
		this.position = position;
	}
	
	public void setPosition(float x, float y)
	{
		this.position = new Vec2f(x, y);
	}
	
	public Vec2f getScale()
	{
		return scale;
	}

	public float getRadians() 
	{
		return rotation;
	}

	public void setScale(Vec2f scale)
	{
		this.scale = scale;
	}
	
	public void setScale(float x, float y)
	{
		this.scale = new Vec2f(x, y);
	}

	public void setRadians(float radians)
	{
		this.rotation = radians;
	}
}
