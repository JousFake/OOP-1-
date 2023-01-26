import java.util.Scanner;

import com.works.works5.UserClassString;

public class Work5 {
	private static String _str;
	private static CustomDeleteSymbolsClass object = new CustomDeleteSymbolsClass();
	
	public static void main(String[] args) {
		System.out.print("Введіть строку: ");
		_str = getString();
		object.setString(_str);
		System.out.println("Початковий текст: " + object.getInitialString());
		object.deleteSymbols();
		System.out.println("Результат: " + object.getEditedString());
	
	}
	public static String getString()
	{
		String _string;
		Scanner in = new Scanner(System.in);
		_string = in.nextLine();
		in.close();
		return _string;
	}

}


class CustomDeleteSymbolsClass {
	private UserClassString _initialString;
	private UserClassString _strb;
	public boolean debug = false;
	
	public void setString(String str)
	{
		if (str.contains("-d") || str.contains("-debug"))
		{
			String _tempString = "";
			debug = true;
			System.out.println("[DEBUG] " + "Режим відладки увімкнено!");
			for (int i = 0; i < str.length(); i++)
			{
				if (str.charAt(i) == '-')
					if (str.length() >= (i+6))
					{
						if (str.charAt(i+1) == 'd' && str.charAt(i+2) == 'e' && str.charAt(i+3) == 'b' && str.charAt(i+4) == 'u' && str.charAt(i+5) == 'g') 
						{
							i = i+6;
							continue;
						}
					}
					else if (str.length() > i+2)
					{
						if(str.charAt(i+1) == 'd' && !Character.isLetter(str.charAt(i+2)))
						{
							i = i+2;
							continue;
						}
					}
					else
						break;
				_tempString+=str.charAt(i);
			}
			str = _tempString;
		}
		_initialString = new UserClassString(str);
		if (debug == true) System.out.println("[DEBUG] " + "У змінну _initialString записано значення: " + _initialString.toString());
	}
	public String getInitialString()
	{
		return _initialString.toString(); 
	}
	public String getEditedString()
	{
		return _strb.toString();
	}
	public void deleteSymbols()
	{
		_strb = _initialString;
		if (debug == true) System.out.println("[DEBUG] " + "У змінну _strb записано значення: " + _strb.toString());
		char _lastchar = _strb.getCharAt(0);
		if (debug == true) System.out.println("[DEBUG] " + "У змінну _lastchar записано значення: " + _lastchar);
		if (debug == true) System.out.println("[DEBUG] " + "Перевірка символів строки");
		for (int i = 0; i < _strb.length(); i++)
		{
			if (debug == true) System.out.println("[DEBUG] " + "Символ №" + i + ": " + _strb.getCharAt(i));
			if (!Character.isLetter(_strb.getCharAt(i)))
			{
				if (debug == true) System.out.println("[DEBUG] " + "Поточний символ - не літера");
				if (_strb.getCharAt(i) == '\n' || _strb.getCharAt(i) == '\t')
				{
					if (debug == true) System.out.println("[DEBUG] " + "Поточний символ - символ табуляції");
					if (_lastchar == ' ') 
					{
						if (debug == true) System.out.println("[DEBUG] " + "Попередній символ - пробіл");
						_strb.deleteCharAt(i);
						if (debug == true) System.out.println("[DEBUG] " + "Видалено символ №" + i);
						i--;
					}
					else 
					{
						if (debug == true) System.out.println("[DEBUG] " + "Попередній символ - не пробіл");
						_strb.setCharAt(i, ' ');
						if (debug == true) System.out.println("[DEBUG] " + "Заміна символа №" + i + " на пробіл");
						_lastchar = ' ';
						if (debug == true) System.out.println("[DEBUG] " + "У змінну _lastchar записано значення: " + _lastchar);
					}
				}
				else if (_strb.getCharAt(i) == ' ')
				{
					if (debug == true) System.out.println("[DEBUG] " + "Поточний символ - пробіл");
					if (_lastchar == ' ') 
					{
						if (debug == true) System.out.println("[DEBUG] " + "Попередній символ - пробіл");
						_strb.deleteCharAt(i);
						if (debug == true) System.out.println("[DEBUG] " + "Видалено символ №" + i);
						i--;
					}
					else 
					{
						if (debug == true) System.out.println("[DEBUG] " + "Попередній символ - не пробіл");
						_lastchar = _strb.getCharAt(i);
						if (debug == true) System.out.println("[DEBUG] " + "У змінну _lastchar записано значення: " + _lastchar);
						
					}
				}
				else
				{
					if (debug == true) System.out.println("[DEBUG] " + "Поточний символ відноситься до класу інших");
					if (_lastchar == ' ') 
					{
						if (debug == true) System.out.println("[DEBUG] " + "Попередній символ - пробіл");
						if (debug == true) System.out.println("[DEBUG] " + "Видалено символ №" + i + ": " + _strb.getCharAt(i));
						_strb.deleteCharAt(i);
						i--;
					}
					else 
					{
						if (debug == true) System.out.println("[DEBUG] " + "Попередній символ - не пробіл");
						if (debug == true) System.out.println("[DEBUG] " + "Заміна символа №" + i + ": " + _strb.getCharAt(i) + " на пробіл");
						_strb.setCharAt(i, ' ');
						_lastchar = ' ';
					}
				}
			}
			else 
			{
				if (debug == true) System.out.println("[DEBUG] " + "Поточний символ - літера");
				_lastchar = _strb.getCharAt(i);
				if (debug == true) System.out.println("[DEBUG] " + "У змінну _lastchar записано значення: " + _lastchar);
			}
		}
	}

}