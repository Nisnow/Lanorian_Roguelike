package util;

/*
 * Get rekt
 */
public class FloatRect 
{
	//coordinates of the rectangle, width, and height
	public float x, y, w, h;
	
	/**
	 * Default constructor
	 */
	public FloatRect()
	{
		x = 0;
		y = 0;
		w = 0;
		h = 0;
	}
	
	/**
	 * Copy constructor
	 * @param p_copy Rect to be copied
	 */
	public FloatRect(FloatRect p_copy)
	{
		x = p_copy.x;
		y = p_copy.y;
		w = p_copy.w;
		h = p_copy.h;
	}
	
	/** 
	 * Constructor.
	 * @param p_x x-coordinate (top-left corner)
	 * @param p_y y-coordinate (top-left corner)
	 * @param p_w width
	 * @param p_h height
	 */
	public FloatRect(float p_x, float p_y, float p_w, float p_h)
	{
		x = p_x;
		y = p_y;
		w = p_w;
		h = p_h;
	}
	
	/**
	 * Checks if two Rects overlap each other
	 * @param p_rect the Rect to check overlap
	 * @return whether two Rects overlap
	 */
	public boolean overlaps(FloatRect p_rect)
	{
		return (x <= (p_rect.x + p_rect.w) && 
				x+w >= p_rect.x &&
				y <= (p_rect.y + p_rect.h) &&
				p_rect.y <= (p_rect.y + h));
	}
	
	/**
	 * Scales this Rect by a scalar
	 * @param scalar
	 */
	public void scale(int scalar)
	{
		w *= scalar;
		h *= scalar;
	}
	
	public String toString()
	{
		String formattedCoords = String.format("(%.2f, %.2f", x, y);
		return "Coordinates: (" + formattedCoords + ") width: " + w + " height: " + h;
	}
}
