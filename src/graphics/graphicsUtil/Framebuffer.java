package graphics.graphicsUtil;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

import org.lwjgl.opengl.GL;

import graphics.Shader;
import graphics.Texture;
import graphics.Window;

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
	
	private Window window;
	
	public static final String POST_PROCESS_VERTEX = "src/resources/shaders/PostProcessVert.glsl";
	public static final String POST_PROCESS_FRAG 	 = "src/resources/shaders/PostProcessFrag.glsl";
	public Shader postProcessor = new Shader(POST_PROCESS_VERTEX, POST_PROCESS_FRAG);

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

	public void setWindow(Window window)
	{
		this.window = window;
	}
	
	// TODO: put this somewhere else
	public void createVAO()
	{
        va.putVert(new Vertex().setPosition(-1.0f,  1.0f, 0.0f).setST(0.0f, 1.0f));
        va.putVert(new Vertex().setPosition(-1.0f, -1.0f, 0.0f).setST(0.0f, 0.0f));
        va.putVert(new Vertex().setPosition(1.0f, -1.0f, 0.0f).setST(1.0f, 0.0f));
        va.putVert(new Vertex().setPosition(1.0f,  1.0f, 0.0f).setST(1.0f, 1.0f));
		
        byte indices[] = {0, 1, 2, 2, 3, 0};
        va.putIdx(indices);
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
	
	public void resize(int width, int height)
	{
		if(width <= 0 || height <= 0)
			throw new IllegalArgumentException("Width and height of framebuffer must be positive!");
		
		glBindTexture(GL_TEXTURE_2D, fboTexture.getID());
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, 0);
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