package util.math;

import java.nio.FloatBuffer;

/*
 * (x, y, z)
 */
public class Vector3f
{
	public float x;
	public float y;
	public float z;
	
	/**
	 * Creates a default vector 3 with all values set to 0
	 */
	public Vector3f()
	{
		x = 0.0f;
		y = 0.0f;
		z = 0.0f;
	}
	
	/**
	 * Copy constructor
	 * @param p_vec vector to copy
	 */
	public Vector3f(Vector3f p_vec)
	{
		x = p_vec.x;
		y = p_vec.y;
		z = p_vec.z;
	}
	
	/**
	 * Creates a vector with specified values
	 * @param p_x x-value
	 * @param p_y y-value
	 * @param p_z z-value
	 */
	public Vector3f(float p_x, float p_y, float p_z)
	{
		x = p_x;
		y = p_y;
		z = p_z;
	}
	
	/**
	 * Calculates the length (magnitude) of this vector
	 * @return the magnitude/length
	 */
	public float magnitude()
	{
		return (float) Math.sqrt(x*x + y*y + z*z);
	}
	
	/**
	 * Normalizes the vector
	 * @return the normalized vector
	 */
	public Vector3f normalize()
	{
		return scale(1.0f / magnitude());
	}
	
	/**
	 * Adds this vector with another one
	 * @param p_vec the other vector
	 * @return the sum of the two vectors
	 */
	public Vector3f add(Vector3f p_vec)
	{
		float n_x = x + p_vec.x;
		float n_y = y + p_vec.y;
		float n_z = z + p_vec.z;
		return new Vector3f(n_x, n_y, n_z);
	}
	
	/**
	 * Negates this vector
	 * @return the negated vector
	 */
	public Vector3f negate()
	{
		return scale(-1.0f);
	}
	
	/**
	 * Subtracts this vector with another one
	 * @param p_vec the other vector
	 * @return the difference between the two vectors
	 */
	public Vector3f subtract(Vector3f p_vec)
	{
		return add(p_vec.negate());
	}
	
	/**
	 * Scales this vector
	 * @param scalar the scalar
	 * @return the scaled vector
	 */
	public Vector3f scale(float scalar)
	{
		float n_x = x * scalar;
		float n_y = y * scalar;
		float n_z = z * scalar;
		return new Vector3f(n_x, n_y, n_z);
	}
	
	// TODO: dot product, cross product, lerp
	
	/**
	 * Stores this vector in a buffer
	 * @param p_buffer the buffer to store data in
	 */
	public void toBuffer(FloatBuffer p_buffer)
	{
		p_buffer.put(x).put(y).put(z);
		p_buffer.flip();
	}
}
