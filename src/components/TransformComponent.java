package components;

import java.util.ArrayList;

import org.joml.Matrix4f;

import com.sun.javafx.geom.Vec2f;

public class TransformComponent 
{
	// Parent transformations, if this component has
	private Vec2f parentPosition = new Vec2f(0.0f, 0.0f);
	private float parentRotation = 0.0f; // in radians
	private Vec2f parentScale = new Vec2f(1.0f, 1.0f);

	// local matrix properties relative to the parent
	private Vec2f position = new Vec2f(0.0f, 0.0f);
	private float rotation = 0.0f; // in radians
	private Vec2f scale = new Vec2f(1.0f, 1.0f);
	
	private ArrayList<TransformComponent> children = new ArrayList<TransformComponent>(); // long boi

	private boolean needsUpdate = false;
	private boolean parentNeedsUpdate = false;
	
	private Matrix4f parentTransform = new Matrix4f();
	private Matrix4f transform = new Matrix4f();
	
	public TransformComponent()
	{
		this.transform.identity();
	}
	
	public TransformComponent(Matrix4f transform)
	{
		this.transform = transform;
	}
	
	public void addChild(TransformComponent tc)
	{
		children.add(tc);
	}
	
	public void updateParent()
	{
		parentTransform = transform;
	}
	
	public void render(TransformComponent parent)
	{
		updateLocal();
		Matrix4f world = transform.mul(parent.getTransform());
	
		for(TransformComponent child : children)
		{
			child.setParentTransform(world);
			child.render(new TransformComponent(world));
		}
	}
	
	public void updateLocal()
	{
		if(needsUpdate)
		{
			transform.identity();
			transform.translate(position.x, position.y, 0.0f);
			transform.rotate(rotation, 0.0f, 0.0f, 0.0f);
			transform.scale(scale.x, scale.y, 1.0f);
			needsUpdate = false;
		}
	}
	
	public Matrix4f getTransform()
	{
		return transform;
	}
	
	public void setTransform(Matrix4f transform)
	{
		this.transform = transform; 
		needsUpdate = true;
	}
	
	public void setParentTransform(Matrix4f transform)
	{
		parentTransform = transform;
		// TODO: other necessary things
	}

	public Matrix4f getParentTransform()
	{
		return parentTransform;
	}
	
	public void setPosition(Vec2f position) 
	{
		this.position = position;
		needsUpdate = true;
	}
	
	public void setPosition(float x, float y)
	{
		this.position = new Vec2f(x, y);
		needsUpdate = true;
	}

	public void setScale(Vec2f scale)
	{
		this.scale = scale;
		needsUpdate = true;
	}
	
	public void setScale(float x, float y)
	{
		this.scale = new Vec2f(x, y);
		needsUpdate = true;
	}

	public void setRotation(float rotation) 
	{
		this.rotation = rotation;
		needsUpdate = true;
	}

	public Vec2f getPosition()
	{
		return position;
	}
	
	public Vec2f getParentPosition()
	{
		return parentPosition;
	}

	public Vec2f getScale() 
	{
		return scale;
	}
	
	public Vec2f getParentScale()
	{
		return parentScale;
	}

	public float getRotation() 
	{
		return rotation;
	}
	
	public float getParentRotation()
	{
		return parentRotation;
	}
	
	public void onTransformChanged()
	{
		parentNeedsUpdate = true;
	}
	
	public void reset()
	{
		children.clear();
	}
}
