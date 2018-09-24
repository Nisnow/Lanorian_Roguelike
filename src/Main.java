import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;
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
        vertices.bind();
        shader = new Shader("src/resources/shaders/TestVert.glsl", "src/resources/shaders/TestFrag.glsl");
        
        // Game loop
        while (!window.closing()) 
        {
            glfwPollEvents();
            
            // Render
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
            
            shader.useProgram();
            
            // Bind the texture
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex1.getTextureID());
             
            // Bind to the VAO that has all the information about the vertices
            GL30.glBindVertexArray(vertices.getVAO());
            GL20.glEnableVertexAttribArray(VertexArray.POSITION_ATTRB);
            GL20.glEnableVertexAttribArray(VertexArray.COLOR_ATTRB);
            GL20.glEnableVertexAttribArray(VertexArray.ST_ATTRB);
             
            // Bind to the index VBO that has all the information about the order of the vertices
            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vertices.getVBOi());
             
            // Draw the vertices
            GL11.glDrawElements(GL11.GL_TRIANGLES, vertices.getIndicesCount(), GL11.GL_UNSIGNED_BYTE, 0);
             
            window.display();
            // Put everything back to default (deselect)
            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
            GL20.glDisableVertexAttribArray(0);
            GL20.glDisableVertexAttribArray(1);
            GL20.glDisableVertexAttribArray(2);
            GL30.glBindVertexArray(0);
             
            GL20.glUseProgram(0);
                         
            window.printFPS();
        }
         
        this.destroyOpenGL();
    }
        
    private void loopCycle() 
    {
      
        this.exitOnGLError("loopCycle");
    }
     
    private void destroyOpenGL()
    {  
    	// TODO: some texture array in renderer
        tex1.deleteTexture();
        shader.delete();
        vertices.delete();
        
        this.exitOnGLError("destroyOpenGL");
         
        glfwTerminate();
    }
     
    private void exitOnGLError(String errorMessage) {
        int errorValue = GL11.glGetError();
         
        /*
        if (errorValue != GL11.GL_NO_ERROR) {
            String errorString = GLU.gluErrorString(errorValue);
            System.err.println("ERROR - " + errorMessage + ": " + errorString);
             
            if (Display.isCreated()) Display.destroy();
            System.exit(-1);
        }*/
    }
}