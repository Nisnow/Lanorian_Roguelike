package editor;

import java.util.ArrayList;

import javax.swing.AbstractListModel;

public class AnimationList extends AbstractListModel
{
	private ArrayList<Animation> animations;
	
	public AnimationList()
	{
		animations = new ArrayList<Animation>();
	}
	
	public AnimationList(ArrayList<Animation> items)
	{
		animations = items;
	}
	
	public void addAnimation(Animation p_animation)
	{
		animations.add(p_animation);
		update();
	}
	
	public void removeAnimation(Animation p_animation)
	{
		animations.remove(p_animation);
		this.fireContentsChanged(this, 0, getSize()-1);
	}
	
	@Override
	public Animation getElementAt(int index)
	{
		return animations.get(index);
	}

	@Override
	public int getSize()
	{
		return animations.size();
	}

	public void update()
	{
		this.fireContentsChanged(this,  0, getSize());
	}
}
