package util.math;

import java.nio.FloatBuffer;

/*
 * <x, y>
 */
public class Vector2f
{
	public float x;
	public float y;
	
	/**
	 * Default vector with all values set to 0
	 */
	public Vector2f()
	{
		x = 0.0f;
		y = 0.0f;
	}
	
	/**
	 * Copy constructor
	 * @param copy the vector to duplicate
	 */
	public Vector2f(Vector2f copy)
	{
		x = copy.x;
		y = copy.y;
	}
	
	/**
	 * Create a vector with specified values
	 * @param p_x x-value
	 * @param p_y y-value
	 */
	public Vector2f(float p_x, float p_y)
	{
		x = p_x;
		y = p_y;
	}
	
	/**
	 * Calculates the magnitude of this vector
	 */
	public float magnitude()
	{
		return (float) Math.sqrt(x * x + y * y);
	}
	
	/**
	 * @return the normal (perpendicular) vector to this one
	 */
	public Vector2f normalize()
	{
		float length = magnitude();
		return scale(1.0f / length);
	}
	
	/**
	 * Adds this vector to another
	 * @param p_vec vector to add
	 * @return the sum of the two vectors
	 */
	public Vector2f add(Vector2f p_vec)
	{
		return new Vector2f(x + p_vec.x, y + p_vec.y);
	}
	
	/**
	 * Subtracts this vector with another
	 * @param p_vec the other vector
	 * @return the difference of the two vectors
	 */
	public Vector2f subtract(Vector2f p_vec)
	{
		return this.add(p_vec.negate());
	}
	
	/**
	 * Negates this vector
	 * @return the negated vector
	 */
	public Vector2f negate()
	{
		return scale(-1.0f);
	}
	
	/**
	 * Multiplies this vector by a scalar
	 * @param scalar the scalar to multiply
	 * @return the scaled vector
	 */
	public Vector2f scale(float scalar)
	{
		return new Vector2f(x * scalar, y * scalar);
	}
	
	/**
	 * Calculates the dot product of this vector with another
	 * @param p_vec the other vector
	 * @return the dot product of the two vectors
	 */
	public float dot(Vector2f p_vec)
	{
		return x * p_vec.x + y * p_vec.y;
	}
	
	/**
	 * Calculates the linear interpolation (curve-fitting)
	 * between this vector and another
	 * @param p_vec the other vector
	 * @param alpha must be between 0.0 and 1.0
	 * @return the linear interpolated vector (line between two vecs)
	 */
	public Vector2f lerp(Vector2f p_vec, float alpha)
	{
		return scale(1.0f - alpha).add(p_vec.scale(alpha));
	}
	
	/**
	 * Stores this vector in a buffer
	 * @param p_buffer the buffer to store vector data in
	 */
	public void toBuffer(FloatBuffer p_buffer)
	{
		p_buffer.put(x).put(y);
		p_buffer.flip();
	}
}
