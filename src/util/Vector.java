package util;

/*
 * A vector or a podouble on the coordinate plane
 */
public class Vector
{
	public double x, y;
	
	/**
	 * Default constructor
	 */
	public Vector()
	{
		x = 0;
		y = 0;
	}
	
	/**
	 * Copy constructor
	 * @param p_copy
	 */
	public Vector(Vector p_copy)
	{
		x = p_copy.x;
		y = p_copy.y;
	}
	
	/**
	 * Constructor
	 * @param p_x x-coordinate
	 * @param p_y y-coordinate
	 */
	public Vector(double p_x, double p_y)
	{
		x = p_x;
		y = p_y;
	}
	
	/**
	 * Adds two vectors together
	 * @param p_vec the vector to add
	 * @return the sum of two vectors
	 */
	public Vector add(Vector p_vec)
	{
		return new Vector(x + p_vec.x, y + p_vec.y);
	}
	
	/**
	 * Multiples this vector by a scalar
	 * @param p_scalar scaling factor
	 * @return the scaled vector
	 */
	public Vector scale(double p_scalar)
	{
		return new Vector(x * p_scalar, y * p_scalar); 
	}
	
	public String toString()
	{
		//adjusted precision to two decimal places
		return String.format("(%.2f, %.2f", x, y);
	}
}
