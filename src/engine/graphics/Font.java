package engine.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBTruetype.stbtt_GetPackedQuad;
import static org.lwjgl.stb.STBTruetype.stbtt_PackBegin;
import static org.lwjgl.stb.STBTruetype.stbtt_PackEnd;
import static org.lwjgl.stb.STBTruetype.stbtt_PackFontRange;
import static org.lwjgl.stb.STBTruetype.stbtt_PackSetOversampling;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.system.MemoryUtil.memAllocFloat;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBTTAlignedQuad;
import org.lwjgl.stb.STBTTPackContext;
import org.lwjgl.stb.STBTTPackedchar;

import engine.components.TransformComponent;
import engine.graphics.Texture;
import engine.graphics.graphicsUtil.Vertex;
import engine.util.IOUtil;

public class Font
{	
	private static final int BITMAP_W = 512;
	private static final int BITMAP_H = 512;
	
	private static final float[] scale = {
	    24.0f,
	    14.0f
	};
	
	private static final int[] sf = {
	    0, 1, 2,
	    0, 1, 2
	};
	
	// ----
	
	private final STBTTAlignedQuad q  = STBTTAlignedQuad.malloc();
	private final FloatBuffer      xb = memAllocFloat(1);
	private final FloatBuffer      yb = memAllocFloat(1);
	
	// ----
	
	private Texture fontTexture;
	
	private int ww = 1024;
	private int wh = 768;
	
	private int font_tex;
	
	private STBTTPackedchar.Buffer chardata;
	
	private int font = 3;

	private TransformComponent transform;
	
	// TODO: Do things with JSON?
	
	public Font(String filePath)
	{
		loadFont(filePath);
	}
	
	public void setTransformComponent(TransformComponent tc)
	{
		this.transform = tc;
	}
	
	public void drawText(Renderer renderer, String text)
	{
		if(transform == null)
			throw new NullPointerException("Text must have a transformComponent to be drawn!");
		
		xb.put(0, transform.getPosition().x);
        yb.put(0, transform.getPosition().y);

        chardata.position(font * 128);
		
        for(int i = 0; i < text.length(); i++)
        {
        	stbtt_GetPackedQuad(chardata, BITMAP_W, BITMAP_H, text.charAt(i), xb, yb, q, font == 0);
            // draw batch here
        	
        	Vertex[] verts = new Vertex[4];
        	verts[0] = new Vertex().setPosition(q.x0(), q.y0(), 0).setST(q.s0(), q.t0());
        	verts[1] = new Vertex().setPosition(q.x1(), q.y0(), 0).setST(q.s1(), q.t0());
        	verts[2] = new Vertex().setPosition(q.x1(), q.y1(), 0).setST(q.s1(), q.t1());
        	verts[3] = new Vertex().setPosition(q.x0(), q.y1(), 0).setST(q.s0(), q.t1());
      
    		for(int j = 0; j < 4; j++)
    		{
    			Vector4f vec = transform.getParentTransform().transform(new Vector4f(verts[j].position, 1.0f));
    			verts[j].setPosition(vec.x, vec.y, vec.z);
    		}
    		
    		Renderer.Batch batch = renderer.new Batch();
    		batch.setTexture(fontTexture);
    		batch.addVertices(verts);
    		batch.addQuad();
    		
    		renderer.getBatches().add(batch);
        }

	}
	
	private void loadFont(String filePath)
	{
		//font_tex = glGenTextures();

		chardata = STBTTPackedchar.malloc(6 * 128);
		
		try (STBTTPackContext pc = STBTTPackContext.malloc())
		{
		    ByteBuffer ttf = IOUtil.ioResourceToByteBuffer(filePath, 512 * 1024);
			
			ByteBuffer bitmap = BufferUtils.createByteBuffer(BITMAP_W * BITMAP_H);
			
			stbtt_PackBegin(pc, bitmap, BITMAP_W, BITMAP_H, 0, 1, NULL);
			
			for (int i = 0; i < 2; i++) 
			{
			    int p = (i * 3 + 0) * 128 + 32;
			    chardata.limit(p + 95);
			    chardata.position(p);
			    stbtt_PackSetOversampling(pc, 1, 1);
			    stbtt_PackFontRange(pc, ttf, 0, scale[i], 32, chardata);
			
			    p = (i * 3 + 1) * 128 + 32;
			    chardata.limit(p + 95);
			    chardata.position(p);
			    stbtt_PackSetOversampling(pc, 2, 2);
			    stbtt_PackFontRange(pc, ttf, 0, scale[i], 32, chardata);
			
			    p = (i * 3 + 2) * 128 + 32;
			    chardata.limit(p + 95);
			    chardata.position(p);
			    stbtt_PackSetOversampling(pc, 3, 1);
			    stbtt_PackFontRange(pc, ttf, 0, scale[i], 32, chardata);
			}
			
			chardata.clear();
			stbtt_PackEnd(pc);
				
			fontTexture = new Texture(BITMAP_W, BITMAP_H, GL_ALPHA, bitmap);
			fontTexture.setFilter(Texture.LINEAR, Texture.LINEAR);
		} 
		catch (IOException e) 
		{
			throw new RuntimeException(e);
		}		
	}	
}
