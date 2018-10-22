package util;

public interface JsonFile
{
	/**
	 * Open a JSON file.
	 * Override this method to extract the correct JSON file data
	 * for your class
	 * 
	 * @param path the JSON file without the extension
	 * @throws Exception if parsing somehow fails
	 */
	public void openJson(String path) throws Exception;
}
