import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;

import graphics.Renderer;
import graphics.Shader;
import graphics.Sprite;
import graphics.Texture;
import graphics.Window;
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
           
            // TODO: put these in renderer !!!!
            Matrix4f transform = new Matrix4f();
            
            // DEFAULT: radians, 0.0, 0.0, 1.0);
            float time = ((float) GLFW.glfwGetTime());
            //transform.translate((float) Math.cos(time), 0, 0);
            //transform.rotate((float)Math.toRadians(60*time), 0.0f, 1.0f, 1.0f);
            transform.scale(3.0f, 3.0f, 0.0f);
            
            renderer.begin();
            	renderer.pushMatrix(transform);
            	soda.draw(renderer);
            	renderer.popMatrix();
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