import java.nio.IntBuffer;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Main
{
	// This calls errors to System.err
	private static GLFWErrorCallback errorCallback = GLFWErrorCallback.createPrint(System.err);
	
	private static long window;
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	
	public static void main(String[] args)
	{
		// Set error callback
        glfwSetErrorCallback(errorCallback);
        
        // Initialize GLFW
        if(!glfwInit())
        {
        	throw new IllegalStateException("Unable to initialize GLFW"); 	
        }
        
        // Create window
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
        window = glfwCreateWindow(WIDTH, HEIGHT, "Lanorian Roguelite", NULL, NULL);
        if(window == NULL)
        {
        	glfwTerminate();
        	throw new RuntimeException("Failed to create GLFW window");
        }
        
        // Center the screen based on user monitor dimensions
        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, (vidMode.width() - WIDTH) / 2, (vidMode.height() - HEIGHT) / 2);
	
        // Create OpenGL context
        glfwMakeContextCurrent(window);
        GL.createCapabilitiesWGL();
        
        //glfwSetKeyCallback(window, keyCallback);
	}
}
