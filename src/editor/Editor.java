package editor;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import java.awt.Component;

public class Editor
{
	private JFrame frame;
	private JTextField yField;
	private JTextField xField;
	private JTextField wField;
	private JTextField hField;
	private JTextField intervalField;
	private JTextField frameField;
	
	private JButton btnNewButton, saveButton, btnPlay, newButton, renameButton, deleteButton;
	private JPanel animationPreviewer;
	private JCheckBox l00pBox, sameDimensionBox; 
	
	private int width = 1250;
	private int height = 1000;
	
	private BufferedImage spriteSheet;
	private File atlas;

	public Editor()
	{
		initialize();
	}

	private void initialize() 
	{
		frame = new JFrame("Atlas Editor");
		frame.setSize(1250, 1000);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
		frame.getContentPane().add(container);
		
		JPanel imagePreviewer = new JPanel();
		imagePreviewer.setBackground(Color.BLACK);
		imagePreviewer.setAlignmentX(Component.LEFT_ALIGNMENT);
		imagePreviewer.setPreferredSize(new Dimension((int)(width*0.8), height));
		container.add(imagePreviewer);
		imagePreviewer.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		imagePreviewer.add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		btnNewButton = new JButton("Load File");
		btnNewButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser fileChooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(".png Files", "png");
		        fileChooser.setFileFilter(filter);
		        
		        int returnValue = fileChooser.showOpenDialog(null);
		        
		        if(returnValue == JFileChooser.APPROVE_OPTION)
		        {
		        	try
		        	{
		        		String fileName = fileChooser.getSelectedFile().getPath();
		        		File file = new File(fileName);
		        		spriteSheet = ImageIO.read(file);
		        		
		        		String atlasName = fileName.substring(0, fileName.indexOf("."));
		        		atlas = new File(fileName + ".json");
		        	}
		        	catch (IOException ioe)
		        	{
		        		ioe.printStackTrace();
		        	}
		        }
			}
		});
		panel_1.add(btnNewButton);
		
		saveButton = new JButton("Save File");
		panel_1.add(saveButton);
		
		JPanel dataContainer = new JPanel();
		//frame.getContentPane().add(panel, BorderLayout.CENTER);
		dataContainer.setLayout(new BoxLayout(dataContainer, BoxLayout.Y_AXIS));
		dataContainer.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
		
		animationPreviewer = new JPanel();
		animationPreviewer.setBackground(Color.BLACK);
		dataContainer.add(animationPreviewer);
		animationPreviewer.setLayout(new BorderLayout(0, 0));
		animationPreviewer.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
		
		JPanel playPanel = new JPanel();
		FlowLayout fl_playPanel = (FlowLayout) playPanel.getLayout();
		fl_playPanel.setAlignment(FlowLayout.LEFT);
		playPanel.setBackground(Color.BLACK);
		playPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
		dataContainer.add(playPanel);
		
		btnPlay = new JButton("Play");
		playPanel.add(btnPlay);
		
		JPanel framePanel = new JPanel();
		FlowLayout fl_framePanel = (FlowLayout) framePanel.getLayout();
		fl_framePanel.setAlignment(FlowLayout.LEFT);
		framePanel.setBackground(Color.BLACK);
		framePanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
		dataContainer.add(framePanel);
		
		frameField = new JTextField();
		framePanel.add(frameField);
		frameField.setColumns(10);
		
		JLabel lblFrames = new JLabel("Frames");
		lblFrames.setForeground(Color.WHITE);
		framePanel.add(lblFrames);
		
		l00pBox = new JCheckBox("Loop");
		framePanel.add(l00pBox);
		
		JPanel intervalPanel = new JPanel();
		dataContainer.add(intervalPanel);
		intervalPanel.setBackground(Color.BLACK);
		intervalPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		intervalField = new JTextField();
		intervalPanel.add(intervalField);
		intervalField.setColumns(10);
		
		JLabel lblInterval = new JLabel("Interval");
		lblInterval.setForeground(Color.WHITE);
		intervalPanel.add(lblInterval);
		
		sameDimensionBox = new JCheckBox("= dims");
		intervalPanel.add(sameDimensionBox);
		
		JPanel dimensionPanel = new JPanel();
		dimensionPanel.setBackground(Color.BLACK);
		dataContainer.add(dimensionPanel);
		dimensionPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		dimensionPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
		
		xField = new JTextField("x");
		dimensionPanel.add(xField);
		xField.setColumns(3);
		
		yField = new JTextField("y");
		dimensionPanel.add(yField);
		yField.setColumns(3);
		
		wField = new JTextField("w");
		dimensionPanel.add(wField);
		wField.setColumns(3);
		
		hField = new JTextField("h");
		dimensionPanel.add(hField);
		hField.setColumns(3);
		
		JPanel animationPanel = new JPanel();
		animationPanel.setBackground(Color.RED);
		dataContainer.add(animationPanel);
		animationPanel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		animationPanel.add(scrollPane, BorderLayout.CENTER);
		
		String[] random = {"loop", "sldfkjs", "sdjfh"};
		
		JList animationList = new JList(random);
		animationList.setForeground(Color.WHITE);
		animationList.setBackground(Color.BLACK);
		scrollPane.setViewportView(animationList);
		
		container.add(dataContainer);
		
		JPanel filePanel = new JPanel();
		dataContainer.add(filePanel);
		filePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		newButton = new JButton("New");
		filePanel.add(newButton);
		
		renameButton = new JButton("Rename");
		filePanel.add(renameButton);
		
		deleteButton = new JButton("Delete");
		filePanel.add(deleteButton);
	}
	
	/**
	 * Displays the chosen image to the imagePreviwer
	 */
	public void displayImage()
	{
		
	}
	
	public void displayAtlasData()
	{
		
	}
	
	public JFrame getFrame()
	{
		return frame;
	}

}
