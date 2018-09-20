package graphics.graphicsUtil;

import java.util.Arrays;

public class Vertex 
{
    // Vertex data
    private float[] position = new float[] {0.0f, 0.0f, 0.0f, 1.0f};
    private float[] color 	 = new float[] {1.0f, 1.0f, 1.0f, 1.0f};
    private float[] st 		 = new float[] {0.0f, 0.0f};
    
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
    	color = copy.getColor();
    	st = copy.getST();
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
    	position[0] = x;
    	position[1] = y;
    	position[2] = z;
    	return this;
    }
    
    /**
     * @return a copy of this vertex's position array
     */
    public float[] getPosition()
    {
    	return Arrays.copyOf(position, position.length);
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
    	color[0] = r;
    	color[1] = g;
    	color[2] = b;
        return this;
    }
    
    /**
     * @return a copy of this vertex's color array
     */
    public float[] getColor()
    {
    	return Arrays.copyOf(color, color.length);
    }
     
    /**
     * Set the ST texture coordinates of this vertex
     * @param s the s-value
     * @param t the t-value
     * @return this vertex for further editing
     */
    public Vertex setST(float s, float t)
    {
    	st[0] = s;
    	st[1] = t;
    	return this;
    }
    
    /**
     * @return a copy of this vertex's ST array
     */
    public float[] getST()
    {
    	return Arrays.copyOf(st, st.length);
    }

    /**
     * Set the position (0.0f--1.0f) of this vertex
     * @param x the x-value
     * @param y the y-value
     * @param z the z-value
     * @param w the w-value
     * @return this vertex for further editing
     */
    public Vertex setPosition(float x, float y, float z, float w)
    {
    	position[0] = x;
    	position[1] = y;
    	position[2] = z;
    	position[3] = w;
    	return this;
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
    	color[0] = r;
    	color[1] = g;
    	color[2] = b;
        return this;
    }
}