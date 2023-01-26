import com.works.work3.DeleteSymbolsClass;

public class Work4 {
	private static final String ANSI_RESET = "\u001B[0m";
	private static final String ANSI_YELLOW = "\u001B[33m";
	
	private static boolean _debug = false;
	public static void main(String[] args){
		DeleteSymbolsClass program = new DeleteSymbolsClass();
		
		ConsoleMenu _mainMenu = new ConsoleMenu();
		_mainMenu.createPage("Головне меню", new String[] {"Ввести строку", "Вивести початкову строку", "Виконати видалення символів", "Вивести оброблену строку", "Завершити програму"}, false, false ,true);
		ConsoleMenu _inputString = new ConsoleMenu();
		_inputString.createPage("Ввести строку", new String[] {"Будь ласка, введіть строку:"}, true, false ,false);
		ConsoleMenu _outputString = new ConsoleMenu();
		_outputString.createPage("Вивести початкову строку", new String[] {"Початкова строка: "}, false, true ,false);
		ConsoleMenu _programPage = new ConsoleMenu();
		_programPage.createPage("Виконати видалення символів", new String[] {"Виконуємо видалення символів..."}, false, true ,false);
		ConsoleMenu _outputResult = new ConsoleMenu();
		_outputResult.createPage("Вивести оброблену строку", new String[] {"Оброблена строка:"}, false, true ,false);
		ConsoleMenu _exitProgram = new ConsoleMenu();
		_exitProgram.createPage("Завершити програму", new String[] {"Ви впевненні, що хочете завершити програму? [y - так, n - ні]"}, true, false ,false);
		String[] _input = null;
		while(true)
		{
			_input = ConsoleMenu.AutoControlMenu();
			if (_input[2] == "true")
			{
				if (_debug == true) _debug = false;
				else _debug = true;
			}
			switch (_input[0])
			{
				case "Ввести строку":
					if (_debug == true) System.out.println(ANSI_YELLOW + "[DEBUG] " + "Запущено метод класу DeleteSymbolsClass - setString()" + ANSI_RESET);
					program.setString(_input[1]);
					if (_debug == true) System.out.println(ANSI_YELLOW + "[DEBUG] " + "Виконано метод класу DeleteSymbolsClass - setString()" + ANSI_RESET);
					break;
				case "Вивести початкову строку":
					if (_debug == true) System.out.println(ANSI_YELLOW + "[DEBUG] " + "Запущено метод класу DeleteSymbolsClass - getInitialString()" + ANSI_RESET);
					System.out.println(program.getInitialString());
					if (_debug == true) System.out.println(ANSI_YELLOW + "[DEBUG] " + "Виконано метод класу DeleteSymbolsClass - getInitialString()" + ANSI_RESET);
					break;
				case "Виконати видалення символів":
					if (_debug == true) System.out.println(ANSI_YELLOW + "[DEBUG] " + "Запущено метод класу DeleteSymbolsClass - deleteSymbols()" + ANSI_RESET);
					program.deleteSymbols();
					if (_debug == true) System.out.println(ANSI_YELLOW + "[DEBUG] " + "Виконано метод класу DeleteSymbolsClass - deleteSymbols()" + ANSI_RESET);
					System.out.println("Виконано.");
					break;
				case "Вивести оброблену строку":
					if (_debug == true) System.out.println(ANSI_YELLOW + "[DEBUG] " + "Запущено метод класу DeleteSymbolsClass - getEditedString()" + ANSI_RESET);
					System.out.println(program.getEditedString());
					if (_debug == true) System.out.println(ANSI_YELLOW + "[DEBUG] " + "Виконано метод класу DeleteSymbolsClass - getEditedString()" + ANSI_RESET);
					break;
				case "Завершити програму":
					if (_debug == true) System.out.println(ANSI_YELLOW + "[DEBUG] " + "Введено команду завершення роботи програми" + ANSI_RESET);
					if (_input[1].contains("n")) 
					{
						if (_debug == true) System.out.println(ANSI_YELLOW + "[DEBUG] " + "Завершення роботи програми не було підтверджено" + ANSI_RESET);
						break;
					}
					else if (_input[1].contains("y"))
					{
						if (_debug == true) System.out.println(ANSI_YELLOW + "[DEBUG] " + "Завершення роботи програми було підтверджено" + ANSI_RESET);
						ConsoleMenu.Terminate();
						System.exit(0);
						break;
					}
					else break;
			}
		}
	}

}