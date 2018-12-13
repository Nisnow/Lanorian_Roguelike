package engine.state;

import java.util.Stack;

public class StateMachine 
{
	private Stack<State> fsm;
	
	public StateMachine()
	{
		fsm = new Stack<State>();
	}
	
	public State popState()
	{
		return fsm.pop();
	}
	
	public void pushState(State state)
	{
		if(!getCurrentState().equals(state))
		{
			fsm.push(state);
		}
	}
	
	public State getCurrentState()
	{
		return fsm.size() > 0 ? fsm.peek() : null;
	}
}
