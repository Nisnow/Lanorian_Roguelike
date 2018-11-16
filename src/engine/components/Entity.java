package engine.components;

import java.util.ArrayList;

public class Entity
{
	private ArrayList<Component> components;
	
	public Entity()
	{
		components = new ArrayList<Component>();
	}
	
	public Entity addComponent(Component component)
	{
		components.add(component);
		return this;
	}
	
	public <T extends Component> T getComponent(int index)
	{
		return null;
	}
}
