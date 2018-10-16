import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.glBlendFuncSeparate;

import graphics.RenderList;
import graphics.Renderer;
import graphics.Sprite;
import graphics.Texture;
import graphics.Window;
import graphics.graphicsUtil.Color;
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
    private Texture tex10;
    private Texture tex2;
    private Renderer renderer;
    private Framebuffer fbo;
     
    public Main() 
    {
        // Initialize OpenGL and GLFW
    	window = new Window();
        window.init(WIDTH, HEIGHT, "Lanorian Roguelite");
         
        RenderList renderList = new RenderList();
        
        tex1 = new Texture("resources/images/narry");
        tex10 = new Texture("resources/images/narry");
        Sprite soda = new Sprite(tex1, "default");
        Sprite soda2 = new Sprite(tex10, "default");
        
    	soda.setPosition(200, 50);
    	soda.setScale(3.0f, 3.0f);
    	
    	soda2.setPosition(300, 0);
    	soda2.setScale(-4f, 4f);
    	renderList.add(soda);
    	renderList.add(soda2);
        
        tex2 = new Texture("resources/images/birboi");
        Sprite mountainDew = new Sprite(tex2, "fly");
        mountainDew.setPosition(100, 300);
        mountainDew.setScale(4.0f, 4.0f);
        renderList.add(mountainDew);
        
        renderer = new Renderer();
        renderer.setWindow(window);
        
        fbo = new Framebuffer(window.getWidth(), window.getHeight());
        fbo.setWindow(window);
        
        // Game loop
        while (!window.closing()) 
        {
        	// Get input
            glfwPollEvents();
            
            fbo.begin();
            
            renderer.resize(fbo.getWidth(), fbo.getHeight());
            
            // Clear the screen before calling anything
            window.clear(0.0f, 0.2f, 0.2f);
    		glClear(GL_COLOR_BUFFER_BIT);
           
    		glBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_ONE, GL_ONE);
            
            renderer.begin();
            	renderer.setColor(Color.WHITE);
            	//renderer.setShader(renderer.shader, false);
            	renderList.draw(renderer);
            renderer.end();
            
            fbo.end();
            
            renderer.resize(window.getWidth(), window.getHeight());
            
    		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
 		
    		// Swap buffers
            window.display();
                         
            window.printFPS();
        }
        this.destroyOpenGL();
    }
     
    private void destroyOpenGL()
    {  
        tex1.delete();
        fbo.delete();
        renderer.delete();
        glfwTerminate();
    }
}