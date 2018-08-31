package editor;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class ImageManipulator
{
	private Previewable previewer;
	
	public ImageManipulator(Previewable p_previewable)
	{
		previewer = p_previewable;
	}
	
	/*
	 * Enables the user to zoom in and out of the previewer
	 * with the mouse scroll wheel.
	 */
	public MouseWheelListener addZoomFunctionality()
	{
		return new MouseWheelListener()
		{

			@Override
			public void mouseWheelMoved(MouseWheelEvent arg0)
			{
				// TODO Auto-generated method stub
			}
			
		};
	}
	
	/*
	 * Enables the user to drag the previewer's renderer
	 * around by clicking and moving the mouse.
	 */
	public MouseMotionListener addDragFunctionality()
	{
		return new MouseMotionListener()
		{
			@Override
			public void mouseDragged(MouseEvent e) 
			{
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseMoved(MouseEvent e) 
			{
				// TODO Auto-generated method stub
			}
		};
	}
	
	/*
	 * Enables the user to use the arrow keys to scroll
	 * around the previewer.
	 */
	public KeyListener addScrollFunctionality()
	{
		return new KeyListener()
		{
			@Override
			public void keyPressed(KeyEvent arg0) 
			{
				// TODO Auto-generated method stub
			}

			@Override
			public void keyReleased(KeyEvent arg0) 
			{
				// TODO Auto-generated method stub
			}

			@Override
			public void keyTyped(KeyEvent arg0) 
			{
				// TODO Auto-generated method stub
			}
		};
	}
	
	// TODO: add arrow key scrolling if mouse is inside component
}
