package engine.components;

import java.util.HashMap;

/*
 * This class maps a Component T with all the entities that have it
 * for easy access
 */
public final class ComponentMapper<T extends Component>
{
	public final static int MAX_ENTITIES = 100;
	
	private HashMap<Entity, T> mapper;
	
	public ComponentMapper()
	{
		mapper = new HashMap<>();
	}

	/*
	 * Attach a component to an entity. This method maps that component
	 * to that entity.
	 */
	public void add(Entity entity, T component)
	{
		if(mapper.size() == MAX_ENTITIES)
			return;
		
		mapper.put(entity, component);
	}
	
	/*
	 * Retrieve a component from an entity
	 */
	public T getFrom(Entity entity)
	{
		return mapper.get(entity);
	}
	
	public int getEntityCount()
	{
		return mapper.size();
	}
	
	public HashMap<Entity, T> getEntites()
	{
		return mapper;
	}
}
