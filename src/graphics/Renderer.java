package graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.glBlendFuncSeparate;

import org.joml.Matrix4f;

import graphics.graphicsUtil.Framebuffer;
import graphics.graphicsUtil.Vertex;

public class Renderer
{
	private Framebuffer fbo;
	private Shader currentShader = new Shader(Shader.DEFAULT_VERTEX, Shader.DEFAULT_FRAG);
 
	private Matrix4f viewMatrix;
	
	private RenderList renderList;
	private SpriteBatch batch;
	
	public Renderer(RenderList renderList)
	{
		batch = new SpriteBatch();
		this.renderList = renderList;
	}
	
	public void setFramebuffer(Framebuffer framebuffer)
	{
		this.fbo = framebuffer;
	}
	
	public Framebuffer getFramebuffer()
	{
		return this.fbo;
	}
	
	public void addVertices(Vertex[] vertices)
	{
		//for(Vertex v : vertices)
		//	data.putVert(v);
	}
	
	public void render()
	{
		if(fbo == null)
			throw new NullPointerException("Must set a framebuffer before rendering!");
		
		fbo.begin();

		glClearColor(0.0f, 0.2f, 0.2f, 1.0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		batch.resize(fbo.getWidth(), fbo.getHeight());
		
		glBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_ONE, GL_ONE);

		viewMatrix = new Matrix4f().ortho2D(0, fbo.getWidth(), fbo.getHeight(), 0);
		currentShader.useProgram();
		currentShader.setUniformMat4f("view", viewMatrix);
		
		batch.begin();
			renderList.draw(batch);
		// for(Batch b : batches)
			// renderBatch(b);
		batch.end();
		
		fbo.end();
		
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
	}
}
