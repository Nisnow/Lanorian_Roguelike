import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

import graphics.Renderer;
import graphics.Texture;
import graphics.Window;
import graphics.graphicsUtil.Framebuffer;
import util.IntRect;
 
public class Main 
{
	public static void main(String[] args) 
    {
        new Main();
    }
     
    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    
    private Window window;
    private Texture tex1;
    private Texture tex2;
    private Framebuffer fbo;
     
    public Main() 
    {
        // Initialize OpenGL and GLFW
    	window = new Window();
        window.init(WIDTH, HEIGHT, "Lanorian Roguelite");
        
        tex1 = new Texture("resources/images/narry");
        tex2 = new Texture("resources/images/birboi");
        
        fbo = new Framebuffer(window.getWidth(), window.getHeight());
        fbo.setWindow(window);
        
        Renderer renderer = new Renderer();
        renderer.setFramebuffer(fbo);
        
        // Game loop
        while (!window.closing()) 
        {
        	// Get input
            glfwPollEvents();
            
            renderer.drawTexture(tex1, new IntRect(0, 0, 32, 32));
            renderer.drawTexture(tex2, new IntRect(0, 0, 64, 64));
            renderer.render();
            window.display();
            window.printFPS();
             
        }
        this.destroyOpenGL();
    }
     
    private void destroyOpenGL()
    {  
        tex1.delete();
        fbo.delete();
        glfwTerminate();
    }
}