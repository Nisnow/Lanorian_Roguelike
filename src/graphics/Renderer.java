package graphics;

import java.util.Stack;

import org.joml.Matrix4f;
import org.joml.Vector4f;

import graphics.graphicsUtil.Vertex;
import graphics.graphicsUtil.VertexArray;
import util.IntRect;

public class Renderer 
{
	private final String DEFAULT_VERTEX = "src/resources/shaders/DefaultVert.glsl";
	private final String DEFAULT_FRAG	= "src/resources/shaders/DefaultFrag.glsl";
	
	private VertexArray data = new VertexArray();
	private Shader shader;
	private Texture currentTexture;

	private Matrix4f viewMatrix = new Matrix4f();
	private Matrix4f currentTransform = new Matrix4f();

	private boolean drawing = false;
	
	private Stack<Matrix4f> transformStack = new Stack<Matrix4f>();
	
	private int startIndex = 0;
	
	Color color = new Color();
	
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
	
	/**
	 * Set the window for this renderer to get window dimensions from
	 * Used to set the orthogonal matrix transformation
	 */
	public void setWindow(Window window)
	{
		viewMatrix.ortho2D(0, window.getWidth(), window.getHeight(), 0);
		shader.useProgram();
		shader.setUniformMat4f("view", viewMatrix);
	}
	
	public void setColor(Color color)
	{
		setColor(color.r, color.g, color.b, color.a);
	}

	public void setColor(float r, float g, float b, float a)
	{
		color.set(r, g, b, a);
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
	
	public Shader getShader()
	{
		return shader;
	}
	
	public void setShader(Shader shader, boolean needsUpdate)
	{
		// TODO: fill in this stubby stub
	}
	
	/*
	 * Begin the sprite batch. Must be called before any draw calls
	 */
	public void begin()
	{
		if(drawing)
			throw new IllegalStateException("Must not be drawing before calling begin()!");	
		drawing = true;
		shader.useProgram();
		currentTexture = null;
	}

	/**
	 * Adds more vertices for a texture to the vertex array objct
	 * @param texture the texture to draw
	 * @param frame the current animation frame to draw
	 */
	public void drawTexture(Texture texture, IntRect frame)
	{
		checkStatus(texture);
		
		float s = (float) frame.x / texture.getWidth();
		float t = (float) frame.y / texture.getHeight();
		float s1 = (float) (frame.x + frame.w) / texture.getWidth();
		float t1 = (float) (frame.y + frame.h) / texture.getHeight();

		Vertex[] verts = new Vertex[4];
		verts[0] = new Vertex().setPosition(0, 0, 0).setColor(1, 0, 0).setST(s, t);
		verts[1] = new Vertex().setPosition(0, frame.h, 0).setColor(0, 1, 0).setST(s, t1);
		verts[2] = new Vertex().setPosition(frame.w, frame.h, 0).setColor(0, 0, 1).setST(s1, t1);
		verts[3] = new Vertex().setPosition(frame.w, 0, 0).setColor(1, 1, 1).setST(s1, t);

		// Transform vertices
		for(int i = 0; i < 4; i++)
		{
			Vector4f vec = currentTransform.transform(new Vector4f(verts[i].position, 1.0f));
			verts[i].setPosition(vec.x, vec.y, vec.z);
			data.put(verts[i]);
		}
		createQuad();
	}
	
	/*
	 * Draw the previous texture
	 */
	public void flush()
	{
		if(startIndex > 0)
		{
			data.flip();
			render();
			startIndex = 0;
			data.reset();
		}
	}
	
	/*
	 * Draw all the vertices in the vertex array. Must be called after all
	 * draw calls
	 */
	public void end()
	{
		if(!drawing)
			throw new IllegalStateException("Must be drawing before calling end()!");
		drawing = false;
		flush();
	}
	
	/*
	 * Create indices for the given texture
	 */
	private void createQuad()
	{
		byte[] idx = new byte[6];
			
		// Triangle 1
		idx[0] = (byte) (startIndex);
		idx[1] = (byte) (startIndex + 1);
		idx[2] = (byte) (startIndex + 2);
		
		// Triangle 2
		idx[3] = (byte) (startIndex + 2);
		idx[4] = (byte) (startIndex + 3);
		idx[5] = (byte) (startIndex);
	
		data.put(idx);
		startIndex += 6;
	}
	
	/**
	 * Draw any previous textures before drawing the current one
	 * @param texture the current texture to be drawn
	 */
	private void checkStatus(Texture texture)
	{
		if(texture == null)
			throw new NullPointerException("Null texture");
		
		if(currentTexture != texture)
		{
			// apply last texture
			flush();
			currentTexture = texture;
		}
	}
		
	/*
	 * Actually draw the vertices in the vertex array
	 */
	private void render()
	{
		if(currentTexture != null)
			currentTexture.bind();
		data.bind();
		data.draw(startIndex);
		data.reset();
	}
	
	/*
	 * Delete the vertex array. Called when closing the program
	 */
	public void delete()
	{
		data.delete();
	}
}