package graphics.graphicsUtil;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Vertex 
{
    // Vertex data
    public Vector3f position = new Vector3f(0.0f, 0.0f, 0.0f);
    public Vector4f color	 = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
    public Vector2f st 		 = new Vector2f(0.0f, 0.0f);
    
    /*
     * Default constructor
     */
    public Vertex() {}
    
    /**
     * Copy constructor
     * @param copy the vertex to copy
     */
    public Vertex(Vertex copy)
    {
    	position = copy.getPosition();
    	color 	 = copy.getColor();
    	st 		 = copy.getST();
    }
    
    /**
     * Set the position (0.0f--1.0f) of this vertex
     * @param x the x-value
     * @param y the y-value
     * @param z the z-value
     * @return this vertex for further editing
     */
    public Vertex setPosition(float x, float y, float z)
    {
    	position.x = x;
    	position.y = y;
    	position.z = z;
    	return this;
    }
    
    /**
     * @return a copy of this vertex's position array
     */
    public Vector3f getPosition()
    {
    	return new Vector3f(position);
    }
     
    /**
     * Set the RGB color of this vertex
     * @param r the red value
     * @param g the green value
     * @param b the blue value
     * @return this vertex for further editing
     */
    public Vertex setColor(float r, float g, float b)
    {
    	color.x = r;
    	color.y = g;
    	color.z = b;
        return this;
    }
    
    /**
     * @return a copy of this vertex's color array
     */
    public Vector4f getColor()
    {
    	return new Vector4f(color);
    }
     
    /**
     * Set the ST texture coordinates of this vertex
     * @param s the s-value
     * @param t the t-value
     * @return this vertex for further editing
     */
    public Vertex setST(float s, float t)
    {
    	st.x = s;
    	st.y = t;
    	return this;
    }
    
    /**
     * @return a copy of this vertex's ST array
     */
    public Vector2f getST()
    {
    	return new Vector2f(st);
    }

    /**
     * Set the RGB color of this vertex
     * @param r the red value
     * @param g the green value
     * @param b the blue value
     * @param a the alpha value
     * @return this vertex for further editing
     */
    public Vertex setColor(float r, float g, float b, float a)
    {
    	color.x = r;
    	color.y = g;
    	color.z = b;
    	color.w = a;
        return this;
    }
}