package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileLoader
{
	/*
	 * Default constructor
	 */
	public FileLoader() {}
	
	public static  String loadFile(String p_path)
	{
		StringBuilder result = new StringBuilder();
		
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(p_path));
			String buffer = "";
			while ((buffer = reader.readLine()) != null)
			{
				result.append(buffer + '\n');
			}
			reader.close();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return result.toString();
	}
}
