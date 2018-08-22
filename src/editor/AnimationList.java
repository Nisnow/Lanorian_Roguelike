package editor;

import javax.swing.AbstractListModel;

import graphics.Animation;

public class AnimationList extends AbstractListModel
{
	private Animation[] animations;
	
	public AnimationList(Animation[] items)
	{
		animations = items;
	}
	
	@Override
	public Object getElementAt(int index)
	{
		return animations[index];
	}

	@Override
	public int getSize()
	{
		return animations.length;
	}

	public void update()
	{
		this.fireIntervalAdded(this,  0, getSize() - 1);;
	}
}
