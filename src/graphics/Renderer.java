package graphics;

import java.util.Stack;

import org.joml.Matrix4f;

import graphics.graphicsUtil.Vertex;
import graphics.graphicsUtil.VertexArray;
import util.IntRect;

public class Renderer 
{
	private final String DEFAULT_VERTEX = "src/resources/shaders/TestVert.glsl";
	private final String DEFAULT_FRAG	= "src/resources/shaders/TestFrag.glsl";
	
	private VertexArray data = new VertexArray();
	private Shader shader;
	private Texture texture;

	private Window window;
	
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
		data.init();
		
		// Initialize renderer with identity matrix
		transformStack.push(new Matrix4f().identity());
	}
	
	public void setWindow(Window window)
	{
		this.window = window;
		viewMatrix.ortho2D(0, window.getWidth(), window.getHeight(), 0);
	}
	
	/**
	 * Adds a new matrix transform to the transformation stack
	 * @param matrix the matrix that will be multiplied by the previous matrix
	 */
	public void pushMatrix(Matrix4f matrix)
	{
		if(transformStack.isEmpty())
		{
			transformStack.push(new Matrix4f().identity());
		}
		else
		{
			Matrix4f prevTransform = new Matrix4f(transformStack.peek());
			prevTransform.mul(matrix);
			transformStack.push(prevTransform);
		}

		currentTransform = transformStack.peek();
		updateUniforms();
	}
	
	/**
	 * Reverts back to a previous coordinate system (matrix)
	 * Must be called sometime after every pushTransform() call
	 */
	public void popMatrix()
	{
		if(transformStack.size() > 1)
			transformStack.pop();
	
		currentTransform = transformStack.peek();
	}
	
	public void updateUniforms()
	{
		shader.useProgram();
		
		shader.setUniformMat4f("view", viewMatrix);
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
		texture = null;
	}
	
	public void drawTexture(Texture texture, IntRect frame)
	{
		this.texture = texture;
		
		float s = (float) frame.x / texture.getWidth();
		float t = (float) frame.y / texture.getHeight();
		float s1 = (float) (frame.x + frame.w) / texture.getWidth();
		float t1 = (float) (frame.y + frame.h) / texture.getHeight();

		data.put(new Vertex().setPosition(0, 0, 0).setColor(1, 0, 0).setST(s, t));
        data.put(new Vertex().setPosition(0, frame.h, 0).setColor(0, 1, 0).setST(s, t1));
        data.put(new Vertex().setPosition(frame.w, frame.h, 0).setColor(0, 0, 1).setST(s1, t1));
        data.put(new Vertex().setPosition(frame.w, 0, 0).setColor(1, 1, 1).setST(s1, t));
	}
	
	public void end()
	{
		if(!drawing)
			throw new IllegalStateException("Must be drawing before calling end()!");
		drawing = false;
		data.flip();
		render();
	}
	
	private void checkStatus()
	{
		// TODO: fill in this stubby stub
	}
	
	private void render()
	{
		if(texture != null)
			texture.bind();
		data.bind();
		data.draw();
		data.reset();
		shader.reset();
	}
	
	public void delete()
	{
		data.delete();
	}
}
