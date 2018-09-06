import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
 
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;

import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;
 
import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;
 
public class Main {
    // Entry point for the application
    public static void main(String[] args) {
        new Main();
    }
     
    // Setup variables
    private final String WINDOW_TITLE = "The Quad: Textured";
    private final int WIDTH = 320;
    private final int HEIGHT = 320;
    // Quad variables
    private int vaoId = 0;
    private int vboId = 0;
    private int vboiId = 0;
    private int indicesCount = 0;
    // Shader variables
    private int vsId = 0;
    private int fsId = 0;
    private int pId = 0;
    // Texture variables
    private int[] texIds = new int[] {0, 0};
    private int textureSelector = 0;
    
    private long window;
     
    public Main() {
        // Initialize OpenGL (Display)
        this.setupOpenGL();
         
        this.setupQuad();
        this.setupShaders();
        this.setupTextures();
         
        while (!glfwWindowShouldClose(window)) {
            // Do a single loop (logic/render)
            this.loopCycle();
             
            glfwPollEvents();
        }
         
        // Destroy OpenGL (Display)
        this.destroyOpenGL();
    }
 
    private void setupTextures() {
        texIds[0] = this.loadPNGTexture("src/resources/images/narry.png", GL13.GL_TEXTURE0);
        texIds[1] = this.loadPNGTexture("src/resources/images/birboi.png", GL13.GL_TEXTURE0);
         
        this.exitOnGLError("setupTexture");
    }
 
    private void setupOpenGL() {
        // Setup an OpenGL context with API version 3.2
    	glfwInit();
    	
    	glfwDefaultWindowHints();
    	glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
    	glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
    	glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
    	glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
    	glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
    	
    	window = glfwCreateWindow(800, 600, "Lanorian Roguelite", NULL, NULL);
    	
    	// Get the resolution of the primary monitor
<<<<<<< HEAD
=======
import graphics.Renderer;
import graphics.Texture;
import graphics.Window;
import util.Clock;
import util.IntRect;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;

import org.joml.Matrix4f;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;

import org.lwjgl.glfw.*;
>>>>>>> 1620456... Buncha rendering things + start to shaders

<<<<<<< HEAD
		// Center the window
        
		//glfwSetKeyCallback(window, keyCallback);
=======
public class Main
{
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	private static final int FPS = 60;
	
	public static void main(String[] args)
	{
		// Print errors to the console
		GLFWErrorCallback.createPrint(System.err).set();
=======
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
>>>>>>> cfb0628... Cleanup
		
		glfwInit();
>>>>>>> 35258d4... stuff
		
<<<<<<< HEAD
		// Use this window for rendering
        glfwMakeContextCurrent(window);
        GL.createCapabilities();
         
        // Setup an XNA like background color
        GL11.glClearColor(0.4f, 0.6f, 0.9f, 0f);
         
        // Map the internal OpenGL coordinate system to the entire screen
        GL11.glViewport(0, 0, WIDTH, HEIGHT);
         
        this.exitOnGLError("setupOpenGL");
    }
     
    private void setupQuad() {
        // We'll define our quad using 4 vertices of the custom 'TexturedVertex' class
        TexturedVertex v0 = new TexturedVertex(); 
        v0.setXYZ(-0.5f, 0.5f, 0); v0.setRGB(1, 0, 0); v0.setST(0, 0);
        TexturedVertex v1 = new TexturedVertex(); 
        v1.setXYZ(-0.5f, -0.5f, 0); v1.setRGB(0, 1, 0); v1.setST(0, 1);
        TexturedVertex v2 = new TexturedVertex(); 
        v2.setXYZ(0.5f, -0.5f, 0); v2.setRGB(0, 0, 1); v2.setST(1, 1);
        TexturedVertex v3 = new TexturedVertex(); 
        v3.setXYZ(0.5f, 0.5f, 0); v3.setRGB(1, 1, 1); v3.setST(1, 0);
         
        TexturedVertex[] vertices = new TexturedVertex[] {v0, v1, v2, v3};
        // Put each 'Vertex' in one FloatBuffer
        FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length *
                TexturedVertex.elementCount);
        for (int i = 0; i < vertices.length; i++) {
            // Add position, color and texture floats to the buffer
            verticesBuffer.put(vertices[i].getElements());
        }
        verticesBuffer.flip();  
        // OpenGL expects to draw vertices in counter clockwise order by default
        byte[] indices = {
                0, 1, 2,
                2, 3, 0
        };
        indicesCount = indices.length;
        ByteBuffer indicesBuffer = BufferUtils.createByteBuffer(indicesCount);
        indicesBuffer.put(indices);
        indicesBuffer.flip();
         
        // Create a new Vertex Array Object in memory and select it (bind)
        vaoId = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoId);
         
        // Create a new Vertex Buffer Object in memory and select it (bind)
        vboId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);
         
