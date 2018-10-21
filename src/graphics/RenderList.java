package graphics;

import java.util.ArrayList;

public class RenderList
{
	private ArrayList<Renderable> renderList = new ArrayList<Renderable>();

	public void draw(Renderer renderer)
	{
		for(Renderable r : renderList)
		{
			r.draw(renderer);
		}
	}
	
	public void add(Renderable object)
	{
		renderList.add(object);
	}
}
