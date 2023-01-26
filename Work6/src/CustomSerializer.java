import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CustomSerializer {
	private String _serializedObject = "";
	private int _deserializeLineCounter = 2;
	private Object obj1;
	
	private void importAll(Object object)
	{
		Package objectPackage = object.getClass().getPackage();
        String packageName = objectPackage.getName();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            Enumeration<URL> resources = classLoader.getResources(packageName.replace(".","/"));
            while(resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                File resourceFile = new File(resource.getFile());
                if(resourceFile.isFile()){
                    String className = resourceFile.getName().replace(".class","");
                    Class.forName(packageName + "." + className);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
	}
	
	public String GetSerializedString()
	{
		return _serializedObject;
	}
	public boolean Serialize(Object object) throws IllegalArgumentException, IllegalAccessException
	{
		importAll(object);
		String maket = "";
		String[] names = object.getClass().toString().split("\\.");
		names[names.length-1] = names[names.length-1].strip();
		String className = object.getClass().toString().substring(object.getClass().toString().lastIndexOf(" ")+1).replace(";", "");
		_serializedObject = "\"" + className + "\"\n";
		maket+="\t";
		this._serializedObject += "{\n";
		this._serializedObject += SerializeObject(maket, object, null);
		this._serializedObject += "}";
		return true;
	} 
	private String SerializeObject(String maket, Object object, Field field) throws IllegalArgumentException, IllegalAccessException
	{
		if (object == null && field == null) return "";
		String _serializedString = "";
		String typeName;
		if (object != null) typeName = object.getClass().getName().toString().replace(";", "");
		else 
		{
			typeName = field.getType().getName().toString().replace(";", "");
		}
		if ((object != null && object.getClass().isArray()) || (field != null && field.getClass().isArray()))
		{
			if(Array.getLength(object) == 0) return _serializedString;
			_serializedString+=maket+"["+typeName+"]";
			if (Array.get(object, 0).getClass().getName().startsWith("java.lang"))
			{
				if (field != null) _serializedString+="\"" + field.getName() + "\" :\n";
				else _serializedString+="\"" + "!value" + "\" :\n";
				maket+="\t";
				for (int i = 0; i < Array.getLength(object); i++)
				{
					Object element = Array.get(object, i);
					if (i == Array.getLength(object)-1)
					{
						_serializedString+=maket+"\""+element.toString()+"\".\n";
					}
					else 
					{
						_serializedString+=maket+"\""+element.toString()+"\";\n";
					}
				}
				return _serializedString; 
			}
			else
			{
				_serializedString+="\"value\" : \n";
				for (int i = 0; i < Array.getLength(object); i++)
				{
					Object element = Array.get(object, i);
					_serializedString+=SerializeObject((maket+"\t"), element, null);
				}
			}
		}
		else if ((object != null && object.getClass().getName().startsWith("java.lang")) || (field != null && field.getType().getName().startsWith("java.lang")))
		{
			_serializedString+=maket+"["+typeName+"]";
			if (field != null)
			{
				typeName = field.getName().toString();
				_serializedString+="\"" + typeName + "\"" + " : " + "\"" + object + "\",\n";
			}
			else _serializedString+="\"value\" : \"" + object.toString() + "\"\n";
			return _serializedString;
		}
		Field[] fields = object.getClass().getDeclaredFields();
		maket+="\t";
		for (Field fieldt : fields)
		{
			if (Modifier.isFinal(fieldt.getModifiers())) continue;
			if (!fieldt.trySetAccessible()) continue;
			if(fieldt.get(object) != null && (int)Arrays.stream(fieldt.get(object).getClass().getDeclaredFields()).filter(fieldd -> fieldd.trySetAccessible()).count() > 1 && (!fieldt.get(object).getClass().getName().toString().startsWith("java.lang."))) 
			{
				String thisclassName = fieldt.get(object).getClass().getName().toString();
				_serializedString+=maket+"["+thisclassName+"]\"" + fieldt.getName()  + "\" : {\n";
				_serializedString+=SerializeObject(maket, fieldt.get(object), fieldt);
				_serializedString+=maket+"}\n";
			}
			else _serializedString+=SerializeObject(maket, fieldt.get(object), fieldt);
		}
		return _serializedString;
	}
	public Object Deserialize() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		String[] lines = _serializedObject.split("\n");
		if (lines[0].startsWith("\"["))
		{
			_deserializeLineCounter=3;
			String className =  lines[0].replace("\"", "").replace("]", "").strip();
			String pureClassName = className.replaceAll("(^\\[+)", "");
			int dimension = className.length()-(pureClassName.length()+1);
			if (dimension > className.length()) dimension = 0;
			pureClassName = className.substring(dimension);
			if (pureClassName.startsWith("L")) pureClassName = pureClassName.substring(1);
			else if (pureClassName.startsWith("[L") && dimension == 0) pureClassName = pureClassName.substring(2);
			obj1 = DeserializeArray(lines, pureClassName);
			return obj1;
		}
		else if (lines[0].startsWith("\"java.lang"))
		{
			String variableClassPatterString = "((?<=\\[).+(?=\\]))";
			String variableNamePatterString = "((?<=\\]\").+?(?=\"\\u0020:))";
			String variableValuePatterString = "(?<=:\\u0020\").+?(?=\")";
			
			Pattern variableClassPatter = Pattern.compile(variableClassPatterString);
			Pattern variableNamePatter = Pattern.compile(variableNamePatterString);
			Pattern variableValuePatter = Pattern.compile(variableValuePatterString);
			
			Matcher matcher = variableClassPatter.matcher(lines[2]);
			if (matcher.find())
			{
				String variableClass = matcher.group(0);
				matcher = variableNamePatter.matcher(lines[2]);
				if (matcher.find())
				{
					matcher = variableValuePatter.matcher(lines[2]);
					if (matcher.find())
					{
						String variableValue = matcher.group(0);
						Class<?> clazz = Class.forName(variableClass);
						Constructor<?> ctor;
						try {
							ctor = clazz.getConstructor(String.class);
							obj1 = ctor.newInstance(variableValue);;
							return obj1;
						} catch (NoSuchMethodException e) {
							Method valueOfMethod = clazz.getMethod("valueOf", String.class);
							obj1 = valueOfMethod.invoke(null, variableValue);
							return obj1;
						}
					}
				}
			}
		}
		Class<?> clazz = Class.forName(lines[0].replace("\"", "").strip());
		Constructor<?> ctor = clazz.getConstructor();
		obj1 = ctor.newInstance();
		Field[] fields = obj1.getClass().getDeclaredFields();
		for (Field field : fields)
		{
			if (!field.trySetAccessible()) continue;
			if (Modifier.isFinal(field.getModifiers())) continue;
			field.set(obj1, DeserializeObject(lines, field));
			_deserializeLineCounter = 2;
		}
		return obj1;
	}
	private Object DeserializeObject (String[] lines, Field variableField) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		Object object = null;
		
		String classNameStringPattern = "((?<=\\[)(?<!\\[L).+(?=\\]))";
		String arrayStringPattern = "((?<=\\[\\[L).+?(?=\\]))";
		String variableValueStringPattern = "((?<=:\\u0020\").+?(?=\"))";
		String variableIsObjectStringPattern = "(\s:\s\\{)";
		String variableNameStringPattern = "((?<=\\[.+\\]\").+?(?=\"))";
		
		Pattern classNamePattern = Pattern.compile(classNameStringPattern);
		Pattern arrayPattern = Pattern.compile(arrayStringPattern);
		Pattern variableValuePattern = Pattern.compile(variableValueStringPattern);
		Pattern variableIsObjectPattern = Pattern.compile(variableIsObjectStringPattern);
		Pattern variableNamePattern = Pattern.compile(variableNameStringPattern);
		for (int i = _deserializeLineCounter; i < lines.length; i++, _deserializeLineCounter++)
		{
			Matcher matcher = arrayPattern.matcher(lines[i]);
			if (matcher.find())
			{
				String className = matcher.group(0);
				matcher = variableNamePattern.matcher(lines[i]);
				if(matcher.find())
				{
					if (!variableField.getName().contains(matcher.group(0))) continue;
					String pureClassName = className.replaceAll("(^\\[+)", className);
					int dimension = className.length()-(pureClassName.length());
					if (dimension > className.length()) dimension = 0;
					pureClassName = className.substring(dimension);
					_deserializeLineCounter++;
					object = DeserializeArray(lines, pureClassName);
					return object;
				}
			}
			matcher = classNamePattern.matcher(lines[i]);
			if (matcher.find())
			{
				String className = matcher.group(0);
				matcher = variableNamePattern.matcher(lines[i]);
				if (matcher.find())
				{
					String variableName = matcher.group(0);
					if (!variableName.contains(variableField.getName())) continue;
					matcher = variableValuePattern.matcher(lines[i]);
					if (matcher.find())
					{
						String variableValue = matcher.group(0);
						Class<?> clazz = Class.forName(className);
						Constructor<?> ctor;
						try {
							ctor = clazz.getConstructor(String.class);
							object = ctor.newInstance(variableValue);;
							return object;
						} catch (NoSuchMethodException e) {
							Method valueOfMethod = clazz.getMethod("valueOf", String.class);
							object = valueOfMethod.invoke(null, variableValue);
							return object;
						}
					}
					matcher = variableIsObjectPattern.matcher(lines[i]);
					if (matcher.find())
					{
						if (className.contains(obj1.getClass().toString())) return null;
						Class<?> clazz = Class.forName(className);
						Constructor<?> ctor = clazz.getConstructor();
						object = ctor.newInstance();
						Field[] fields = object.getClass().getDeclaredFields();
						_deserializeLineCounter++;
						for (Field field : fields)
						{
							if (!field.trySetAccessible()) continue;
							field.set(object, DeserializeObject(lines, field));
						}
						return object;
					}
				}
			}
			
		}
		return object;
	}
	private Object DeserializeArray(String[] lines, String className) throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		List<Object> returnArray = new ArrayList<>();
		boolean isEnd = false;
		
		String arrayStringPattern = "((?<=\\[\\[L?+).+(?=\\]))";
		String elementValueStringPatter = "((?<=^[\\s]+\").+(?=\"))";
		String objectStringPatter = "((?<=^[\\s]+\\[).+(?=\\]\s:\s\\{))";
		
		
		Pattern arrayPattern = Pattern.compile(arrayStringPattern);
		Pattern elementValuePattern = Pattern.compile(elementValueStringPatter);
		Pattern objectPatter = Pattern.compile(objectStringPatter);
		Matcher matcher;
		for (int i = _deserializeLineCounter; i < lines.length; _deserializeLineCounter++, i++)
		{
			if (lines[_deserializeLineCounter].strip().endsWith(".") || lines[_deserializeLineCounter].strip().contains("}"))
			{
				if (lines[_deserializeLineCounter].strip().contains("}"))
				{
					String arraydimension = className.replaceAll("(^\\[+)", "");
					int dimension = className.length() - (arraydimension.length()+1);
					if (dimension > className.length() || dimension < 0) dimension = 0;
					String finalClassName = className.substring(dimension);
					if (finalClassName.startsWith("L")) finalClassName = finalClassName.substring(1);
					Class<?> clazz;
					if (finalClassName.startsWith("[")) clazz = Class.forName(finalClassName+";");
					else clazz = Class.forName(finalClassName);
					Object array = convertToArray(returnArray, clazz);
					return array;
				}
				isEnd = true;
			}
			matcher = arrayPattern.matcher(lines[_deserializeLineCounter]);
			if (matcher.find())
			{
				_deserializeLineCounter++;
				String arrayClassName = matcher.group(0).strip();
				returnArray.add(DeserializeArray(lines,arrayClassName));
				_deserializeLineCounter--;
				continue;
			}
			matcher = elementValuePattern.matcher(lines[_deserializeLineCounter]);
			if (matcher.find())
			{
				Class<?> clazz = Class.forName(className);
				Constructor<?> ctor;
				try {
					ctor = clazz.getConstructor(String.class);
					Object obj = ctor.newInstance(matcher.group(0));
					returnArray.add(obj);
				} catch (NoSuchMethodException e) {
					Method valueOfMethod = clazz.getMethod("valueOf", String.class);
					Object obj = valueOfMethod.invoke(null, matcher.group(0));
					returnArray.add(obj);
				}
				if (isEnd == true)
				{
					clazz = Class.forName(className);
					Object array = Array.newInstance(clazz, returnArray.size());
					System.arraycopy(returnArray.toArray(), 0, array, 0, returnArray.size());
					_deserializeLineCounter++;
					return array;
				}
				continue;
			}
			matcher = objectPatter.matcher(lines[_deserializeLineCounter]);
			if (matcher.find())
			{
				Class<?> clazz = Class.forName(matcher.group(0));
				Constructor<?> ctor = clazz.getConstructor();
				Object obj = ctor.newInstance();
				Field[] fields = obj.getClass().getDeclaredFields();
				for (Field field : fields)
				{
					field.set(obj,DeserializeObject(lines, field));
				}
				returnArray.add(obj);
				if (isEnd == true)
				{
					clazz = Class.forName(className);
					Object array = Array.newInstance(clazz, returnArray.size());
					System.arraycopy(returnArray.toArray(), 0, array, 0, returnArray.size());
					return array;
				}
				continue;
			}
			
		}
		Class<?> clazz = Class.forName(className+";");
		Object array = convertToArray(returnArray, clazz);
		return array;
	}
	private Object convertToArray(List<?> list, Class<?> componentType)
	{
		int size = list.size();
		Object array = Array.newInstance(componentType, size);
		for (int i = 0; i < size; i++)
		{
			Object element = list.get(i);
			if (element instanceof Object[] || element.getClass().isArray())
			{
				Array.set(array, i, convertToArray(element, componentType.getComponentType()));
			}
			else Array.set(array, i, element);
		}
		return array;
	}
	private Object convertToArray(Object object, Class<?> componentType)
	{
		int size = Array.getLength(object);
		Object array = Array.newInstance(componentType, size);
		for (int i = 0; i < size; i++)
		{
			Object element = Array.get(object, i);
			if (element instanceof Object[] || element.getClass().isArray())
			{
				Array.set(array, i, convertToArray(element, componentType.getComponentType()));
			}
			else Array.set(array, i, element);
		}
		return array;
	}
	public void writeToFile(String path)
	{
		JSONUtils file = new JSONUtils(path);
		file.Writer(_serializedObject);
	}
	public void readFromFile(String path)
	{
		JSONUtils file = new JSONUtils(path);
		try {
			_serializedObject = file.Reader();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
