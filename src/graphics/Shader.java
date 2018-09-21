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
	
	public Shader(String vertPath, String fragPath)
	{
		String vert = FileLoader.loadFile(vertPath);
		String frag = FileLoader.loadFile(fragPath);
		
		loadShader(vert, frag);
	}
	
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
	
	private int compileShader(String shaderSource, int shaderType)
	{
		int shaderObj = glCreateShader(shaderType);
		glShaderSource(shaderObj, shaderSource);
		glCompileShader(shaderObj);
		
		int status = glGetShaderi(shaderObj, GL_COMPILE_STATUS);
		if (status != GL_TRUE) {
		    throw new RuntimeException(glGetShaderInfoLog(shaderObj));
		}
		
		return shaderObj;
	}
	
	public void useProgram()
	{
		glUseProgram(shaderProgram);
	}
}
