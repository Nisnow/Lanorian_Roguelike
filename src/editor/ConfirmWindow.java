package editor;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTextField;

import graphics.Animation;

import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/*
 * A window to create or rename animations
 */
public class ConfirmWindow
{
	private JFrame frame;
	private JTextField nameField;
	
	public ConfirmWindow()
	{
		initialize();
	}
	
	public void setVisible(boolean p_visible)
	{
		frame.setVisible(p_visible);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 290, 203);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel container = new JPanel();
		frame.getContentPane().add(container, BorderLayout.CENTER);
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		
		// ------------------------------------------------
		
		JPanel textPanel = new JPanel();
		textPanel.setBackground(Color.WHITE);
		container.add(textPanel);
		textPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		nameField = new JTextField();
		textPanel.add(nameField);
		nameField.setColumns(10);
		
		// ------------------------------------------------
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.WHITE);
		container.add(buttonPanel);
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cancel();
			}
		});
		buttonPanel.add(cancelButton);
		
		JButton confirmButton = new JButton("Confirm");
		confirmButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				confirm();
			}
		});
		buttonPanel.add(confirmButton);
	}
	
	private void confirm()
	{
		
	}
	
	private void cancel()
	{
		
	}
	
	private Animation createAnimation()
	{
		return null;
	}
}
