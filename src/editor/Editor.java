package editor;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class Editor
{
	private JFrame window;
	
	public Editor()
	{
		window = new JFrame("Atlas Editor");
		window.setSize(750, 500);
		window.setResizable(false);
		window.setVisible(true); 
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel imagePreviewer = new JPanel();

		JPanel dataInputArea = new JPanel(new BorderLayout());

		window.setLocationRelativeTo(null);
		window.add(imagePreviewer);
		window.add(dataInputArea);
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, imagePreviewer, dataInputArea);
		splitPane.setEnabled(false);
		imagePreviewer.setPreferredSize(new Dimension((int) (750*0.75), 500));
		
		window.setContentPane(splitPane);
	}
}
