package graphics.graphicsUtil;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glDrawBuffer;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glReadBuffer;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;
import static org.lwjgl.opengl.GL30.GL_DRAW_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_COMPLETE;
import static org.lwjgl.opengl.GL30.GL_READ_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;
import static org.lwjgl.opengl.GL30.glBlitFramebuffer;
import static org.lwjgl.opengl.GL30.glCheckFramebufferStatus;
import static org.lwjgl.opengl.GL30.glDeleteFramebuffers;
import static org.lwjgl.opengl.GL30.glFramebufferTexture2D;
import static org.lwjgl.opengl.GL30.glGenFramebuffers;

import org.lwjgl.opengl.GL;

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
		fboTexture = new Texture(width, height);
		
		// Attach the texture to this frame buffer
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0,
				GL_TEXTURE_2D, fboTexture.getID(), 0);

		glDrawBuffer(GL_COLOR_ATTACHMENT0);
		
		// Check if the framebuffer is complete
		if(glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE)
		{
			glBindFramebuffer(GL_FRAMEBUFFER, 0);
			glDeleteTextures(fboTexture.getID());
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
	
	/*
	 * Start drawing to this framebuffer
	 * Must be called before starting the sprite batch
	 */
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
	
	/*
	 * Reset the frame buffer
	 * Must be called some time after begin() when finished
	 */
	public void end()
	{
		if(id == 0)
			throw new IllegalStateException("Can't reset FBO because it doesn't exist!");
		
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
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