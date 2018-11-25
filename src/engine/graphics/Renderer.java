package engine.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.glBlendFuncSeparate;

import java.util.ArrayList;

import org.joml.Matrix4f;

import engine.graphics.graphicsUtil.Framebuffer;
import engine.graphics.graphicsUtil.Vertex;
import engine.graphics.graphicsUtil.VertexArray;
import engine.util.IntRect;

public class Renderer
{
	private Framebuffer fbo;
	private Shader currentShader = Shader.DEFAULT;
 
	private Matrix4f viewMatrix;

	private ArrayList<Batch> batches = new ArrayList<Batch>();
	
	private VertexArray data;
	
	/*
	 * Represents something to be drawn to the screen
	 * This class holds the entity's vertices to be added
	 * to the Renderer's vertex array
	 */
	public class Batch
	{
		public Texture texture;
		public Shader shader;
		public Vertex[] vertices;
		public byte[] indices = new byte[6];
		
		/*
		 * Set the texture to be drawn in this batch if it has
		 */
		public void setTexture(Texture texture)
		{
			this.texture = texture;
		}
		
		public void setShader(Shader shader)
		{
			this.shader = shader;
		}

		/*
		 * Add indices to the element buffer for more efficient rendering
		 */
		public void addQuad()
		{
			byte startIdx = (byte) data.getIndexBuffer().position();
			
			// Triangle 1
			indices[0] = (byte) (startIdx);
			indices[1] = (byte) (startIdx + 1);
			indices[2] = (byte) (startIdx + 2);
			
			// Triangle 2
			indices[3] = (byte) (startIdx + 2);
			indices[4] = (byte) (startIdx + 3);
			indices[5] = (byte) (startIdx);
		}
		
		/**
		 * Add all the necessary vertices to render this object
		 * 
		 * @param vertices an array of vertices to be drawn
		 */
		public void addVertices(Vertex[] vertices)
		{
			this.vertices = vertices;
		}
		
		/*
		 * Clear the vertex array and unbind this batch's texture
		 * (if it has) for the next batch to be drawn
		 */
		public void reset()
		{
			if(texture != null)
				texture.unbind();
			
			data.reset();
		}
	}
	
	public Renderer()
	{
		data = new VertexArray();
		data.init();
	}
	
	/**
	 * Framebuffer must be set before drawing anything!
	 * 
	 * @param framebuffer the framebuffer for this Renderer
	 * to draw to
	 */
	public void setFramebuffer(Framebuffer framebuffer)
	{
		this.fbo = framebuffer;
	}
	
	public Framebuffer getFramebuffer()
	{
		return this.fbo;
	}
	
	public ArrayList<Batch> getBatches()
	{
		return batches;
	}
	
	/*
	 * Draw one frame
	 * Called every frame in the game loop
	 */
	public void render()
	{
		if(fbo == null)
			throw new NullPointerException("Must set a framebuffer before rendering!");
		
		fbo.begin();

		glClearColor(0.0f, 0.2f, 0.2f, 1.0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glViewport(0, 0, fbo.getWidth(), fbo.getHeight());
		
		glBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_ONE, GL_ONE);

		viewMatrix = new Matrix4f().ortho2D(0, fbo.getWidth(), fbo.getHeight(), 0);
		
		for(Batch b : batches)
		{
			b.shader.useProgram();
			b.shader.setUniformMat4f("view", viewMatrix);
			renderBatch(b);
		}
		
		fbo.end();
		
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		batches.clear();
	}

	private void renderBatch(Batch batch)
	{
		for(Vertex v : batch.vertices)
			data.putVert(v);
		
		data.putIdx(batch.indices);
		
		data.flip();
		
		if(batch.texture != null)
		{
			batch.texture.bind();
			batch.shader.setUniform1i("texture_diffuse", 0);
		}
		
		data.bind();
		data.draw(batch.indices.length);
		batch.reset();
	}
}
