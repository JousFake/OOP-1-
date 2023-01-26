package com.works.work3;

public class DeleteSymbolsClass {
	private StringBuilder _initialString;
	private StringBuilder _strb;
	public boolean debug = false;
	
	public void setString(String str)
	{
		_initialString = new StringBuilder(str);
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
		char _lastchar = _strb.charAt(0);
		if (debug == true) System.out.println("[DEBUG] " + "У змінну _lastchar записано значення: " + _lastchar);
		if (debug == true) System.out.println("[DEBUG] " + "Перевірка символів строки");
		for (int i = 0; i < _strb.length(); i++)
		{
			if (debug == true) System.out.println("[DEBUG] " + "Символ №" + i + ": " + _strb.charAt(i));
			if (!Character.isLetter(_strb.charAt(i)))
			{
				if (debug == true) System.out.println("[DEBUG] " + "Поточний символ - не літера");
				if (_strb.charAt(i) == '\n' || _strb.charAt(i) == '\t')
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
				else if (_strb.charAt(i) == ' ')
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
						_lastchar = _strb.charAt(i);
						if (debug == true) System.out.println("[DEBUG] " + "У змінну _lastchar записано значення: " + _lastchar);
						
					}
				}
				else
				{
					if (debug == true) System.out.println("[DEBUG] " + "Поточний символ відноситься до класу інших");
					if (_lastchar == ' ') 
					{
						if (debug == true) System.out.println("[DEBUG] " + "Попередній символ - пробіл");
						if (debug == true) System.out.println("[DEBUG] " + "Видалено символ №" + i + ": " + _strb.charAt(i));
						_strb.deleteCharAt(i);
						i--;
					}
					else 
					{
						if (debug == true) System.out.println("[DEBUG] " + "Попередній символ - не пробіл");
						if (debug == true) System.out.println("[DEBUG] " + "Заміна символа №" + i + ": " + _strb.charAt(i) + " на пробіл");
						_strb.setCharAt(i, ' ');
						_lastchar = ' ';
					}
				}
			}
			else 
			{
				if (debug == true) System.out.println("[DEBUG] " + "Поточний символ - літера");
				_lastchar = _strb.charAt(i);
				if (debug == true) System.out.println("[DEBUG] " + "У змінну _lastchar записано значення: " + _lastchar);
			}
		}
	}

}