        // Put the position coordinates in attribute list 0
        GL20.glVertexAttribPointer(0, TexturedVertex.positionElementCount, GL11.GL_FLOAT, 
                false, TexturedVertex.stride, TexturedVertex.positionByteOffset);
        // Put the color components in attribute list 1
        GL20.glVertexAttribPointer(1, TexturedVertex.colorElementCount, GL11.GL_FLOAT, 
                false, TexturedVertex.stride, TexturedVertex.colorByteOffset);
        // Put the texture coordinates in attribute list 2
        GL20.glVertexAttribPointer(2, TexturedVertex.textureElementCount, GL11.GL_FLOAT, 
                false, TexturedVertex.stride, TexturedVertex.textureByteOffset);
         
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
         
        // Deselect (bind to 0) the VAO
        GL30.glBindVertexArray(0);
         
        // Create a new VBO for the indices and select it (bind) - INDICES
        vboiId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiId);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
         
        this.exitOnGLError("setupQuad");
    }
     
    private void setupShaders() {       
        // Load the vertex shader
        vsId = this.loadShader("src/resources/shaders/TestVert.glsl", GL20.GL_VERTEX_SHADER);
        // Load the fragment shader
        fsId = this.loadShader("src/resources/shaders/TestFrag.glsl", GL20.GL_FRAGMENT_SHADER);
         
        // Create a new shader program that links both shaders
        pId = GL20.glCreateProgram();
        GL20.glAttachShader(pId, vsId);
        GL20.glAttachShader(pId, fsId);
 
        // Position information will be attribute 0
        GL20.glBindAttribLocation(pId, 0, "in_Position");
        // Color information will be attribute 1
        GL20.glBindAttribLocation(pId, 1, "in_Color");
        // Textute information will be attribute 2
        GL20.glBindAttribLocation(pId, 2, "in_TextureCoord");
         
        GL20.glLinkProgram(pId);
        GL20.glValidateProgram(pId);
         
        this.exitOnGLError("setupShaders");
    }
     
    private void loopCycle() {

         
        // Render
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
         
        GL20.glUseProgram(pId);
         
        // Bind the texture
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texIds[textureSelector]);
         
        // Bind to the VAO that has all the information about the vertices
        GL30.glBindVertexArray(vaoId);
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
         
        // Bind to the index VBO that has all the information about the order of the vertices
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiId);
         
        // Draw the vertices
        GL11.glDrawElements(GL11.GL_TRIANGLES, indicesCount, GL11.GL_UNSIGNED_BYTE, 0);
         
        glfwSwapBuffers(window);
        // Put everything back to default (deselect)
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
         
        GL20.glUseProgram(0);
         
        this.exitOnGLError("loopCycle");
    }
     
    private void destroyOpenGL() {  
        // Delete the texture
        GL11.glDeleteTextures(texIds[0]);
        GL11.glDeleteTextures(texIds[1]);
         
        // Delete the shaders
        GL20.glUseProgram(0);
        GL20.glDetachShader(pId, vsId);
        GL20.glDetachShader(pId, fsId);
         
        GL20.glDeleteShader(vsId);
        GL20.glDeleteShader(fsId);
        GL20.glDeleteProgram(pId);
         
        // Select the VAO
        GL30.glBindVertexArray(vaoId);
         
        // Disable the VBO index from the VAO attributes list
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
         
        // Delete the vertex VBO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL15.glDeleteBuffers(vboId);
         
        // Delete the index VBO
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL15.glDeleteBuffers(vboiId);
         
        // Delete the VAO
        GL30.glBindVertexArray(0);
        GL30.glDeleteVertexArrays(vaoId);
         
        this.exitOnGLError("destroyOpenGL");
         
        glfwTerminate();
    }
     
    private int loadShader(String filename, int type) {
        StringBuilder shaderSource = new StringBuilder();
        int shaderID = 0;
         
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                shaderSource.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Could not read file.");
            e.printStackTrace();
            System.exit(-1);
        }
         
        shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, shaderSource);
        GL20.glCompileShader(shaderID);
         
		int status = glGetShaderi(shaderID, GL_COMPILE_STATUS);
		if (status != GL_TRUE) {
		    throw new RuntimeException(glGetShaderInfoLog(shaderID));
<<<<<<< HEAD
=======
		Window window = new Window();
		window.init(WIDTH, HEIGHT, "Lanorian Roguelite");
		glfwSwapInterval(1);
		
		Renderer renderer = new Renderer();
		renderer.setWindow(window);
		
		Texture tex1 = new Texture("resources/images/narry");
		
		Clock gameClock = new Clock();
		float delta = 0;
		
		// temporary game loop
		while(!window.closing())
		{
			delta = gameClock.getElapse();
			gameClock.restart();
			
			// Clear screen
			window.clear();
			
			//draw stuff
			//Matrix4f view = renderer.getViewMatrix();
			//view.identity();
			
			renderer.updateUniforms();
			
			// TODO: render list for convenient drawing, of course
			renderer.beginDrawing();
				renderer.drawTexture(tex1, new IntRect(0, 0, 32, 32));		
			renderer.end();
			
			// Swap buffers
			window.display();
<<<<<<< HEAD
>>>>>>> 1620456... Buncha rendering things + start to shaders
=======
			
			//temporary til I do something different
			glfwPollEvents();
<<<<<<< HEAD
>>>>>>> 962d71b... more changes that do nothing
=======
			
			// Delay
			try
			{
				long totalNanos = (int)(1e9/FPS) - (int)(gameClock.getElapse()*1e9f);
				if(totalNanos > 0)
				{
					int nanos = (int) (totalNanos % 1000000);
					long milis = (totalNanos - nanos) / 1000000;
					Thread.sleep(milis, (int)nanos);
				}
			} catch (InterruptedException e)
			{
				break;
			}
>>>>>>> 35258d4... stuff
=======
>>>>>>> cfb0628... Cleanup
		}
         
        this.exitOnGLError("loadShader");
         
        return shaderID;
    }
     
    private int loadPNGTexture(String filename, int textureUnit) {
        ByteBuffer buf = null;
        int tWidth = 0;
        int tHeight = 0;
         
        try {
            // Open the PNG file as an InputStream
            InputStream in = new FileInputStream(filename);
            // Link the PNG decoder to this stream
            PNGDecoder decoder = new PNGDecoder(in);
             
            // Get the width and height of the texture
            tWidth = decoder.getWidth();
            tHeight = decoder.getHeight();
             
             
            // Decode the PNG file in a ByteBuffer
            buf = ByteBuffer.allocateDirect(
                    4 * decoder.getWidth() * decoder.getHeight());
            decoder.decode(buf, decoder.getWidth() * 4, Format.RGBA);
            buf.flip();
             
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
         
        // Create a new texture object in memory and bind it
        int texId = GL11.glGenTextures();
        GL13.glActiveTexture(textureUnit);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texId);
         
        // All RGB bytes are aligned to each other and each component is 1 byte
        GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
         
        // Upload the texture data and generate mip maps (for scaling)
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, tWidth, tHeight, 0, 
                GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buf);
        GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
         
        // Setup the ST coordinate system
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
         
        // Setup what to do when the texture has to be scaled
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, 
                GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, 
                GL11.GL_LINEAR_MIPMAP_LINEAR);
         
        this.exitOnGLError("loadPNGTexture");
         
        return texId;
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