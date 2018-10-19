package graphics;

import graphics.graphicsUtil.Framebuffer;
import graphics.graphicsUtil.VertexArray;

public class Renderer
{
	private Framebuffer fbo;
	private VertexArray data;
	
	// TODO: multiple batches for layering system?
	private SpriteBatch batch; 
	
	public void init()
	{
		data.init();
	}
	
	public void setFramebuffer(Framebuffer framebuffer)
	{
		this.fbo = framebuffer;
	}
	
	public Framebuffer getFramebuffer()
	{
		return this.fbo;
	}
	
	public int addQuad()
	{
		// TODO: spriteComponent of sorts to put vertices
		byte[] idx = new byte[6];
		int startIdx = data.getIndexBuffer().position();
		
		// Triangle 1
		idx[0] = (byte) (startIdx);
		idx[1] = (byte) (startIdx + 1);
		idx[2] = (byte) (startIdx + 2);
		
		// Triangle 2
		idx[3] = (byte) (startIdx + 2);
		idx[4] = (byte) (startIdx + 3);
		idx[5] = (byte) (startIdx);
	
		data.putIdx(idx);
		
		return startIdx;
	}
	
	public void render()
	{
		fbo.begin();
		batch.begin();
			// put the whole rendering loop here
		batch.end();
		fbo.end();
	}
}
