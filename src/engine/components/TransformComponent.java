package engine.components;

import java.util.ArrayList;

import org.joml.Matrix4f;

import com.sun.javafx.geom.Vec2f;

public class TransformComponent implements Component
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
	
	private Matrix4f parentTransform = new Matrix4f();
	private Matrix4f transform = new Matrix4f();
	
	/**
	 * Initialize a TransformComponent with the identity matrix
	 * set as its local and parent transforms
	 */
	public TransformComponent() {}
	
	public TransformComponent(Matrix4f transform)
	{
		this.transform = transform;
	}
	
	/*
	 * Add a transformation node relative to this one
	 */
	public void addChild(TransformComponent tc)
	{
		children.add(tc);
	}
	
	public void removeChild(TransformComponent tc)
	{
		children.remove(tc);
	}
	
	/*
	 * This transformation will be at the top of the
	 * transformation tree. All of its children transformation
	 * will be relative to this
	 */
	public void setAsParent()
	{
		parentTransform = transform;
	}
	
	/**
	 * Render all the transformations relative to this one (i.e.
	 * all the transformations stored in the children ArrayList)
	 * 
	 * @param parent the transformation this one is relative to. 
	 * If this transformation is the parent, its parent is the identity
	 * matrix
	 * @param needsUpdate the dirty flag to update only when 
	 * this transformation has been updated
	 */
	public void render(TransformComponent parent, boolean needsUpdate)
	{
		updateLocal();
		Matrix4f world = transform;
		
		if(needsUpdate)
		{
			world = transform.mul(parent.getTransform());
			needsUpdate = false;
		}
		
		// Render all of the transformations relative to this one
		for(TransformComponent child : children)
		{
			child.setParentTransform(world);
			child.render(new TransformComponent(world), needsUpdate);
		}
	}
	
	public void updateLocal()
	{
		if(needsUpdate)
		{
			transform.identity();
			transform.translate(position.x, position.y, 0.0f);
			transform.rotate(rotation, 0.0f, 0.0f, 1.0f);
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
	
	public void setScale(float scale)
	{
		this.scale = new Vec2f(scale, scale);
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
	
	public void reset()
	{
		children.clear();
	}
}
