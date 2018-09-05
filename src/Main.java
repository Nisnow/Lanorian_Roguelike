import java.nio.IntBuffer;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Main
{
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	
	private long window;
	
    private static GLFWKeyCallback keyCallback = new GLFWKeyCallback() {

        @Override
        public void invoke(long window, int key, int scancode, int action, int mods) {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS) {
                glfwSetWindowShouldClose(window, true);
            }
        }
    };
    
    public void run()
    {
    	System.out.println("blarghr");
    	
    	init();
    	loop();
    	
		// Free the window callbacks and destroy the window
        glfwDestroyWindow(window);
		glfwDestroyWindow(window);

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
    }
    
    private void init()
    {
    	// Set error callback
		GLFWErrorCallback.createPrint(System.err).set();
        
        // Initialize GLFW
        if(!glfwInit())
        {
        	throw new IllegalStateException("Unable to initialize GLFW"); 	
        }
        
		glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
		
        // Create window
        window = glfwCreateWindow(WIDTH, HEIGHT, "Lanorian Roguelite", NULL, NULL);
        if(window == NULL)
        {
        	glfwTerminate();
        	throw new RuntimeException("Failed to create GLFW window");
        }
        
     // Get the thread stack and push a new frame
 		try ( MemoryStack stack = MemoryStack.stackPush() )
 		{
 			IntBuffer pWidth = stack.mallocInt(1); // int*
 			IntBuffer pHeight = stack.mallocInt(1); // int*

 			// Get the window size passed to glfwCreateWindow
 			glfwGetWindowSize(window, pWidth, pHeight);

 			// Get the resolution of the primary monitor
 			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

 			// Center the window
 			glfwSetWindowPos(window, (vidmode.width() - pWidth.get(0)) / 2,
 									 (vidmode.height() - pHeight.get(0)) / 2);
 		} // the stack frame is popped automatically
 		
        // Create OpenGL context
        glfwMakeContextCurrent(window);
        
        glfwSwapInterval(1);
        
        glfwSetKeyCallback(window, keyCallback);
   
        glfwShowWindow(window);
    }
    
    private void loop()
    {
        GL.createCapabilitiesWGL();
        
        //glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
        
        while(!glfwWindowShouldClose(window))
        {
        	//glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        	
        	glfwSwapBuffers(window);
        	
        	glfwPollEvents();
        }
    }
    
	public static void main(String[] args)
	{
		new Main().run();
	}
}
