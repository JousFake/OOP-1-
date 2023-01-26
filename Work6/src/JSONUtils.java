import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JSONUtils {
	private Path _path;
	private String _serializedObject = "";

	public JSONUtils(String path, String name)
	{
		if (!Files.exists(Paths.get(path))) throw new java.lang.Error("Incorrect path");
		else 
		{
			String[] names = new String[] {"", ""};
			if (!name.contains(".json"))
			{
				if (name.contains("."))
				{
					names = name.split("\\.");
					names[0] += ".json";
					if (path.charAt(path.length()-1) == '\\' || path.charAt(path.length()-1) == '/')
					{
						_path = Paths.get(path+names[0]);
					}
					else _path = Paths.get(path + "\\" + names[0]);
				}
				else
				{
					names[0] = name+".json";
					if (path.charAt(path.length()-1) == '\\' || path.charAt(path.length()-1) == '/')
					{
						_path = Paths.get(path+names[0]);
					}
					else _path = Paths.get(path + "\\" + names[0]);
				}
			}
			else
			{
				if (path.charAt(path.length()-1) == '\\' || path.charAt(path.length()-1) == '/')
				{
					_path = Paths.get(path+name);
				}
				else _path = Paths.get(path + "\\" + name);
			}
		}
	}
	public JSONUtils(String path)
	{
		int lastSlesh = path.lastIndexOf("/");
		int lastBackSlesh = path.lastIndexOf("\\");
		String purePath = "";
		String fileName = "";
		if (lastSlesh > lastBackSlesh) 
		{
			purePath = path.substring(0, lastSlesh);
			fileName = path.substring(lastSlesh, path.length()-1);
		}
		else 
		{
			purePath = path.substring(0, lastBackSlesh);
			fileName = path.substring(lastBackSlesh, path.length()-1);
		}
		if (!Files.exists(Paths.get(purePath))) throw new java.lang.Error("Incorrect path");
		if (!fileName.endsWith("\\.json"))
		{
			path+=".json";
		}
		_path = Paths.get(path);
	}
	
	public void readData()
	{
		System.out.println("Name: " + _path.getFileName());
		System.out.println("Path: " + _path.toString());
	}
	public boolean Writer(String str)
	{
		try {
			FileWriter writer = new FileWriter(_path.toString());
			writer.write(str);
			writer.close();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}
	public String Reader() throws IOException
	{
		//StringBuilder resultStringBuilder = new StringBuilder();
		String data = "";
		String line = "";
		BufferedReader reader = new BufferedReader(new FileReader(_path.toString()));
		line = reader.readLine();
		while (line != null)
		{
			data+=line + "\n";
			line = reader.readLine();
		}
		_serializedObject = data;
		reader.close();
		return _serializedObject;
	}

}
