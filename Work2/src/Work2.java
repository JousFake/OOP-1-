import java.util.Scanner;

public class Work2 {
	private static int _number;

	public static void main(String[] args) {
		_number = getNumber();
		if (getSumOfDigits(_number/1000) == getSumOfDigits(_number%1000))
		{
			System.out.println("Сумма перших трьох цифр числа дорівнює сумі останніх трох цифр.");
		}
		else
		{
			System.out.println("Сумма перших трьох цифр числа не дорівнює сумі останніх трох цифр.");
		}
	}
	
	public static int getNumber()
	{
		int _num = 0;
		Scanner in = new Scanner(System.in);
		while (_num < 100000 || _num > 999999)
		{
			System.out.print("Введіть шестизначне число: ");
			_num = in.nextInt();
		}
		in.close();
		return _num;
	}
	public static int getSumOfDigits(int num)
	{
		long _currentDigit = 0;
		long _restDigits = num;
		int _sum = 0;
		while (_restDigits != 0)
		{
			_currentDigit = _restDigits%10;
			_restDigits = _restDigits/10;
			_sum += _currentDigit;
		}
		return _sum;
	}

}
