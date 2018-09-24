package graphics;

import util.FileLoader;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import graphics.graphicsUtil.VertexArray;

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
	 * Clean up memory and delete this shader program
	 * and its shaders.
	 */
	public void delete()
	{
		glUseProgram(0);
		glDetachShader(shaderProgram, vertexShader);
		glDetachShader(shaderProgram, fragmentShader);
		
		glDeleteShader(vertexShader);
		glDeleteShader(fragmentShader);
	}
}
