package graphics;

import static org.lwjgl.opengl.GL20.*;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

import util.ResourceManager;

public class Shader 
{
	public static final String DEFAULT_VERTEX = "src/resources/shaders/DefaultVert.glsl";
	public static final String DEFAULT_FRAG	  = "src/resources/shaders/DefaultFrag.glsl";

	public static final String POST_PROCESS_VERTEX = "src/resources/shaders/PostProcessVert.glsl";
	public static final String POST_PROCESS_FRAG   = "src/resources/shaders/PostProcessFrag.glsl";

	private int shaderProgram;
	
	/*
	 * Default constructor
	 */
	public Shader() {}
	
	/**
	 * Constructor that compiles the shader file path arguments
	 * 
	 * @param vertPath the file name of the vertex shader
	 * @param fragPath the file name of the fragment shader
	 */
	public Shader(String vertPath, String fragPath)
	{
		shaderProgram = ResourceManager.loadShader(vertPath, fragPath);
	}
	/*
	 * Use this shader for rendering.
	 * Called in the rendering loop before any drawing
	 */
	public void useProgram()
	{
		glUseProgram(shaderProgram);
	}
	
	/*
	 * Reset the shader to the default. 
	 * Called at the end of the rendering loop.
	 */
	public void reset()
	{
		glUseProgram(0);
	}
	
	/*
	 * Clean up memory and delete this shader program
	 * and its shaders.
	 */
	public void delete()
	{
		this.reset();
	}
	
	/**
	 * Set an int uniform for this shader to use
	 * 
	 * @param name the name of the uniform (must match in shader)
	 * @param value the value of the int to pass through the shader
	 */
	public void setUniform1i(String name, int value)
	{
		int location = glGetUniformLocation(shaderProgram, name);
		
		if(location < 0)
		{
			System.out.println("Failed to get uniform integer");
			return;
		}
		
		this.useProgram();
		glUniform1i(location, value);
	}
	
	/**
	 * Set a Vector2f uniform for this shader to use
	 * 
	 * @param name the name of the uniform (must match in shader)
	 * @param value the Vector2f to pass through the shader
	 */
	public void setUniformVec2f(String name, Vector2f value)
	{
		int location = glGetUniformLocation(shaderProgram, name);
		
		if(location < 0)
		{
			System.out.println("Failed to get uniform vector 2f");
			return;
		}
		
		this.useProgram();
		glUniform2fv(location, value.get(BufferUtils.createFloatBuffer(2)));
	}
	
	/**
	 * Set a matrix4f uniform for this shader to use
	 * 
	 * @param name the name of the uniform (must match in the shader)
	 * @param value the Matrix4f to pass through the shader
	 */
	public void setUniformMat4f(String name, Matrix4f value)
	{
		int location = glGetUniformLocation(shaderProgram, name);

		if(location < 0)
		{
			System.out.println("Failed to get uniform Matrix4f");
			return;
		}
		
		this.useProgram();
		glUniformMatrix4fv(location, false, value.get(BufferUtils.createFloatBuffer(4*4)));
	}
	
	/**
	 * Set a vector4f uniform for this shader to use
	 * 
	 * @param name the name of the uniform (must match in the shader)
	 * @param value the vector4f to pass through the shader
	 */
	public void setUniformVec4f(String name, Vector4f value)
	{
		int location = glGetUniformLocation(shaderProgram, name);
		
		if (location < 0)
		{
			System.out.println("Failed to get uniform Vec4f");
			return;
		}
		
		this.useProgram();
		glUniform4fv(location, value.get(BufferUtils.createFloatBuffer(4)));
	}
}
