import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;

import engine.components.ComponentMapper;
import engine.components.Entity;
import engine.components.GraphicsComponent;
import engine.components.TransformComponent;
import engine.graphics.Font;
import engine.graphics.Renderer;
import engine.graphics.Texture;
import engine.graphics.Window;
import engine.graphics.graphicsUtil.Color;
import engine.graphics.graphicsUtil.Framebuffer;
 
public class Main 
{
	public static void main(String[] args) 
    {
        new Main();
    }
     
	private final boolean PRINT_FPS = false;
	
    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    
    private Window window;
    private Texture narryTex;
    private Texture birboiTex;
    private Framebuffer fbo;
     
    public Main() 
    {
        // Initialize OpenGL and GLFW
    	window = new Window();
        window.init(WIDTH, HEIGHT, "Lanorian Roguelite");
        
        narryTex = new Texture("resources/images/narry");
        birboiTex = new Texture("resources/images/birboi");
        
        fbo = new Framebuffer(window.getWidth(), window.getHeight());
        fbo.setWindow(window);
        
        Renderer renderer = new Renderer();
        renderer.setFramebuffer(fbo);

        // FIXME: add everything below to a Scene class of sorts
        Entity narry = new Entity();
        Entity birboi = new Entity();
        
        ArrayList<Entity> entities = new ArrayList<Entity>();
        entities.add(narry);
        entities.add(birboi);
        
        GraphicsComponent narryG = new GraphicsComponent(narryTex, "default");
        GraphicsComponent birbG = new GraphicsComponent(birboiTex, "fly");
        
        TransformComponent narryTrans = new TransformComponent();
        narryTrans.setPosition(300, 300);
        narryTrans.setScale(4.0f, 4.0f);
        narryTrans.setAsParent();
        
        TransformComponent birbTrans = new TransformComponent();
        birbTrans.setPosition(10, 10);
        narryTrans.addChild(birbTrans);
        
        narryG.setTransformComponent(narryTrans);
        birbG.setTransformComponent(birbTrans);
             
        ComponentMapper<GraphicsComponent> graphMap = new ComponentMapper<>();
        ComponentMapper<TransformComponent> transMap = new ComponentMapper<>();
        
        graphMap.add(narry, narryG);
        graphMap.add(birboi, birbG);
        transMap.add(narry, narryTrans);
        transMap.add(birboi, birbTrans);
        
        Font font = new Font(Font.ALEGREYA_SANS);
        font.setTextColor(Color.BLUE);
        TransformComponent tt = new TransformComponent();
        tt.setScale(2.0f);
        tt.setPosition(50, 50);
        tt.setAsParent();
        font.setTransformComponent(tt);
        
        // Game loop
        while (!window.closing()) 
        {
        	// Get input
            glfwPollEvents();
            
            // render the parent 
            // TODO: basic world transform + camera
            narryTrans.setRotation((float) Math.cos(GLFW.glfwGetTime()));
            narryTrans.setScale(4*(float)Math.cos(GLFW.glfwGetTime()), 4*(float)Math.cos(GLFW.glfwGetTime()));
            narryTrans.render(new TransformComponent(), true);
            
            tt.setScale(1.0f, 4*Math.abs((float)Math.cos(GLFW.glfwGetTime())));
            tt.render(new TransformComponent(), true);
            font.drawText(renderer, "Phantom cheese doodles");
            
            for(Entity e : entities)
            	graphMap.getFrom(e).render(renderer);
            
            renderer.render();
            
            window.display();
            if(PRINT_FPS)
            	window.printFPS();
        }
        this.destroyOpenGL();
    }
     
    private void destroyOpenGL()
    {  
        narryTex.delete();
        fbo.delete();
        glfwTerminate();
    }
}