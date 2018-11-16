package engine.components;

import java.util.HashMap;

/*
 * This class maps a Component with all the entities that have it
 * for easy access
 */
public final class ComponentMapper<T extends Component>
{
	private HashMap<Entity, Class<? extends Component>> activeComponents;
	
	private static int componentIndex = 0;
	private int id;
	
	public ComponentMapper(Class<T> component)
	{
		id = componentIndex++;
	}
	
	public T getEntity(Entity entity)
	{
		return entity.getComponent(id);
	}
}
