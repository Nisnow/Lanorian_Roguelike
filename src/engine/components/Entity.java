package engine.components;

/*
 * A container of Components. An entity is only an integer.
 */
public class Entity
{
	private static int entityCount = 0;
	private int id;

	public Entity()
	{
		id = entityCount++;
	}

	public int getId()
	{
		return id;
	}
}
