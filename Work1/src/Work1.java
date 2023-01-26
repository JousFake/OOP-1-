
public class Work1 {
	private static int bite = 0xf759;
	private static long Phonenumber = 380662239715L;
	private static byte LastTwoDigits = 0b1111;
	private static int LastFourDigits = 022763;
	private static int _exerciseFive;
	private static char _сharacterByNumberOfGroupList;
	private static short _pairNumbers;
	private static short _unpairedNumbers;
	private static short _numberOfOnes;
	
	
	public static void main(String[] args) 
	{
		int _numberByGroupList = 6;
		_exerciseFive = ((_numberByGroupList-1)%26)+1;
		_сharacterByNumberOfGroupList = (char)('A' + _exerciseFive-1);
		_pairNumbers = (short)(getEven(bite) + getEven(Phonenumber) + getEven(LastTwoDigits) + getEven(LastFourDigits) + getEven(_exerciseFive));
		_unpairedNumbers = (short)(getOdd(bite) + getOdd(Phonenumber) + getOdd(LastTwoDigits) + getOdd(LastFourDigits) + getOdd(_exerciseFive));
		_numberOfOnes = (short)(getNumberOfOnes(bite) + getNumberOfOnes(Phonenumber) + getNumberOfOnes(LastTwoDigits) + getNumberOfOnes(LastFourDigits) + getNumberOfOnes(_exerciseFive));
		display();
	}
	
	public static int getEven(long i)
	{
		long _currentNumber = 0;
		long _restNumbers = i;
		int count = 0;
		while (_restNumbers != 0)
		{
			_currentNumber = _restNumbers%10;
			_restNumbers = _restNumbers/10;
			if ((_currentNumber % 2) == 0)
			{
				count++;
			}
		}
		return count;
	}
	public static int getOdd(long i)
	{
		long _currentNumber = 0;
		long _restNumbers = i;
		int count = 0;
		while (_restNumbers != 0)
		{
			_currentNumber = _restNumbers%10;
			_restNumbers = _restNumbers/10;
			if ((_currentNumber % 2) != 0)
			{
				count++;
			}
		}
		return count;
	}
	public static int getNumberOfOnes(long i)
	{
		long _currentNumber = 0;
		long _restNumbers = i;
		int count = 0;
		while (_restNumbers != 0)
		{
			_currentNumber = _restNumbers%10;
			_restNumbers = _restNumbers/10;
			if (_currentNumber == 1)
			{
				count++;
			}
		}
		return count;
	}
	public static void display()
	{
		System.out.println("Виведення усіх значень:");
		System.out.println("Завдання 1(Випадкове п'ятизначне число):\t" + bite);
		System.out.println("Завдання 2(омер мобільного телефону):\t\t" + Phonenumber);
		System.out.println("Завдання 3(Останні дві цифри телефону):\t\t" + LastTwoDigits);
		System.out.println("Завдання 4(Останні чотири цифри телефону):\t" + LastFourDigits);
		System.out.println("Завдання 5(Залишок від ділення..):\t\t" + _exerciseFive);
		System.out.println("Завдання 6(Символ в англійському алфавіті):\t" + _сharacterByNumberOfGroupList);
		System.out.println("\nІнші завдання:");
		System.out.println("Завдання 7(Кількість парних цифр):\t" + _pairNumbers);
		System.out.println("Завдання 7(Кількість непарних цифр):\t" + _unpairedNumbers);
		System.out.println("Завдання 8(Кількість одиниць):\t\t" + _numberOfOnes);
	}
}
