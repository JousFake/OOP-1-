package com.works.works5;

public class UserClassString {
	public String[] _strs;
	public int test2;
	
	public UserClassString(String ...strings)
	{
		test2 = 12342;
		if (strings.length > 0)
		{
			_strs = new String[strings.length];
			for (int i = 0; i < strings.length; i++) _strs[i] = strings[i];
		}
		else
		{
			_strs = new String[0];
		}
	}
	public UserClassString()
	{
		
	}
	public String toString()
	{
		String returnString = "";
		for (int i = 0; i < _strs.length; i++)
		{
			returnString+=_strs[i];
		}
		return returnString;
	}
	public void add(String string)
	{
		if (test2 != 0 ) test2 = 50;
		String[] _tempList = new String[_strs.length+1];
		System.arraycopy(_strs, 0, _tempList, 0, _strs.length);
		_tempList[_strs.length] = string;
		_strs = new String[_tempList.length];
		System.arraycopy(_tempList, 0, _strs, 0, _tempList.length);
		_tempList = null;
	}
	public void clear()
	{
		_strs = new String[0];
	}
	public boolean remove(String string)
	{
		for(int i = 0; i < _strs.length; i++)
		{
			if(_strs[i].contains(string))
			{
				if (_strs[i] == string)
				{
					String[] _tempList = new String[_strs.length];
					System.arraycopy(_strs, 0, _tempList, 0, _strs.length);
					_strs = new String[_tempList.length-1];
					
					int z = 0;
					for (int y = 0; y < _strs.length; y++)
					{
						if (_tempList[z] == string) 
						{
							z++;
							y--;
						}
						else
						{
							_strs[y] = _tempList[z];
							z++;
						}
					}
				}
				else
				{
					_strs[i] = _strs[i].replace(string, "");
				}
				return true;
			}
		}
		return false;
	}
	public Object[] toArray()
	{
		Object[] _array = new Object[_strs.length];
		System.arraycopy(_strs, 0, _array, 0, _strs.length);
		return _array;
	}
	public int size()
	{
		return this._strs.length;
	}
	public int length()
	{
		int _size = 0;
		for (int i = 0; i < this.size(); i++)
		{
			_size+= this._strs[i].length();
		}
		return _size;
	}
	public boolean contains(String string)
	{
		for (int i = 0; i < _strs.length; i++) if (_strs[i].contains(string) || _strs[i] == string) return true;
		return false;
	}
	public boolean containsAll(UserClassString container)
	{
		UserIterator iterator = container.createIterator();
		while (iterator.hasNext())
		{
			if (this.contains(iterator.next())) continue;
			else return false;
		}
		return true;
	}
	public char getCharAt(int index)
	{
		for (int i = 0; i < this.size(); i++)
		{
			if (index >= _strs[i].length()) 
			{
				if (i == 0) index--;
				index-=_strs[i].length();
				continue;
			}
			
			return _strs[i].charAt(index);
		}
		return '\0';
	}
	public void setCharAt(int index, char character)
	{
		String _tempStr = "";
		for (int i = 0; i < this.size(); i++)
		{
			if (index >= _strs[i].length()) 
			{
				System.out.println("here");
				//if(i == 0) index--;
				index-=_strs[i].length();
				continue;
			}
			for (int y = 0; y < _strs[i].length(); y++)
			{
				if(y != index) _tempStr+=_strs[i].charAt(y);
				else _tempStr+=character;
			}
			if(_tempStr != null) _strs[i] = _tempStr;
			break;
		}
	}
	public boolean deleteCharAt(int index)
	{
		for (int i = 0; i < this.size(); i++)
		{
			if (index >= _strs[i].length()) 
			{
				index-=_strs[i].length()-1;
				if (i == 0) continue;
				else index++;
				continue;
			}
			String _tempString = "";
			for (int y = 0; y < _strs[i].length(); y++)
			{
				if (y != index) _tempString+=_strs[i].charAt(y);
				else continue;
			}
			_strs[i] = _tempString;
			if (_strs[i] == "") this.remove("");
			return true;
		}
		return false;
	}
	public UserIterator createIterator()
	{
		UserIterator thisIterator = new UserIterator(this.size(), this);
		return thisIterator;
	}

}
