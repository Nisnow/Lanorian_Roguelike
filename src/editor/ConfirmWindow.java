package editor;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTextField;

import graphics.Animation;
import util.Log;

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
	private JButton currentButton;
	private JTextField nameField;
	private AnimationList animationList;
	
	public ConfirmWindow()
	{
		initialize();
	}
	
	public void setVisible(boolean p_visible)
	{
		frame.setVisible(p_visible);
	}
	
	public void setButton(JButton p_button)
	{
		currentButton = p_button;
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
				Animation animation = new Animation(nameField.getText());
				animationList.addAnimation(animation);
				frame.setVisible(false);
			}
		});
		buttonPanel.add(confirmButton);
	}
	
	public void setAnimationList(AnimationList p_list)
	{
		animationList = p_list;
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
