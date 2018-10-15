package graphics.graphicsUtil;

import org.joml.Vector4f;

public class Color 
{
	// BEGIN COLOR DECLARATION
	public static final Color TRANSPARENT = new Color(0.0f, 0.0f, 0.0f, 0.0f);
	public static final Color WHITE = new Color(1.0f, 1.0f, 1.0f, 1.0f);
	public static final Color YELLOW = new Color(1.0f, 1.0f, 0, 1.0f);
	public static final Color RED = new Color(1.0f, 0, 0, 1.0f);
	public static final Color BLUE = new Color(0, 0, 1.0f, 1.0f);
	public static final Color GREEN = new Color(0, 1.0f, 0, 1.0f);
	public static final Color BLACK = new Color(0, 0, 0, 1.0f);
	public static final Color GRAY = new Color(0.5f, 0.5f, 0.5f, 1.0f);
	public static final Color CYAN = new Color(0, 1.0f, 1.0f, 1.0f);
	public static final Color DARK_GRAY = new Color(0.3f, 0.3f, 0.3f, 1.0f);
	public static final Color LIGHT_GRAY = new Color(0.7f, 0.7f, 0.7f, 1.0f);
	public final static Color PINK = new Color(255, 175, 175, 255);
	public final static Color ORANGE = new Color(255, 200, 0, 255);
	public final static Color MAGENTA = new Color(255, 0, 255, 255);
	// END COLOR DECLARATION
	
	// Red, green, blue, and alpha values
	public float r;
	public float g;
	public float b;
	public float a;

	/*
	 * Default constructor to create a white color
	 */
	public Color()
	{
		this(Color.WHITE);
	}
	
	/*
	 * Copy constructor
	 */
	public Color(Color copy)
	{
		this(copy.r, copy.g, copy.b, copy.a);
	}
	
	/*
	 * Create a  color with RGBA values 
	 * ranging from 0.0f--1.0f
	 */
	public Color(float r, float g, float b, float a)
	{
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	/*
	 * Create a color with RGB values
	 * ranging from 0.0f--1.0f.
	 * This color is completely opaque with an alpha
	 * value of 1.0f
	 */
	public Color(float r, float g, float b)
	{
		this(r, g, b, 1.0f);
	}	
	
	/*
	 * Create a color with RGBA values
	 * ranging from 0--255
	 */
	public Color(int r, int g, int b, int a)
	{
		this(r / 255.0f, g / 255.0f, b / 255.0f, a / 255.0f);
	}
	
	/*
	 * Create a color with RGB values
	 * ranging from 0--255
	 * This color is completely opaque with an alpha
	 * value of 255
	 */
	public Color(int r, int g, int b)
	{
		this(r / 255.0f, g / 255.0f, b / 255.0f, 255.0f);
	}

	public int red()
	{
		return (int) (r * 255);
	}

	public int green()
	{
		return (int) (g * 255);
	}

	public int blue() 
	{
		return (int) (b * 255);
	}
	
	public int alpha() 
	{
		return (int) (a * 255);
	}
	
	public void set(Color color) 
	{
		set(color.r, color.g, color.b, color.a);
	}
	
	public void set(float r, float g, float b, float a) 
	{
		set(r, g, b);
		this.a = a;
	}
	
	public void set(float r, float g, float b) 
	{
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	/**
	 * @return this color represented as a vector4f
	 */
	public Vector4f getColorAsVector()
	{
		return new Vector4f(r, g, b, a);
	}
	
	@Override
	public String toString()
	{
		return "("+r+", "+g+", "+b+", "+a+")";
	}
}
