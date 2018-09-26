package graphics;

import java.util.Stack;

import org.joml.Matrix4f;

import graphics.graphicsUtil.VertexArray;
import util.IntRect;

public class Renderer 
{
	private final String DEFAULT_VERTEX = "src/resources/shaders/TestVert.glsl";
	private final String DEFAULT_FRAG	= "src/resources/shaders/TestFrag.glsl";
	
	private VertexArray data = new VertexArray();
	private Shader shader;
	private Texture currentTexture;
	
	private Matrix4f viewMatrix = new Matrix4f();
	private Matrix4f currentTransform = new Matrix4f();

	private boolean drawing = false;
	
	private Stack<Matrix4f> transformStack = new Stack<Matrix4f>();
	
	/*
	 * Initialize the renderer with the default shader
	 */
	public Renderer()
	{
		shader = new Shader(DEFAULT_VERTEX, DEFAULT_FRAG);
	}
	
	/**
	 * Set up vertex arrays, VAO, and VBO
	 * required for rendering
	 */
	public void init()
	{
		// TODO: fill in this stubby stub
	}
	
	public void pushMatrix(Matrix4f matrix)
	{
		// TODO: fill in this stubby stub
	}
	
	public void popMatrix()
	{
		// TODO: fill in this stubby stub
	}
	
	public void updateUniforms()
	{
		shader.setUniformMat4f("transform", currentTransform);
		// TODO: fill in this stubby stub some more
	}
	
	public Shader getShader()
	{
		return shader;
	}
	
	public void setShader(Shader shader, boolean needsUpdate)
	{
		// TODO: fill in this stubby stub
	}
	
	public void begin()
	{
		if(drawing)
			throw new IllegalStateException("Must not be drawing before calling begin()!");	
		drawing = true;
		shader.useProgram();
		currentTexture = null;
	}
	
	public void drawTexture(Texture texture, IntRect frame)
	{
		// TODO: fill in this stubby stub
	}
	
	public void flush()
	{
		// TODO: fill in this stubby stub
	}
	
	public void end()
	{
		drawing = false;
		// TODO: fill in this stubby stub some more
	}
	
	private void checkStatus()
	{
		// TODO: fill in this stubby stub
	}
	
	private void render()
	{
		// TODO: fill in this stubby stub
	}
}
