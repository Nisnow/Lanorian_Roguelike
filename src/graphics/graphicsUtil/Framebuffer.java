package graphics.graphicsUtil;

import static org.lwjgl.opengl.EXTFramebufferObject.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

import org.lwjgl.opengl.GL;

import graphics.Window;
/*
 * Frame buffer for render-to-texture functionality
 * and post-processing effects
 */
public class Framebuffer
{
	private int id, textureId;
	private int width, height;
	
	private Window window;
	
	/*
	 * Create a frame buffer with a specified width and height
	 */
	public Framebuffer(int width, int height)
	{
		if(width <= 0 || height <= 0)
			throw new IllegalArgumentException("Width and height must be positive!");
		
		if(!GL.getCapabilities().GL_EXT_framebuffer_object)
			throw new IllegalStateException("FBO not supported with this hardware!");
			
		System.out.println("Init framebuffer");
		
		// Create the frame buffer
		id = glGenFramebuffers();
		glBindFramebuffer(GL_FRAMEBUFFER, id);

		// Create the texture to draw on
		textureId = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, textureId);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, 
				GL_RGB, GL_UNSIGNED_BYTE, 0);
	
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);

		// Attach the texture to this frame buffer
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0,
				GL_TEXTURE_2D, textureId, 0);

		glDrawBuffer(GL_COLOR_ATTACHMENT0);
		
		// Check if the framebuffer is complete
		if(glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE)
		{
			glBindFramebuffer(GL_FRAMEBUFFER, 0);
			glDeleteFramebuffers(id);
			throw new IllegalStateException("Incomplete frambuffer!");
		} 	
		
		// Draw to default frame buffer
		glBindFramebuffer(GL_DRAW_FRAMEBUFFER, 0);
		
		this.width = width;
		this.height = height;
	}

	public void setWindow(Window window)
	{
		this.window = window;
	}
	
	public void begin()
	{
		if(id == 0)
			throw new IllegalStateException("Can't use FBO because it doesn't exist!");
		
		// Read from this framebuffer
		glBindFramebuffer(GL_READ_FRAMEBUFFER, id);
		glReadBuffer(GL_COLOR_ATTACHMENT0);
		
		// Copy framebuffer data to default
		glBlitFramebuffer(0, 0, width, height, 0, 0, window.getWidth(), 
				window.getHeight(), GL_COLOR_BUFFER_BIT, GL_NEAREST);
	}
	
	public void end()
	{
		if(id == 0)
			return;
		glViewport(0, 0, window.getWidth(), window.getHeight());
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
	}
	
	public void resize(int width, int height)
	{
		if(width <= 0 || height <= 0)
			throw new IllegalArgumentException("Width and height of framebuffer must be positive!");
		
		glBindTexture(GL_TEXTURE_2D, textureId);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, 0);
		glBindTexture(GL_TEXTURE_2D, 0);
		
		this.width = width;
		this.height = height;
	}
	
	/**
	 * @return the texture id 
	 */
	public int getTextureId()
	{
		return textureId;
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
}