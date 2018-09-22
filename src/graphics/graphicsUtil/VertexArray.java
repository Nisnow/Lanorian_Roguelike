package graphics.graphicsUtil;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class VertexArray
{
    private ArrayList<Vertex> vertices = new ArrayList<Vertex>();
    
    private FloatBuffer buffer;
    
    private int vaoID;
    private int vboID;
    private int vboiID;
    
    // Bytes per float
    public static final int BPF = 4;
     
    // Elements per parameter
    public static final int POSITION_ELEMENT_COUNT 	= 4;
    public static final int COLOR_ELEMENT_COUNT 	= 4;
    public static final int ST_ELEMENT_COUNT 		= 2;
     
    // Bytes per parameter
    public static final int POSITION_BYTES 	= POSITION_ELEMENT_COUNT * BPF;
    public static final int COLOR_BYTES 	= COLOR_ELEMENT_COUNT * BPF;
    public static final int ST_BYTES 		= ST_ELEMENT_COUNT * BPF;
     
    // Byte offsets per parameter
    public static final int POSITION_OFFSET = 0;
    public static final int COLOR_OFFSET 	= POSITION_OFFSET + POSITION_BYTES;
    public static final int ST_OFFSET 		= COLOR_OFFSET + COLOR_BYTES;
     
    // The number of elements that a vertex has
    public static final int ELEMENT_COUNT = POSITION_ELEMENT_COUNT + 
            COLOR_ELEMENT_COUNT + ST_ELEMENT_COUNT;    
    
    // The size of a vertex in bytes
    public static final int STRIDE = POSITION_BYTES + COLOR_BYTES + 
            ST_BYTES;
    
    // Vertex array attributes
    public static final int POSITION_ATTRB = 0;
    public static final int COLOR_ATTRB = 1;
    public static final int ST_ATTRB = 2;
    
    private ByteBuffer indicesBuffer ;
    private int indicesCount;
    
    // OpenGL expects to draw vertices in counter clockwise order by default
    // TODO: put in renderer
    private byte[] indices = {
            0, 1, 2,
            2, 3, 0
    };
    /*
     * Default constructor
     */
    public VertexArray() {}
    
    /**
     * Add a vertex to vertex array
     * @param vert the vertex to add
     * @return this vertex array for further editing
     */
    public VertexArray add(Vertex vert)
    {
    	vertices.add(vert);
    	return this;
    }
    
    public void bind()
    {
    	buffer = BufferUtils.createFloatBuffer(vertices.size() * ELEMENT_COUNT);
    	
    	// Put vertices in float buffer
    	for(Vertex v : vertices)
    	{
    		buffer.put(v.getPosition());
    		buffer.put(v.getColor());
    		buffer.put(v.getST());
    	}
    	buffer.flip();
        
        indicesCount = indices.length;
        indicesBuffer = BufferUtils.createByteBuffer(indicesCount);
        indicesBuffer.put(indices);
        indicesBuffer.flip();
    	
    	// Create a new vertex array object in memory and bind it
    	vaoID = glGenVertexArrays();
    	glBindVertexArray(vaoID);
    	
    	// Create a new vertex buffer object in memory and bind it
    	vboID = glGenBuffers();
    	glBindBuffer(GL_ARRAY_BUFFER, vboID);
    	glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
    
    	// Bind vertex attributes
    	glVertexAttribPointer(POSITION_ATTRB, POSITION_ELEMENT_COUNT, GL_FLOAT,
    			false, STRIDE, POSITION_OFFSET);
    	glVertexAttribPointer(COLOR_ATTRB, COLOR_ELEMENT_COUNT, GL_FLOAT,
    			false, STRIDE, COLOR_OFFSET);
    	glVertexAttribPointer(ST_ATTRB, ST_ELEMENT_COUNT, GL_FLOAT,
    			false, STRIDE, ST_OFFSET);
    	
    	// Unbind VAO and VBO now that they're registered
    	glBindBuffer(GL_ARRAY_BUFFER, 0);
    	glBindVertexArray(0);
    	
        // Create a new VBO for the indices and select it (bind) - INDICES
        vboiID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboiID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }
    
    public void unbind()
    {
    	// TODO: buffer.clear();
    }
    
    public FloatBuffer getBuffer()
    {
    	return buffer;
    }
    
    public int getVAO()
    {
    	return vaoID;
    }
    
    public int getVBO()
    {
    	return vboID;
    }
    
    public int getVBOi()
    {
    	return vboiID;
    }
    
    public int getIndicesCount()
    {
    	return indicesCount;
    }
    
    public int length()
    {
    	return vertices.size();
    }
    
    public Vertex get(int index)
    {
    	return vertices.get(index);
    }    
}