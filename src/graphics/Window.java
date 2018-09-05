package graphics;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

public class Window
{
	private long window;
	
    private static GLFWKeyCallback keyCallback = new GLFWKeyCallback()
    {
        @Override
        public void invoke(long window, int key, int scancode, int action, int mods)
        {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS)
            {
                glfwSetWindowShouldClose(window, true);
            }
        }
    };
    
    /*
     * Initializes the window
     */
    public void init(int p_width, int p_height, String p_title)
    {
    	System.out.println("Initializing...");
    	
    	glfwInit();
    	
    	glfwDefaultWindowHints();
    	glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
    	
    	window = glfwCreateWindow(p_width, p_height, p_title, NULL, NULL);
    	
    	// Get the resolution of the primary monitor
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

		// Center the window
		glfwSetWindowPos(window, (vidmode.width() - p_width) / 2,
								 (vidmode.height() - p_height / 2));
        
		glfwSetKeyCallback(window, keyCallback);
		
		// Use this window for rendering
        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();
        
        // Enable alpha blending
        glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
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
	 * Get position of cursor
	 */
	public float[] getCursorPosition()
	{
		try (MemoryStack stack = MemoryStack.stackPush())
		{
			DoubleBuffer x = stack.mallocDouble(1);
			DoubleBuffer y = stack.mallocDouble(1);
			glfwGetCursorPos(window, x, y);
			return new float[]{(float)x.get(0), (float)y.get(0)};
		}
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
 