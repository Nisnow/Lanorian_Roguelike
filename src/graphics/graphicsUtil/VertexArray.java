package graphics.graphicsUtil;

import java.nio.FloatBuffer;
import java.util.ArrayList;

public class VertexArray
{
    // delete later
    private float[] position = new float[] {0f, 0f, 0f, 1f};
    private float[] color 	 = new float[] {1f, 1f, 1f, 1f};
    private float[] st 		 = new float[] {0f, 0f};
     
    private ArrayList<Vertex> vertices = new ArrayList<Vertex>();;
    
    private FloatBuffer buffer;
    private int bufferSize;
    
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
    
    /*
     * Default constructor
     */
    public VertexArray() {}
    
    public VertexArray(int size)
    {
    	bufferSize = size;
    }
    
    public VertexArray add(Vertex vert)
    {
    	vertices.add(vert);
    	return this;
    }
    
    public void bind()
    {
    	// TODO: buffer creation and binding
    }
    
    public void unbind()
    {
    	// TODO: buffer.clear();
    }
    
    public int length()
    {
    	return vertices.size();
    }
    
    public Vertex get(int index)
    {
    	return vertices.get(index);
    }

      // TODO: vertex buffer
}