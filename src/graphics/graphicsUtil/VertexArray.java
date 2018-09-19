package graphics.graphicsUtil;

public class VertexArray
{
    // Vertex data
    private float[] position = new float[] {0f, 0f, 0f, 1f};
    private float[] color 	 = new float[] {1f, 1f, 1f, 1f};
    private float[] st 		 = new float[] {0f, 0f};
     
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
     
    // The amount of elements that a vertex has
    public static final int ELEMENT_COUNT = POSITION_ELEMENT_COUNT + 
            COLOR_ELEMENT_COUNT + ST_ELEMENT_COUNT;    
    // The size of a vertex in bytes, like in C/C++: sizeof(Vertex)
    public static final int STRIDE = POSITION_BYTES + COLOR_BYTES + 
            ST_BYTES;
     
    public void setPosition(float x, float y, float z)
    {
        this.setPosition(x, y, z, 1f);
    }
     
    public void setColor(float r, float g, float b)
    {
        this.setColor(r, g, b, 1f);
    }
     
    public void setST(float s, float t)
    {
        this.st = new float[] {s, t};
    }
     
    public void setPosition(float x, float y, float z, float w)
    {
        this.position = new float[] {x, y, z, w};
    }
     
    public void setColor(float r, float g, float b, float a)
    {
        this.color = new float[] {r, g, b, 1f};
    }
     
    public float[] getElements()
    {
        float[] out = new float[VertexArray.ELEMENT_COUNT];
        int i = 0;
         
        // Insert XYZW elements
        out[i++] = this.position[0];
        out[i++] = this.position[1];
        out[i++] = this.position[2];
        out[i++] = this.position[3];
        
        // Insert RGBA elements
        out[i++] = this.color[0];
        out[i++] = this.color[1];
        out[i++] = this.color[2];
        out[i++] = this.color[3];
        
        // Insert ST elements
        out[i++] = this.st[0];
        out[i++] = this.st[1];
         
        return out;
    }
     
    public float[] getPosition() 
    {
        return new float[] {this.position[0], this.position[1], this.position[2], this.position[3]};
    }
     
    public float[] getColor() 
    {
        return new float[] {this.color[0], this.color[1], this.color[2], this.color[3]};
    }
     
    public float[] getST() 
    {
        return new float[] {this.st[0], this.st[1]};
    }
}