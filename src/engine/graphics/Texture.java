package engine.graphics;

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

import org.lwjgl.BufferUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

import engine.graphics.graphicsUtil.VertexArray;

// TODO: refactor + cleanup so there's not so much copied code
public class Texture 
{
	// Filters
	public static final int LINEAR = GL_LINEAR;
	public static final int NEAREST = GL_NEAREST;
	public static final int LINEAR_MIPMAP_LINEAR = GL_LINEAR_MIPMAP_LINEAR;
	public static final int LINEAR_MIPMAP_NEAREST = GL_LINEAR_MIPMAP_NEAREST;
	public static final int NEAREST_MIPMAP_NEAREST = GL_NEAREST_MIPMAP_NEAREST;
	public static final int NEAREST_MIPMAP_LINEAR = GL_NEAREST_MIPMAP_LINEAR;
	
	// Wrap modes
	public static final int CLAMP = GL_CLAMP;
	public static final int CLAMP_TO_EDGE = GL_CLAMP_TO_EDGE;
	public static final int REPEAT = GL_REPEAT;
	
	public static final int DEFAULT_FILTER = NEAREST;
	public static final int DEFAULT_WRAP = REPEAT;
	
	private ArrayList<Animation> animationList = new ArrayList<Animation>();
	
	private int textureID;
	
	private int width;
	private int height;
	
	/*
	 * Default constructor
	 */
	public Texture() 
	{
		textureID = glGenTextures();
	}
	
	/*
	 * Creates an empty texture with a given width and height
	 */
	public Texture(int width, int height)
	{
		this(width, height, GL_RGB);
	}
	
	public Texture(int width, int height, int format)
	{
		textureID = glGenTextures();
		
		glBindTexture(GL_TEXTURE_2D, textureID);
		glTexImage2D(GL_TEXTURE_2D, 0, format, width, height, 0, 
				format, GL_UNSIGNED_BYTE, 0);
	
		setFilter(DEFAULT_FILTER, DEFAULT_FILTER);

		// Reset the texture now that it's bound
		unbind();
	}
	
	public Texture(int width, int height, int format, ByteBuffer data)
	{
		textureID = glGenTextures();
		
		glBindTexture(GL_TEXTURE_2D, textureID);
		glTexImage2D(GL_TEXTURE_2D, 0, format, width, height, 0, 
				format, GL_UNSIGNED_BYTE, data);
	
		setFilter(DEFAULT_FILTER, DEFAULT_FILTER);

		// Reset the texture now that it's bound
		unbind();
	}
	
	/*
	 * Copy constructor
	 */
	public Texture(Texture copy)
	{
		this.textureID = copy.getID();
		this.width = copy.getWidth();
		this.height = copy.getHeight();
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
			uploadImageData(GL_RGBA, buffer);
			
			glGenerateMipmap(GL_TEXTURE_2D);
		
			setWrap(DEFAULT_WRAP);
			setFilter(LINEAR_MIPMAP_LINEAR, NEAREST);
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
	
	/**
	 * Set the filters required for mipmaps (image scaling)
	 * 
	 * @param minFilter
	 * @param magFilter
	 */
	public void setFilter(int minFilter, int magFilter)
	{
		bind();
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, minFilter);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, magFilter);
	}
	
	/**
	 * Set up the texture ST coordinate system according to
	 * @param wrap
	 */
	public void setWrap(int wrap)
	{
		bind();
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, wrap);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, wrap);
	}
	
	/**
	 * Upload buffer data to a new GL texture
	 * 
	 * @param imageFormat GL_RGBA, GL_RGB, etc.
	 * @param data the buffer from the decoded PNG image
	 */
	public void uploadImageData(int imageFormat, ByteBuffer data)
	{
		bind();
		glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
		glPixelStorei(GL_PACK_ALIGNMENT, 1);
		
		glTexImage2D(GL_TEXTURE_2D, 0, imageFormat, width, height, 0,
				imageFormat, GL_UNSIGNED_BYTE, data);
	}
	
	/*
	 * Render this current texture.
	 * Must be called in the rendering loop after
	 * choosing a shader to use
	 */
	public void bind()
	{
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, textureID);
	}
	
	public void unbind()
	{
		glBindTexture(GL_TEXTURE_2D, 0);
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