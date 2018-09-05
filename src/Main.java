import org.lwjgl.glfw.GLFWErrorCallback;

import graphics.Window;

import static org.lwjgl.glfw.GLFW.glfwInit;
import org.lwjgl.glfw.*;

public class Main
{
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	
	public static void main(String[] args)
	{
		// Print errors to the console
		GLFWErrorCallback.createPrint(System.err).set();
		
		glfwInit();
		
		Window window = new Window();
		window.init(WIDTH, HEIGHT, "Lanorian Roguelite");
		
		// temporary game loop
		while(!window.closing())
		{
			// handle window events
			// update()
			// renderer.clear()
			// draw stuff
			// swap buffers
		}
	}
}