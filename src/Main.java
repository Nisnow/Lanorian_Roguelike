import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

import graphics.RenderList;
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
         
        RenderList renderList = new RenderList();
        
        tex1 = new Texture("resources/images/narry");
        Sprite soda = new Sprite(tex1, "default");
        
    	soda.setPosition(200, 50);
    	soda.setScale(3.0f, 3.0f);
    	renderList.add(soda);
        
        tex2 = new Texture("resources/images/birboi");
        Sprite mountainDew = new Sprite(tex2, "fly");
        mountainDew.setPosition(100, 300);
        renderList.add(mountainDew);
        
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
            	renderList.draw(renderer);
            renderer.end();
            
            // Swap buffers
            window.display();
                         
            window.printFPS();
        }
        this.destroyOpenGL();
    }
     
    private void destroyOpenGL()
    {  
        tex1.deleteTexture();
        renderer.delete();
        glfwTerminate();
    }
}