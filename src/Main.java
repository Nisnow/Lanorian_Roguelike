import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;

import graphics.Renderer;
import graphics.Shader;
import graphics.Sprite;
import graphics.Texture;
import graphics.Window;
import graphics.graphicsUtil.Vertex;
import graphics.graphicsUtil.VertexArray;
 
public class Main 
{

	public static void main(String[] args) 
    {
        new Main();
    }
     
    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    
    private Window window;
    private Shader shader;
    private VertexArray vertices;
    private Texture tex1;
     
    public Main() 
    {
        // Initialize OpenGL and GLFW
    	window = new Window();
        window.init(WIDTH, HEIGHT, "Lanorian Roguelite");
         
        // Initialize renderer 
        vertices = new VertexArray();
        vertices.add(new Vertex().setPosition(-0.5f, 0.5f, 0).setColor(1, 0, 0).setST(0, 0));
        vertices.add(new Vertex().setPosition(-0.5f, -0.5f, 0).setColor(0, 1, 0).setST(0, 1));
        vertices.add(new Vertex().setPosition(0.5f, -0.5f, 0).setColor(0, 0, 1).setST(1, 1));
        vertices.add(new Vertex().setPosition(0.5f, 0.5f, 0).setColor(1, 1, 1).setST(1, 0));
        
        tex1 = new Texture("resources/images/narry");
        Sprite soda = new Sprite(tex1);
        
        Renderer renderer = new Renderer();
        
        vertices.createQuad();
        
        shader = new Shader("src/resources/shaders/TestVert.glsl", "src/resources/shaders/TestFrag.glsl");

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
            
            // Renderer.begin() to use shader and set uniforms
            // Use the selected shader
            renderer.begin();
            	renderer.updateUniforms();
            	// renderList.draw(renderer);
            	
            // where all Drawable objects call draw() method
            // add vertices to some vertex array for a buffer
            // Use this texture and its vertices for rendering
            tex1.bind();
            
            // Renderer.end() to bind VAO and actually draw stuff
            vertices.bind();
            
            // Draw the vertices
            vertices.draw(); 
            
            renderer.end();
            
            // Swap buffers
            window.display();
            
            // Reset everything to default
            vertices.reset();
            shader.reset();
                         
            window.printFPS();
        }
         
        this.destroyOpenGL();
    }
     
    private void destroyOpenGL()
    {  
    	// TODO: some texture array in renderer
        tex1.deleteTexture();
        shader.delete();
        vertices.delete();
        
        glfwTerminate();
    }
}