import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;

import graphics.Shader;
import graphics.Texture;
import graphics.Window;
import graphics.graphicsUtil.Vertex;
import graphics.graphicsUtil.VertexArray;
 
public class Main {

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
            transform.rotate((float)Math.toRadians(60*GLFW.glfwGetTime()), 0.0f, 0.0f, 1.0f);
            // Use the selected shader
            shader.useProgram();
            shader.setUniformMat4f("transform", transform);
            
            // Use this texture and its vertices for rendering
            tex1.bind();
            vertices.bind();
            
            // Draw the vertices
            vertices.draw(); 
            
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