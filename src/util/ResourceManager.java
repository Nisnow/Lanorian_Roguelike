package util;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import graphics.graphicsUtil.VertexArray;

public class ResourceManager
{
	public static void loadTexture(String path)
	{
		// TODO: fill in this stubby stub
	}
	
	/**
	 * Load a shader from a file
	 * 
	 * @param vert the vertex shader file path
	 * @param frag the fragment shader file path
	 */
	public static int loadShader(String vertPath, String fragPath)
	{
		// Convert the GLSL files into strings
		String vert = loadFileAsString(vertPath);
		String frag = loadFileAsString(fragPath);
		
		// Create the vertex and fragment shaders
		int vertexShader = compileShader(vert, GL_VERTEX_SHADER);
		int fragmentShader = compileShader(frag, GL_FRAGMENT_SHADER);
		
		// Create a new shader program that links both shaders
		int shaderProgram = glCreateProgram();
		glAttachShader(shaderProgram, vertexShader);
		glAttachShader(shaderProgram, fragmentShader);
		
		// Bind vertex attributes to shaders
		glBindAttribLocation(shaderProgram, VertexArray.POSITION_ATTRB, "in_Position");
		glBindAttribLocation(shaderProgram, VertexArray.COLOR_ATTRB, "in_Color");
		glBindAttribLocation(shaderProgram, VertexArray.ST_ATTRB, "in_TextureCoord");
		
		// Link shader
		glLinkProgram(shaderProgram);
		glValidateProgram(shaderProgram);
	
		// Detatch the shaders now that they're linked
		glDetachShader(shaderProgram, vertexShader);
		glDetachShader(shaderProgram, fragmentShader);

		return shaderProgram;
	}

	/**
	 * Compile this shader
	 * 
	 * @param shaderSource the shader file converted into a string
	 * @param shaderType GL_VERTEX_SHADER or GL_FRAGMENT_SHADER
	 * @return the ID for this vertex or fragment shader
	 */
	private static int compileShader(String shaderSource, int shaderType)
	{
		int shaderID = glCreateShader(shaderType);
		glShaderSource(shaderID, shaderSource);
		glCompileShader(shaderID);
		
		int status = glGetShaderi(shaderID, GL_COMPILE_STATUS);
		if (status != GL_TRUE) 
		{
		    throw new RuntimeException(glGetShaderInfoLog(shaderID));
		}
		
		return shaderID;
	}
	
	/**
	 * Convert a file into a string
	 * 
	 * @param path the file path 
 	 * @return this file converted into a string
	 */
	public static String loadFileAsString(String path)
	{
		StringBuilder result = new StringBuilder();
		
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(path));
			String buffer = "";
			while ((buffer = reader.readLine()) != null)
			{
				result.append(buffer + '\n');
			}
			reader.close();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return result.toString();
	}
}
