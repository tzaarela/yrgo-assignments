public class Letter {

	int _id;
	String _name = "";
	float _moveDirectionX = 1;
	float _moveDirectionY = 1;
	float _xPos = 0;
	float _yPos = 0;
	color _fontColor;
	boolean _hasShadow;
	Letter _shadow;

	public Letter(int id, String name, color fontColor, boolean hasShadow){
		_name = name;
		_fontColor = fontColor;
		_id = id;
		_hasShadow = hasShadow;
	}

	public Letter GetShadow(){
		return _shadow;
	}

	public String GetName(){
		return _name;
	}

	public color GetColor(){
		return _fontColor;
	}

	public float GetPosX(){
		return _xPos;
	}

	public float GetPosY()
	{
		return _yPos;
	}

	public int GetId()
	{
		return _id;
	}

	public void SetPosX(float newPos){
		_xPos = newPos;
	}

	public void SetPosY(float newPos){
		_yPos = newPos;
	}

	public void SetColor(color fontColor){
		_fontColor = fontColor;
	}

	public void SetShadow(Letter shadow){
		_shadow = shadow;
	}

	public boolean HasShadow(){
		return _hasShadow;
	}
	
}