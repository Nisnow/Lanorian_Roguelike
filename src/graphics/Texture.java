package graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL30.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;
import graphics.graphicsUtil.VertexArray;

// TODO: refactor + cleanup so there's not so much copied code
public class Texture 
{
	private ArrayList<Animation> animationList = new ArrayList<Animation>();

	// This value is incremented whenever a texture is created
	private static int textureCount = -1;
	
	private int textureID;
	private int textureUnit;
	
	private int width;
	private int height;
	
	/*
	 * Default constructor
	 */
	public Texture() 
	{
		textureCount++;
		textureUnit = textureCount;
		textureID = glGenTextures();
	}
	
	/*
	 * Creates an empty texture with a given width and height
	 */
	public Texture(int width, int height)
	{
		textureCount++;
		textureUnit = textureCount;
		
		textureID = glGenTextures();

		this.bind();
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, 
				GL_RGB, GL_UNSIGNED_BYTE, 0);
	
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);

		// Reset the texture now that it's bound
		glBindTexture(GL_TEXTURE_2D, 0);

	}
	
	/**
	 * Constructor that also opens up a texture image
	 * from the file path argument. 
	 * 
	 * @param path the path of the texture image. Must be
	 * resources/images/ + the name of the image minus the file
	 * extension. PNG only.
	 */
	public Texture(String path)
	{
		openResource(path);
		textureCount++;
		textureUnit = textureCount;
	}
	
	/**
	 * Open an image from the resources folder. Note that it only opens png and json files
	 * @param path The name of the image without the extension
	 */
	public void openResource(String path)
	{
		try
		{
			InputStream imageStream = getClass().getClassLoader().getResourceAsStream(path + ".png");
			if (imageStream == null)
				throw new FileNotFoundException("Could not find image resource for \"" + path + ".png\"");
			openImage(imageStream);
			
			System.out.println("Loaded texture " + path + ".png");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		try
		{	
			InputStream atlasStream = getClass().getClassLoader().getResourceAsStream(path + ".json");
			if (atlasStream == null)
				throw new FileNotFoundException("Could not find atlas resource for \"" + path + ".json\"");
			openAtlas(atlasStream);	
			
			System.out.println("Loaded atlas " + path + ".json");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Open an image from the resources folder
	 */
	private void openImage(InputStream stream)
	{
		ByteBuffer buffer;
		
		try
		{
			PNGDecoder decoder = new PNGDecoder(stream);
			
			width = decoder.getWidth();
			height = decoder.getHeight();

			// Decode PNG to a buffer
			buffer = ByteBuffer.allocateDirect(width * height * VertexArray.BPF);
			decoder.decode(buffer, width * VertexArray.BPF, Format.RGBA);
			buffer.flip();
			
			// Create a new texture object in memory and bind it
			textureID = glGenTextures();
			this.bind();
			
			// Tell OpenGL that each RGB byte component is one byte
			glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
			
			// Upload texture data and generate mipmaps for better scaling
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0,
					GL_RGBA, GL_UNSIGNED_BYTE, buffer);
			glGenerateMipmap(GL_TEXTURE_2D);
		
			// Set up ST coordinate system
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
			
			// Set up mipmap parameters (scaling)
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	/**
	 * Open an image's JSON atlas file
	 * @param stream Stream to JSON file atlas
	 * @throws IOException if parsing fails
	 */
	private void openAtlas(InputStream stream) throws Exception
	{
		JsonParser parser = new JsonParser();
		
		try
		{
			// Parse the document
			Object obj = parser.parse(new InputStreamReader(stream));
			JsonObject jsonObject = (JsonObject) obj;

			// Get the array of animation data
			JsonArray atlas = (JsonArray) jsonObject.get("atlas");
			Iterator i = atlas.iterator();
			
			// Create an Animation object for each animation JSON object
			while(i.hasNext())
			{
				JsonObject data = (JsonObject) i.next();
				Animation animation = new Animation();
				animation.parse(data);
				animationList.add(animation);
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}
	
	/*
	 * Render this current texture.
	 * Must be called in the rendering loop after
	 * choosing a shader to use
	 */
	public void bind()
	{
		glActiveTexture(GL_TEXTURE0 + textureUnit);
		glBindTexture(GL_TEXTURE_2D, textureID);
	}
	
	/*
	 * Delete this texture upon closing the window with
	 * OpenGL context
	 */
	public void delete()
	{
		glDeleteTextures(0);
	}
	
	/**
	 * Used in setting sampler2D uniform-1i and 
	 * texture binding in the rendering loop
	 *
	 * @return the texture unit of this texture
	 */
	public int getTextureUnit()
	{
		return textureUnit;
	}
	
	/**
	 * @return the raw GL texture ID
	 */
	public int getID()
	{
		return textureID;
	}
	
	/**
	 * Get animation by name
	 * @param name Name of the animation
	 * @return Animation object. Null if not found.
	 */
	public Animation getAnimation(String p_name)
	{
		for(Animation a : animationList)
			if(a.getName().equals(p_name))
				return a;
		return null;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
}