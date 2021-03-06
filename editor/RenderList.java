package editor;

import java.util.ArrayList;

/*
 * List of all the Drawable entities that Renderer draws
 */
public class RenderList {
	private ArrayList<Drawable> drawables = new ArrayList<Drawable>();

	/**
	 * Draws the list of objects lined up in drawables
	 * 
	 * @param p_renderer the renderer that draws everything
	 */
	public void draw(Renderer p_renderer) {
		for (Drawable drawable : drawables) {
			drawable.draw(p_renderer);
		}
	}

	/**
	 * Adds a drawable object to the list
	 * 
	 * @param p_drawable the object to add
	 */
	public void addDrawable(Drawable p_drawable) {
		drawables.add(p_drawable);
	}

	/**
	 * Removes an object from the list
	 * 
	 * @param p_drawable the object to remove
	 * @return the object that was removed
	 */
	public Drawable removeDrawable(Drawable p_drawable) {
		Drawable temp = null;

		for (int i = 0; i < drawables.size(); i++) {
			if (p_drawable.equals(drawables.get(i))) {
				temp = drawables.get(i);
				drawables.remove(i);
				break;
			}
		}
		return temp;
	}
}
