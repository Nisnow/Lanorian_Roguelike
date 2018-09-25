package graphics;

import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL20.*;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

import graphics.graphicsUtil.VertexArray;
import util.FileLoader;

public class Shader 
{
	private int shaderProgram;
	private int vertexShader;
	private int fragmentShader;
	
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
		String vert = FileLoader.loadFile(vertPath);
		String frag = FileLoader.loadFile(fragPath);
		
		loadShader(vert, frag);
	}
	
	/**
	 * Load a shader from a file
	 * 
	 * @param vert the vertex shader converted into a string
	 * @param frag the fragment shader converted into a string
	 */
	private void loadShader(String vert, String frag)
	{
		// Compile the shaders
		vertexShader = compileShader(vert, GL_VERTEX_SHADER);
		fragmentShader = compileShader(frag, GL_FRAGMENT_SHADER);
		
		// Create a new shader program that links both shaders
		shaderProgram = glCreateProgram();
		glAttachShader(shaderProgram, vertexShader);
		glAttachShader(shaderProgram, fragmentShader);
		
		// Bind vertex attributes to shaders
		glBindAttribLocation(shaderProgram, VertexArray.POSITION_ATTRB, "in_Position");
		glBindAttribLocation(shaderProgram, VertexArray.COLOR_ATTRB, "in_Color");
		glBindAttribLocation(shaderProgram, VertexArray.ST_ATTRB, "in_TextureCoord");
		
		glLinkProgram(shaderProgram);
		glValidateProgram(shaderProgram);
	}
	
	/**
	 * Compile this shader
	 * 
	 * @param shaderSource the shader file converted into a string
	 * @param shaderType GL_VERTEX_SHADER or GL_FRAGMENT_SHADER
	 * @return the ID for this vertex or fragment shader
	 */
	private int compileShader(String shaderSource, int shaderType)
	{
		int shaderID = glCreateShader(shaderType);
		glShaderSource(shaderID, shaderSource);
		glCompileShader(shaderID);
		
		int status = glGetShaderi(shaderID, GL_COMPILE_STATUS);
		if (status != GL_TRUE) {
		    throw new RuntimeException(glGetShaderInfoLog(shaderID));
		}
		
		return shaderID;
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
		glDetachShader(shaderProgram, vertexShader);
		glDetachShader(shaderProgram, fragmentShader);
		
		glDeleteShader(vertexShader);
		glDeleteShader(fragmentShader);
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
		
		if (location < 0)
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

		if (location < 0)
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
