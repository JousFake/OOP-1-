import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleMenu {
	private static final String ANSI_RESET = "\u001B[0m";
	private static final String ANSI_YELLOW = "\u001B[33m";
	private static final String ANSI_GREEN = "\u001B[32m";
	private static final String ANSI_RED = "\u001B[31m";
	private static final String	HIGH_INTENSITY		= "\u001B[1m";
	private static final String ANSI_ORANGE = HIGH_INTENSITY + ANSI_YELLOW;
	
	private String _pageName;
	private List<String> _pageLines;
	private static boolean _debug = false;
	private static boolean _isDebugModeChanged = false;
	private boolean _isInput = false;
	private boolean _isMainPage = false;
	private boolean _isAction = false;
	private static ConsoleMenu _mainPage = null;
	private static List<ConsoleMenu> _history = new ArrayList<ConsoleMenu>();
	private static List<String> _memory = new ArrayList<String>();
	private static List<ConsoleMenu> _pagesList = new ArrayList<ConsoleMenu>();
	private static Scanner in = new Scanner(System.in);
	private static boolean _isLastActive = false;
	public static void main(String[] args) {
		for (int i = 0; i < args.length; i++)
		{
			if (args[i] == "-d" || args[i] == "-debug") _debug = true;
		}
	}
	
	public static void ClearConsole()
	{
		if (_debug == true) System.out.println(ANSI_YELLOW + "[DEBUG] " + "Запущено очистку консолі" + ANSI_RESET);
		System.out.println(System.lineSeparator().repeat(100));
		System.out.print("\033[H\033[2J");
		if (_debug == true) System.out.println(ANSI_YELLOW + "[DEBUG] " + "Консоль очищено" + ANSI_RESET);
    }
	public int createPage(String pagename, String[] lines, boolean isinput, boolean isAction, boolean isMainPage)
	{
		if (lines.length == 0) return 1;
		this._pageLines = new ArrayList<String>();
		this._pageName = pagename;
		for (int i = 0; i < lines.length; i++)
		{
			this._pageLines.add(lines[i]);
		}
		this._isInput = isinput;
		if (isMainPage == true) 
		{
			this._isMainPage = true;
			//_mainPage = this;
		}
		else this._isMainPage = false;
		this._isAction = isAction;
		_pagesList.add(this);
		_memory.add(_pagesList.get(_pagesList.size()-1)._pageName);
		return 0;
	}
	public int createPage(String pagename, String[] lines, boolean isinput, boolean isAction)
	{
		if (lines.length == 0) return 1;
		this._pageLines = new ArrayList<String>();
		this._pageName = pagename;
		for (int i = 0; i < lines.length; i++)
		{
			this._pageLines.add(lines[i]);
		}
		this._isInput = isinput;
		this._isAction = isAction;
		_pagesList.add(this);
		return 0;
	}
	public String showPage()
	{
		ClearConsole();
		if (_debug == true) System.out.println(ANSI_ORANGE + "[DEBUG] " + "Увага! Увімкнено режим відладки!" + ANSI_RESET);
		String _userInput = null;
		if (this._pageLines == null || this._pageName == null) 
		{
			if (_debug == true) System.out.println(ANSI_YELLOW + "[DEBUG] " + "Не задані рядки сторінки або назва сторінки" + ANSI_RESET);
			return "Помилка. Сторінка не налаштована!";
		}
		System.out.println(this._pageName);
		for (int i = 0; i < this._pageLines.size(); i++)
		{
			System.out.println("[" + i + "] " + this._pageLines.get(i));
		}
		if (this._isAction == false && this._isInput == true) 
		{
			if (_debug == true) System.out.println(ANSI_YELLOW + "[DEBUG] " + "Ввод змінної _userInput" + ANSI_RESET);
			_userInput = in.nextLine();
		}
		return _userInput;
	}
	public static String[] AutoControlMenu()
	{
		if (_pagesList.size() == 0) return new String[] {"-1", "Помилка! Не вказані сторінки для відображення.", "false"};
		//if (_mainPage == null) return new String[] {"-2", "Помилка! Головна сторінка не задана.", "false"};
		String _userInput = null;
		if (_history.size() == 0) 
		{
			if (_debug == true) System.out.println(ANSI_YELLOW + "[DEBUG] " + "Історія користування меню порожня" + ANSI_RESET);
			_history.add(_pagesList.get(_memory.indexOf(_mainPage._pageName)));
			if (_debug == true) System.out.println(ANSI_YELLOW + "[DEBUG] " + "До масиву _history додано значення: " + _history.get(_history.size()-1) + ANSI_RESET);
		}
		//_userInput = _mainPage.showPage();
		while (_userInput == null ? true : !_userInput.contains("-exit"))
		{
			if (_isLastActive == true)
			{
				if (_debug == true) System.out.println(ANSI_YELLOW + "[DEBUG] " + "Остання сторінка була позначена як 'потребує додаткових розрахунків'" + ANSI_RESET);
				if (_debug == true) System.out.println(ANSI_YELLOW + "[DEBUG] " + "Очікування Enter для продовження роботи програми" + ANSI_RESET);
				in.nextLine();
				if (_debug == true) System.out.println(ANSI_YELLOW + "[DEBUG] " + "З масиву _history видалено значення: " + _history.get(_history.size()-1) + ANSI_RESET);
				_history.remove(_history.get(_history.size()-1));
				_isLastActive = false;
				if (_debug == true) System.out.println(ANSI_YELLOW + "[DEBUG] " + "Змінна _isLastActive змінена на: " + _isLastActive + ANSI_RESET);
				continue;
			}
			_userInput = _history.get(_history.size()-1).showPage();
			if (_userInput == "Помилка. Сторінка не налаштована!") return new String[] {"-4", "Помилка. Сторінка не налаштована!", "false"};
			if (_userInput != null && _userInput.contains("-exit"))
			{
				if (_debug == true) System.out.println(ANSI_YELLOW + "[DEBUG] " + "Введено команду завершення роботи програми" + ANSI_RESET);
				System.exit(1);
				break;
			}
			if (_userInput != null && _userInput.contains("-help"))
			{
				ClearConsole();
				System.out.println(ANSI_GREEN + "Справка:");
				System.out.println(HIGH_INTENSITY + "Програму розробив Курапов Дмитро Ігорович" + ANSI_RESET);
				System.out.println(ANSI_GREEN + "-help - допомога");
				System.out.println("-d або -debuf - увімкнути/вимкнути режим відладки");
				System.out.println("-exit - термінове завершення роботи програми");
				System.out.println(HIGH_INTENSITY + "Для повернення назад, натисніть Enter" + ANSI_RESET);
				in.nextLine();
				continue;
			}
			if (_debug == true) System.out.println(ANSI_YELLOW + "[DEBUG] " + "У змінну _userInput записано значення: " + _userInput + ANSI_RESET);
			if (_userInput != null && (_userInput.contains("-d") || _userInput.contains("-debug"))) 
			{
				if (_debug == false) _debug = true;
				else  _debug = true;
				_isDebugModeChanged = true;
				_userInput = _userInput.replaceAll("-d", "");
				_userInput = _userInput.replaceAll("-debug", "");
				_userInput = _userInput.strip();
			}
			if (_history.get(_history.size()-1)._isAction == true)
			{
				if (_debug == true) System.out.println(ANSI_YELLOW + "[DEBUG] " + "Поточна сторінка позначена як 'потребує додаткових розрахунків'" + ANSI_RESET);
				_isLastActive = true;
				if (_debug == true) System.out.println(ANSI_YELLOW + "[DEBUG] " + "Змінна _isLastActive змінена на: " + _isLastActive + ANSI_RESET);
				if (_debug == true) System.out.println(ANSI_YELLOW + "[DEBUG] " + "Передача управління головній програмі" + ANSI_RESET);
				if (_isDebugModeChanged == true)
				{
					_isDebugModeChanged = false;
					return new String[] {_history.get(_history.size()-1)._pageName,_userInput, "true"};
				}
				else return new String[] {_history.get(_history.size()-1)._pageName,_userInput, "false"};
			}
			else if (_userInput != null && (_history.get(_history.size()-1)._isInput == true && _userInput != ""))
			{
				String _tempName = _history.get(_history.size()-1)._pageName;
				if (_debug == true) System.out.println(ANSI_YELLOW + "[DEBUG] " + "У змінну _tempName записано значення: " + _tempName + ANSI_RESET);
				_history.remove(_history.get(_history.size()-1));
				if (_debug == true) System.out.println(ANSI_YELLOW + "[DEBUG] " + "З масиву _history було видалено значення: " + _tempName + ANSI_RESET);
				if (_debug == true) System.out.println(ANSI_YELLOW + "[DEBUG] " + "Передача управління головній програмі" + ANSI_RESET);
				if (_isDebugModeChanged == true )
				{
					_isDebugModeChanged = false;
					return new String[] {_tempName,_userInput, "true"};
				}
				else return new String[] {_tempName,_userInput, "false"};
			}
			else if (_history.get(_history.size()-1)._isInput == false)
			{
				if (_debug == true) System.out.println(ANSI_YELLOW + "[DEBUG] " + "Очікування вводу користувача" + ANSI_RESET);
				_userInput = in.nextLine();
				if (_userInput != null && _userInput.contains("-exit"))
				{
					if (_debug == true) System.out.println(ANSI_YELLOW + "[DEBUG] " + "Введено команду завершення роботи програми" + ANSI_RESET);
					System.exit(1);
					break;
				}
				if (_userInput != null && _userInput.contains("-help"))
				{
					ClearConsole();
					System.out.println(ANSI_GREEN + "Справка:");
					System.out.println(HIGH_INTENSITY + "Програму розробив Курапов Дмитро Ігорович" + ANSI_RESET);
					System.out.println(ANSI_GREEN + "-help - допомога");
					System.out.println("-d або -debuf - увімкнути/вимкнути режим відладки");
					System.out.println("-exit - термінове завершення роботи програми");
					System.out.println(HIGH_INTENSITY + "Для повернення назад, натисніть Enter" + ANSI_RESET);
					in.nextLine();
					continue;
				}
				if (_userInput.contains("-d")  || _userInput.contains("-debug")) 
				{
					if (_debug == false) _debug = true;
					else  _debug = false;
					_isDebugModeChanged = true;
					_userInput = _userInput.replaceAll("-d", "");
					_userInput = _userInput.replaceAll("-debug", "");
					_userInput = _userInput.strip();
				}
				if (_debug == true) System.out.println(ANSI_YELLOW + "[DEBUG] " + "У змінну _userInput записано значення: " + _userInput + ANSI_RESET);
				if (_userInput != null && (_userInput == "" && _history.get(_history.size()-1)._isMainPage == false))
				{
					if (_debug == true) System.out.println(ANSI_YELLOW + "[DEBUG] " + "Було натиснено Enter, дія - повернутись на попередню сторінку" + ANSI_RESET);
					if (_debug == true) System.out.println(ANSI_YELLOW + "[DEBUG] " + "З масиву _history було видалено значення: " + _history.get(_history.size()-1) + ANSI_RESET);
					_history.remove(_history.get(_history.size()-1));
					continue;
				}
				else
				{
					try
					{
						int _nextpage = Integer.parseInt(_userInput);
						if (_nextpage >= _history.get(_history.size()-1)._pageLines.size() || _nextpage < 0)
						{
							System.out.println(ANSI_RED + "Помилка. Введено некоректне значення індексу сторінки. Спробуйте ввести ще раз, якщо ви хочете повернутись назад, натисніть Enter на наступній сторінці." + ANSI_RESET);
							if (_debug == true) System.out.println(ANSI_YELLOW + "[DEBUG] " + "Очікування натискання Enter, дія - перезавантаження сторінки" + ANSI_RESET);
							in.nextLine();
							continue;
						}
						else _history.add(_pagesList.get(_memory.indexOf(_history.get(_history.size()-1)._pageLines.get(_nextpage))));
						if (_debug == true) System.out.println(ANSI_YELLOW + "[DEBUG] " + "До масиву _history додано значення: " + _history.get(_history.size()-1) + ANSI_RESET);
						
					} catch (NumberFormatException  e)
					{
						if (_isDebugModeChanged == true) continue;
						System.out.println(ANSI_RED + "Помилка. Ви ввели некоректну адресу сторінки. Спробуйте ввести ще раз, якщо ви хочете повернутись назад, натисніть Enter на наступній сторінці." + ANSI_RESET);
						if (_debug == true) System.out.println(ANSI_YELLOW + "[DEBUG] " + "Очікування натискання Enter, дія - перезавантаження сторінки" + ANSI_RESET);
						in.nextLine();
						continue;
					}
				}
			}
			
		}
		if (_userInput != null && _userInput.contains("-exit"))
		{
			if (_debug == true) System.out.println(ANSI_YELLOW + "[DEBUG] " + "Введено команду завершення роботи програми!" + ANSI_RESET);
			System.exit(1);
		}
		return new String[] {"-3", "Робота функції завершена.", "false"};
	}
	public static void Terminate()
	{
		if (_debug == true) System.out.println(ANSI_YELLOW + "[DEBUG] " + "Запущено метод знищення меню" + ANSI_RESET);
		in.close();
		if (_debug == true) System.out.println(ANSI_YELLOW + "[DEBUG] " + "Зачинено вхідний поток даних" + ANSI_RESET);
		_pagesList = null;
		if (_debug == true) System.out.println(ANSI_YELLOW + "[DEBUG] " + "У змінну _pagesList було записано значення: null" + ANSI_RESET);
		_mainPage = null;
		if (_debug == true) System.out.println(ANSI_YELLOW + "[DEBUG] " + "У змінну _mainPage було записано значення: null" + ANSI_RESET);
		_history = null;
		if (_debug == true) System.out.println(ANSI_YELLOW + "[DEBUG] " + "У змінну _history було записано значення: null" + ANSI_RESET);
		_memory = null;
		if (_debug == true) System.out.println(ANSI_YELLOW + "[DEBUG] " + "У змінну _memory було записано значення: null" + ANSI_RESET);
	}
	

}
