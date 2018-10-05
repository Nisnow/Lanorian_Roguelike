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
		texture = null;
	}
	
	public void drawTexture(Texture texture, IntRect frame)
	{
		this.texture = texture;
		
		// temp
		data.put(new Vertex().setPosition(-0.5f, 0.5f, 0).setColor(1, 0, 0).setST(0, 0));
        data.put(new Vertex().setPosition(-0.5f, -0.5f, 0).setColor(0, 1, 0).setST(0, 1));
        data.put(new Vertex().setPosition(0.5f, -0.5f, 0).setColor(0, 0, 1).setST(1, 1));
        data.put(new Vertex().setPosition(0.5f, 0.5f, 0).setColor(1, 1, 1).setST(1, 0));
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
	}
	
	public void delete()
	{
		data.delete();
	}
}
