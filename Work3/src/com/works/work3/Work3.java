package com.works.work3;

import java.util.Scanner;

public class Work3 {
	private String _str;
	private DeleteSymbolsClass object = new DeleteSymbolsClass();
	
	public void main(String[] args) {
		System.out.print("Введіть строку: ");
		_str = getString();
		object.setString(_str);
		System.out.println("Початковий текст: " + object.getInitialString());
		object.deleteSymbols();
		System.out.println("Результат: " + object.getEditedString());
	}
	public String getString()
	{
		String _string;
		Scanner in = new Scanner(System.in);
		_string = in.nextLine();
		in.close();
		return _string;
	}
}
