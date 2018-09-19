package graphics;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;

import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

public class Window
{
	private long window;
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

		glfwMakeContextCurrent(window);
		GL.createCapabilities();

		// Enable alpha blending
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
			}
		});

		System.out.println("Done");
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

	/**
	 * Use this window for rendering
	 */
	public void useGLContext()
	{

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
