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
    
    private Window window;
    private Shader shader;
    private VertexArray vertices;
     
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
        
        
        this.setupTextures();
        //vertices.bind();
        //shader = new Shader("src/resources/shaders/TestVert.glsl", "src/resources/shaders/TestFrag.glsl");
        
        this.setupQuad();
        this.setupShaders();

         
        // Game loop
        while (!window.closing()) 
        {
            glfwPollEvents();
            
            // Do a single loop (logic/render)
            this.loopCycle();
             
            window.printFPS();
        }
         
        // Destroy OpenGL
        this.destroyOpenGL();
    }
 
    private void setupTextures()
    {
    	// TODO: texture class with bind() methods to 
        texIds[0] = this.loadPNGTexture("src/resources/images/narry.png", GL13.GL_TEXTURE0);
        texIds[1] = this.loadPNGTexture("src/resources/images/birboi.png", GL13.GL_TEXTURE0);
         
        this.exitOnGLError("setupTexture");
    }
     
    // DE1337 (haxx0r) LATER
    private void setupQuad() 
    {
        VertexArray vertices = new VertexArray();
        vertices.add(new Vertex().setPosition(-0.5f, 0.5f, 0).setColor(1, 0, 0).setST(0, 0));
        vertices.add(new Vertex().setPosition(-0.5f, -0.5f, 0).setColor(0, 1, 0).setST(0, 1));
        vertices.add(new Vertex().setPosition(0.5f, -0.5f, 0).setColor(0, 0, 1).setST(1, 1));
        vertices.add(new Vertex().setPosition(0.5f, 0.5f, 0).setColor(1, 1, 1).setST(1, 0));
        
        // Put each 'Vertex' in one FloatBuffer
        FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length() *
                VertexArray.ELEMENT_COUNT);
        
        for (int i = 0; i < vertices.length(); i++) 
        {
            // Add position, color and texture floats to the buffer
            verticesBuffer.put(vertices.get(i).getPosition());
            verticesBuffer.put(vertices.get(i).getColor());
            verticesBuffer.put(vertices.get(i).getST());
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
        GL20.glVertexAttribPointer(0, VertexArray.POSITION_ELEMENT_COUNT, GL11.GL_FLOAT, 
                false, VertexArray.STRIDE, VertexArray.POSITION_OFFSET);
        
        // Put the color components in attribute list 1
        GL20.glVertexAttribPointer(1, VertexArray.COLOR_ELEMENT_COUNT, GL11.GL_FLOAT, 
                false, VertexArray.STRIDE, VertexArray.COLOR_OFFSET);
        
        // Put the texture coordinates in attribute list 2
        GL20.glVertexAttribPointer(2, VertexArray.ST_ELEMENT_COUNT, GL11.GL_FLOAT, 
                false, VertexArray.STRIDE, VertexArray.ST_OFFSET);
         
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
         
        // Deselect (bind to 0) the VAO
        GL30.glBindVertexArray(0);
         
        vboiId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiId);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
       
        this.exitOnGLError("setupQuad");
    }
     
    private void setupShaders()
    {       
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
        GL20.glBindAttribLocation(pId, 1, "in_Color");
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
         
        window.display();
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
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, tWidth, tHeight, 0, 
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