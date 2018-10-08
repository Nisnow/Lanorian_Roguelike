import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.lwjgl.glfw.GLFW;

import graphics.Renderer;
import graphics.Sprite;
import graphics.Texture;
import graphics.Window;
 
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
    private Renderer renderer;
     
    public Main() 
    {
        // Initialize OpenGL and GLFW
    	window = new Window();
        window.init(WIDTH, HEIGHT, "Lanorian Roguelite");
         
        tex1 = new Texture("resources/images/narry");
        Sprite soda = new Sprite(tex1, "default");
        
        renderer = new Renderer();
        renderer.setWindow(window);

        // Game loop
        while (!window.closing()) 
        {
        	// Get input
            glfwPollEvents();
            
            // Clear the screen before calling anything
            window.clear();
           
            renderer.begin();
            	soda.setScale(3.0f, 3.0f);
            	soda.setPosition(200, 100);
            	soda.draw(renderer);
            	// renderList.draw(renderer);
            renderer.end();
            
            // Swap buffers
            window.display();
                         
            window.printFPS();
        }
        this.destroyOpenGL();
    }
     
    private void destroyOpenGL()
    {  
    	// TODO: some texture array in renderer
        tex1.deleteTexture();
        renderer.delete();
        
        glfwTerminate();
    }
}