package engine.graphics;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import engine.graphics.graphicsUtil.Framebuffer;

public class Window
{
	private long window;
	private Framebuffer framebuffer;
	private GLFWWindowSizeCallback windowSizeCallback;

	// TODO: Move this eventually to some "Game" class
	// TODO: Key callback
	public void init(int width, int height, String title)
	{
		System.out.println("Initializing window...");
	
		glfwInit();

		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);

		window = glfwCreateWindow(width, height, title, NULL, NULL);

		// TODO: center window and have constant aspect ratio
		// (see Code Fight for aspec ratio thingies)

		// Use this window for rendering
		glfwMakeContextCurrent(window);
		GL.createCapabilities();

		// Enable alpha blending for transparency
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		glfwSetWindowSizeCallback(window, windowSizeCallback = new GLFWWindowSizeCallback()
		{
			@Override
			public void invoke(long window, int width, int height)
			{
				// TODO: adjust matrix transformations
				// TODO: adjust sprite scale as well
				glViewport(0, 0, width, height);

				if(framebuffer != null);
					framebuffer.resize(width, height);
			}
		});

		System.out.println("Done");
	}
	
	double lastTime = glfwGetTime();
	int frameCount = 0;
	
    public void printFPS()
    {
		double currentTime = glfwGetTime();
		frameCount++;
		
		if(currentTime - lastTime >= 1.0)
		{
			System.out.println(frameCount + " FPS");
			frameCount = 0;
			lastTime += 1.0;
		}
    }
    
    public void setFramebuffer(Framebuffer framebuffer)
    {
    	this.framebuffer = framebuffer;
    }

	/*
	 * Sets the visibility of the window
	 */
	public void setVisible(boolean isVisible)
	{
		if(isVisible)
			glfwShowWindow(window);
		else
			glfwHideWindow(window);
	}

	/*
	 * Checks if the current window is ready to close.
	 */
	public boolean closing()
	{
		return glfwWindowShouldClose(window);
	}

	/*
	 * Clear the window with a color.
	 * Called before every frame
	 */
	public void clear(float R, float G, float B)
	{
		glClearColor(R, G, B, 0.0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	/**
	 * Clear the screen with black
	 */
	public void clear()
	{
		clear(0.0f, 0.0f, 0.0f);
	}

	/*
	 * Update the window.
	 * Called before clearing the frame.
	 */
	public void display()
	{
		glfwSwapBuffers(window);
	}

	public int getWidth()
	{
		try (MemoryStack stack = MemoryStack.stackPush())
		{
			IntBuffer width = stack.mallocInt(1);
			glfwGetFramebufferSize(window, width, null);
			return width.get(0);
		}
	}

	public int getHeight()
	{
		try (MemoryStack stack = MemoryStack.stackPush())
		{
			IntBuffer height = stack.mallocInt(1);
			glfwGetFramebufferSize(window, null, height);
			return height.get(0);
		}
	}
}
