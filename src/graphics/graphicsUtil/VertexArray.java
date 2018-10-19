package graphics.graphicsUtil;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class VertexArray
{
    private FloatBuffer verticesBuffer;
    private ByteBuffer indicesBuffer;

    private int vaoID;
    private int vboID;
    private int vboiID;
    
    // Bytes per float
    public static final int BPF = 4;

    // Indices per quad
    private static final int INDICES = 6;

    // Elements per parameter
    public static final int POSITION_ELEMENT_COUNT 	= 3;
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
    
    /*
     * Default constructor
     */
    public VertexArray() 
    {
    	this(1000);
    }
    
    /**
     * 
     * @param size the number of vertices (e.g. 3 for a triangle)
     */
    public VertexArray(int size)
    {
    	verticesBuffer = BufferUtils.createFloatBuffer(size * INDICES);
    	indicesBuffer = BufferUtils.createByteBuffer(size * INDICES);
    }
    
    /**
     * Add a vertex to vertex array
     * @param vert the vertex to add
     * @return this vertex array for further editing
     */
    public VertexArray putVert(Vertex vert)
    {
    	float[] position = {vert.position.x, vert.position.y, vert.position.z};
    	float[] color = {vert.color.x, vert.color.y, vert.color.z, vert.color.w};
    	float[] st = {vert.st.x, vert.st.y};
    	
    	verticesBuffer.put(position);
		verticesBuffer.put(color);
		verticesBuffer.put(st);
	
    	return this;
    }
    
    /**
     * Put an array of indices into the element buffer for rendering
     * textures
     * 
     * @param indices the array of byte indices
     * @return this vertex array for further editing
     */
    public VertexArray putIdx(byte[] indices)
    {
    	indicesBuffer.put(indices);
    	return this;
    }
    
    public void init()
    {
    	// Create a new vertex array object in memory and bind it
    	vaoID = glGenVertexArrays();
    	glBindVertexArray(vaoID);
    	
    	// Create the vertex buffer object ahead of time and bind it
    	vboID = glGenBuffers();
    	glBindBuffer(GL_ARRAY_BUFFER, vboID);
    	glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
    
    	// Create vertex attributes
    	glVertexAttribPointer(POSITION_ATTRB, POSITION_ELEMENT_COUNT, GL_FLOAT,
    			false, STRIDE, POSITION_OFFSET);
    	glVertexAttribPointer(COLOR_ATTRB, COLOR_ELEMENT_COUNT, GL_FLOAT,
    			false, STRIDE, COLOR_OFFSET);
    	glVertexAttribPointer(ST_ATTRB, ST_ELEMENT_COUNT, GL_FLOAT,
    			false, STRIDE, ST_OFFSET);
    	
    	// Create vertex buffer element array
    	vboiID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboiID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);

        // Unbind everything since they're bound
    	glBindVertexArray(0);
    	glBindBuffer(GL_ARRAY_BUFFER, 0);
    	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    /*
     * Flip the buffers
     * This sets the buffers from write mode to read mode
     */
    public void flip()
    {
    	verticesBuffer.flip();
    	indicesBuffer.flip();
    }
    
    /*
     * Bind the vertex array object, vertex buffer, and element buffer
     */
    public void bind()
    {
    	// Bind to the VAO that has all the information about the vertices
    	glBindVertexArray(vaoID);
    	
    	// Bind the vertex buffer object
    	glBindBuffer(GL_ARRAY_BUFFER, vboID);
    	glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
    	
    	glEnableVertexAttribArray(POSITION_ATTRB);
    	glEnableVertexAttribArray(COLOR_ATTRB);
    	glEnableVertexAttribArray(ST_ATTRB);
    	
    	// Bind to the index VBO that has all the information about the order of the vertices
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboiID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
    }
    
    public void draw(int indexCount)
    {
    	glDrawElements(GL_TRIANGLES, indexCount, GL_UNSIGNED_BYTE, 0);
    }
    
    /*
     * Puts everything back to default and clears the buffers
     * for the next frame 
     * Called at the end of the rendering loop.
     */
    public void reset()
    {
    	verticesBuffer.clear();
    	indicesBuffer.clear();
    	
    	glBindVertexArray(0);
    	glBindBuffer(GL_ARRAY_BUFFER, 0);
    	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    	
    	glDisableVertexAttribArray(POSITION_ATTRB);
    	glDisableVertexAttribArray(COLOR_ATTRB);
    	glDisableVertexAttribArray(ST_ATTRB);
    }
    
    public void delete()
    {
    	// Prepare VAO for deletion
    	glBindVertexArray(vaoID);
    	
    	// Disable VBO index from VAO attribute list
    	glDisableVertexAttribArray(POSITION_ATTRB);
    	glDisableVertexAttribArray(COLOR_ATTRB);
    	
    	// Delete vertex VBO
    	glBindBuffer(GL_ARRAY_BUFFER, 0);
    	glDeleteBuffers(vboID);
    	
    	// Delete index VBO
    	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    	glDeleteBuffers(vboiID);
    	
    	// Fnally, delete the VAO
    	glBindVertexArray(0);
    	glDeleteVertexArrays(vaoID);
    }
    
    public ByteBuffer getIndexBuffer()
    {
    	return indicesBuffer;
    }
    
    public FloatBuffer getVertexBuffer()
    {
    	return verticesBuffer;
    }
}