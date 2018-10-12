package graphics.graphicsUtil;

import static org.lwjgl.opengl.EXTFramebufferObject.GL_FRAMEBUFFER_EXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glBindFramebufferEXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glCheckFramebufferStatusEXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glFramebufferTexture2DEXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glGenFramebuffersEXT;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDrawBuffer;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_COMPLETE;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;
import static org.lwjgl.opengl.GL30.glDeleteFramebuffers;

import org.lwjgl.opengl.GL;

import graphics.Texture;
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
			
		// Create the frame buffer
		id = glGenFramebuffersEXT();
		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, id);

		// Create the texture to draw on
		textureId = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, textureId);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, 
				GL_RGB, GL_UNSIGNED_BYTE, 0);
	
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);

		// Attach the texture to this frame buffer
		glFramebufferTexture2DEXT(GL_FRAMEBUFFER_EXT, GL_COLOR_ATTACHMENT0,
				GL_TEXTURE_2D, textureId, 0);

		// Check if the framebuffer is complete
		if(glCheckFramebufferStatusEXT(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE)
		{
			glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
			glDeleteFramebuffers(id);
			throw new IllegalStateException("Incomplete frambuffer!");
		} 	
		
		// Reset everything now that they're bound
		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
		
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
		glViewport(0, 0, width, height);
		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, id);
	}
	
	public void end()
	{
		if(id == 0)
			return;
		glViewport(0, 0, window.getWidth(), window.getHeight());
		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
	}
	
	public void resize(int width, int height)
	{
		// TODO: fill in this stubby stub
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