package engine.graphics.graphicsUtil;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL;

import engine.graphics.Shader;
import engine.graphics.Texture;
import engine.graphics.Window;

/*
 * Frame buffer for render-to-texture functionality
 * and post-processing effects
 */
public class Framebuffer
{
	private int id;
	private Texture fboTexture;
	private VertexArray va;
	private int width, height;
	
	private Vertex[] vertices = new Vertex[4];
	
	public Shader postProcessor = Shader.POST_PROCESS_DEFAULT;

	/*
	 * Create a frame buffer with a specified width and height
	 */
	public Framebuffer(int width, int height)
	{
		if(width <= 0 || height <= 0)
			throw new IllegalArgumentException("Width and height must be positive!");
		
		if(!GL.getCapabilities().GL_EXT_framebuffer_object)
			throw new IllegalStateException("FBO not supported with this hardware!");
		
		// Create the vertex array to hold the texture data
		va = new VertexArray();
		va.init();
		
		// Create the frame buffer
		id = glGenFramebuffers();
		glBindFramebuffer(GL_FRAMEBUFFER, id);

		// Create the texture to draw on
		fboTexture = new Texture(width, height);
		
		// Attach the texture to this frame buffer
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0,
				GL_TEXTURE_2D, fboTexture.getID(), 0);
		
		// Check if the framebuffer is complete
		if(glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE)
		{
			glBindFramebuffer(GL_FRAMEBUFFER, 0);
			glDeleteTextures(fboTexture.getID());
			glDeleteFramebuffers(id);
			throw new IllegalStateException("Incomplete frambuffer!");
		} 	
		
		this.width = width;
		this.height = height;
	}

	// TODO: put this somewhere else?
	public void createVAO()
	{
        vertices[0] = new Vertex().setPosition(-1.0f,  1.0f, 0.0f).setST(0.0f, 1.0f); // Top left
        vertices[1] = new Vertex().setPosition(-1.0f, -1.0f, 0.0f).setST(0.0f, 0.0f); // Bottom left
        vertices[2] = new Vertex().setPosition(1.0f, -1.0f, 0.0f).setST(1.0f, 0.0f); // Bottom right
        vertices[3] = new Vertex().setPosition(1.0f,  1.0f, 0.0f).setST(1.0f, 1.0f); // Top right
		
        byte indices[] = {0, 1, 2, 2, 3, 0};
        va.putIdx(indices);
        for(Vertex v : vertices)
        	va.putVert(v);
	}
	
	// for resizing purposes
	public void calculateNewVertexPositions(float originalWidth, float originalHeight,
											float newWidth, float newHeight)
	{
		float arOrigin = (float) originalWidth / (float) originalHeight;
		float arNew = (float) width / (float) height;
		
		float scaleW = (float) newWidth / (float) originalWidth;
		float scaleH = (float) newHeight / (float) originalHeight;
		
		if(arNew > arOrigin)
			scaleW = scaleH;
		else
			scaleH = scaleW;
			
		float marginX = (newWidth - originalWidth * scaleW) / 2;
		float marginY = (newHeight - originalHeight * scaleH) / 2;
		
		glViewport((int) marginX, (int) marginY, (int) (originalWidth * scaleW), (int) (originalHeight * scaleH));
		//glOrtho(0.0, (double) originalWidth / arOrigin, 0.0, (double) originalHeight / arOrigin, 0.0, 1.0);
	}
	
	/*
	 * Start drawing to this framebuffer
	 * Must be called before starting the sprite batch
	 */
	public void begin()
	{
		if(id == 0)
			throw new IllegalStateException("Can't use FBO because it doesn't exist!");
		
		glBindFramebuffer(GL_FRAMEBUFFER, id); // Make sure your multisampled FBO is the read framebuffer
		createVAO();
	}
	
	/*
	 * Reset the frame buffer
	 * Must be called some time after begin() when finished
	 */
	public void end()
	{
		if(id == 0)
			throw new IllegalStateException("Can't reset FBO because it doesn't exist!");
		
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
		drawFbo();
	}
	
	public void drawFbo()
	{
		glClear(GL_COLOR_BUFFER_BIT);
		postProcessor.useProgram();
		va.flip();
		fboTexture.bind();
		postProcessor.setUniform1i("texture_diffuse", 0);
		va.bind();
		va.draw(6);
		va.reset();
	}
	
	// TODO: Resize FB according to the correct ratio
	public void resize(int width, int height)
	{
		if(width <= 0 || height <= 0)
			throw new IllegalArgumentException("Width and height of framebuffer must be positive!");
		
		calculateNewVertexPositions(800, 600, width, height);
		glBindTexture(GL_TEXTURE_2D, fboTexture.getID());
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, this.width, this.height, 0, GL_RGB, GL_UNSIGNED_BYTE, 0);
		glBindTexture(GL_TEXTURE_2D, 0);
		
		this.width = width;
		this.height = height;
	}
	
	public Texture getFboTexture()
	{
		return fboTexture;
	}
	
	/**
	 * @return the width of the framebuffer in pixels
	 */
	public int getWidth()
	{
		return width;
	}
	
	/**
	 * @return the height of the framebuffer in pixels
	 */
	public int getHeight()
	{
		return height;
	}
	
	/*
	 * Dispose of this framebuffer once the user closes
	 * the program
	 */
	public void delete()
	{
		if(id == 0)
			return;
		
		fboTexture.delete();
		glDeleteFramebuffers(id);
	}
}