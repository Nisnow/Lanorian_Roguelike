package util.math;

import java.nio.FloatBuffer;

/*
 * [m00, m01]
 * [m10, m11]
 */
public class Matrix2f
{
	public float m00, m01;
	public float m10, m11;
	
	/**
	 * Create a default 2x2 identity matrix
	 */
	public Matrix2f()
	{
		m00 = 1.0f; m01 = 0.0f;
		m10 = 0.0f; m11 = 1.0f;
	}
	
	/**
	 * Creates a 2x2 matrix with specified columns
	 * @param col1 Vector for column 1
	 * @param col2 Vector for column 2
	 */
	public Matrix2f(Vector2f col1, Vector2f col2)
	{
		m00 = col1.x; m01 = col2.x;
		m10 = col1.y; m11 = col2.y;
	}
	
	/**
	 * Adds this matrix to another
	 * @param p_mat the other matrix
	 * @return the sum of the two matrices
	 */
	public Matrix2f add(Matrix2f p_mat)
	{
		Matrix2f sum = new Matrix2f();
		
		sum.m00 = m00 + p_mat.m00; sum.m01 = m01 + p_mat.m01;
		sum.m10 = m10 + p_mat.m10; sum.m11 = m11 + p_mat.m11;
		
		return sum;
	}
	
	/**
	 * Subtracts this matrix from another
	 * @param p_vec the other matrix
	 * @return the difference between the two
	 */
	public Matrix2f subtract(Matrix2f p_vec)
	{
		return this.add(p_vec.negate());
	}
	
	/**
	 * Negates this matrix
	 * @return the negated matrix
	 */
	public Matrix2f negate()
	{
		return this.scale(-1.0f);
	}
	
	/**
	 * Scales this matrix by a scalar
	 * @param scalar
	 * @return the scaled matrix
	 */
	public Matrix2f scale(float scalar)
	{
		Matrix2f result = new Matrix2f();
		
		result.m00 = m00 * scalar; result.m01 = m01 * scalar;
		result.m10 = m10 * scalar; result.m11 = m11 * scalar;
		
		return result;
	}
	
	/**
	 * Multiplies this matrix with a vector2f
	 * @param p_vec the vector to multiply
	 * @return the resulting vector2f
	 */
	public Vector2f multiply(Vector2f p_vec)
	{
		float n_x = m00 * p_vec.x + m01 * p_vec.y;
		float n_y = m10 * p_vec.x + m11 * p_vec.y;
		
		return new Vector2f(n_x, n_y);
	}
	
	/**
	 * Multiplies this matrix2 with another matrix2
	 * @param p_mat the other matrix2
	 * @return the product of this matrix * p_mat
	 */
	public Matrix2f multiply(Matrix2f p_mat)
	{
        Matrix2f result = new Matrix2f();

        result.m00 = m00 * p_mat.m00 + m01 * p_mat.m10;
        result.m10 = m10 * p_mat.m00 + m11 * p_mat.m10;

        result.m01 = m00 * p_mat.m01 + m01 * p_mat.m11;
        result.m11 = m10 * p_mat.m01 + m11 * p_mat.m11;

        return result;
	}
	
	/**
	 * @return the transposed matrix
	 */
	public Matrix2f transpose()
	{
        Matrix2f result = new Matrix2f();

        result.m00 = m00;
        result.m10 = m01;

        result.m01 = m10;
        result.m11 = m11;

        return result;
	}
	
	/**
	 * Stores this matrix in a buffer
	 * @param p_buffer the buffer to store the matrix
	 */
	public void toBuffer(FloatBuffer p_buffer)
	{
		p_buffer.put(m00).put(m01);
		p_buffer.put(m10).put(m11);
		p_buffer.flip();
	}
}
