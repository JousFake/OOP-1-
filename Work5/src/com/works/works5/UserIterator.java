package com.works.works5;

public class UserIterator implements CustomInterface {
	private int _currentIndex = 0;
	private int _currentSize = 0; 
	private UserClassString _object = null;

	public UserIterator(int currentSize, UserClassString object)
	{
		this._currentSize = currentSize;
		this._object = object;
		this._currentIndex = 0;
	}

	@Override
	public boolean hasNext() {
		if (this._currentIndex < this._currentSize)
		{
			return true;
		}
		return false;
	}

	@Override
	public String next() {
		try {
			String _temp = this._object._strs[this._currentIndex];
			this._currentIndex++;
			return _temp;
			
		} catch (ArrayIndexOutOfBoundsException e)
		{
			_currentIndex--;
			return null;
		}
	}

	@Override
	public void remove() {
		this._object.remove(this._object._strs[_currentIndex]);
		this._currentSize = this._object.size();
	}

}
