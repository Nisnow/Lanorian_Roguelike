package engine.util;

public class Pair<T1, T2>
{
	public final T1 first;
	public final T2 second;
	
	/*
	 * Construct a pair that relates two similar objects
	 */
	public Pair(T1 p_first, T2 p_second)
	{
		first = p_first;
		second = p_second;
	}
}
