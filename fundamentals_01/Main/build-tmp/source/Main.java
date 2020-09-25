import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Main extends PApplet {



float letterWidth = 70;
float letterHeight = 80;
float shadowOffsetX = 3;
float shadowOffsetY = 2;
float xMoveDirection = 1;
float yMoveDirection = 1;
float moveSpeed = 1;
float frameCounter = 0;
float currentFrameRate = 0;
float textPositionX = 300;
float textPositionY = 200;
float letterSpacing = -20;

boolean hasMovingLetters = true;
boolean isColorSwapped = false;

ArrayList<Letter> letterList;

public void setup() {

  	
  	frameRate(300);

  	letterList = new ArrayList<Letter>();
  		letterList.add(new Letter(0, "T", color(68,83,80,200), true));
  		letterList.add(new Letter(1, "Z", color(68,83,80,200), true));
  		letterList.add(new Letter(2, "A", color(68,83,80,200), true));
  		letterList.add(new Letter(3, "A", color(68,83,80,200), true)); 
  
  	for (int i = 0; i < letterList.size(); i++) {

  		Letter letter = letterList.get(i);

  		letter.SetPosX(textPositionX + (letterSpacing + letterWidth) * i);
  		letter.SetPosY(textPositionY);

  		if(letter.HasShadow()){
  			letter.SetShadow(new Letter(letter.GetId(), "SHADOW", color(51, 49, 49, 327), false));
  		}
  	}
}

public void draw(){

	//saveFrame("frame-######.png");

	//TestValues
  	letterWidth = 51;
  	letterHeight = 59;
	shadowOffsetX = 3;
	shadowOffsetY = 2;

  	
  	background(255, 253, 253);
  	
	showFrameRate();
  	showCoordinates();

  	CreateTrail();
  	strokeWeight(10.0f);
  	PrintLetters();
  	MoveLetters(letterList);
}

public void showCoordinates(){
  
	fill(color(0,0,0));
	text("X: " + textPositionX, 20, 50, 20);
	text("Y: " + textPositionY, 20, 80, 20);
}

public void showFrameRate(){	

	if (frameCounter == 0){
		currentFrameRate = frameRate;
	}
	
	text("fps: " + currentFrameRate, 20, 20, 20);
	frameCounter++;

	if (frameCounter > 100){
  		frameCounter = 0;
	}
}

public void BlinkLetters(){
	for (Letter l : letterList) {

		int fontColor = l.GetColor();
		int a = (fontColor >> 24) & 0xFF;
		int r = (fontColor >> 16) & 0xFF; 
		int g = (fontColor >> 8) & 0xFF; 
		int b = fontColor & 0xFF;  

		if (r >= 254){
			isColorSwapped = true;
		}
		else if (r <= 0){
			isColorSwapped = false;
		}
		if (isColorSwapped){
			l.SetColor(color(r - 10, g, b, a));
		}
		else {
			l.SetColor(color(r + 10, g, b, a));
		}
	}
}

public void PrintLetters(){

	for (Letter letter : letterList) {
		switch (letter.GetName()) {
			case "T" : {
				printT(letter, false);
				break;
			}

			case "A" : {
				printA(letter, false);
				break;
			}

			case "Z" : {
				printZ(letter, false);
				break;
			}
			default :
			break;	
		}
	}
}

public float MoveX(boolean canBounce, float xPos){	
	
	if (canBounce){
		if(textPositionX > 570){
	  		xMoveDirection = -1;
	  		BlinkLetters();
	  	}

	  	if(textPositionX < 0){
	  		xMoveDirection = 1;
	  		BlinkLetters();
	  	}
	}

  	xPos = xPos + moveSpeed * xMoveDirection;

  	return xPos;
}

public float MoveY(boolean canBounce, float moveY ){
	
	if (canBounce){
		if(textPositionY > 374){
	  		yMoveDirection = -1;
	  		BlinkLetters();
	  	}
	  	if(textPositionY < 0){
	  		yMoveDirection = 1;
	  		BlinkLetters();
	  	}
  	}

  	moveY = moveY + moveSpeed * yMoveDirection;

  	return moveY;
}

public void MoveLetters(ArrayList<Letter> letters){

	for (int i = 0; i < letters.size(); i++) {
			
			Letter letter = letterList.get(i);

			if (i == 0){
	  			textPositionX = letter.GetPosX();
	  			textPositionY = letter.GetPosY();
	  		}

			float newPosX = MoveX(true, letter.GetPosX());
			float newPosY = MoveY(true, letter.GetPosY());

	  		letter.SetPosX(newPosX);
	  		letter.SetPosY(newPosY);
  	}
}

public void CreateTrail() {

	float centerX;
	float centerY;
	float lettersLength;
	
	centerX = textPositionX + ((letterList.size() + 3) * (letterWidth + letterSpacing) / 2);
	centerY = textPositionY + letterHeight / 2;

	stroke(96, 87, 82, 52);
	strokeWeight(261);
	lettersLength = (letterList.size() + 1) * (letterWidth + letterSpacing) - letterSpacing;
	fill(50,113,50);
	
	if (yMoveDirection == 1 && xMoveDirection == 1){
		line(centerX, centerY,
			centerX - 20, centerY - 20);
		line(centerX, centerY,
			centerX - 40, centerY - 40);
		line(centerX, centerY,
			centerX - 60, centerY - 60);
		line(centerX, centerY,
			centerX - 80, centerY - 80);
		line(centerX, centerY,
			centerX - 100, centerY - 100);
	}

	else if (yMoveDirection == 1 && xMoveDirection == -1){
		line(centerX, centerY,
			centerX + 20, centerY - 20);
		line(centerX, centerY,
			centerX + 40, centerY - 40);
		line(centerX, centerY,
			centerX + 60, centerY - 60);
		line(centerX, centerY,
			centerX + 80, centerY - 80);
		line(centerX, centerY,
			centerX + 100, centerY - 100);

	}

	else if (yMoveDirection == -1 && xMoveDirection == 1){
		line(centerX, centerY,
			centerX - 20, centerY + 20);
		line(centerX, centerY,
			centerX - 40, centerY + 40);
		line(centerX, centerY,
			centerX - 60, centerY + 60);
		line(centerX, centerY,
			centerX - 80, centerY + 80);
		line(centerX, centerY,
			centerX - 100, centerY + 100);

	}

	else if (yMoveDirection == -1 && xMoveDirection == -1){
		line(centerX, centerY,
			centerX + 20, centerY + 20);
		line(centerX, centerY,
			centerX + 40, centerY + 40);
		line(centerX, centerY,
			centerX + 60, centerY + 60);
		line(centerX, centerY,
			centerX + 80, centerY + 80);
		line(centerX, centerY,
			centerX + 100, centerY + 100);
	}
}

public void printT(Letter letter, boolean isShadow){

	float xPos;
	float yPos;
	int strokeColor;
	
	if(!isShadow){
		printT(letter, true);
		strokeColor = letter.GetColor();
		xPos = letter.GetPosX();
		yPos = letter.GetPosY();
	}
	else{
		strokeColor = letter.GetShadow().GetColor();
		xPos = letter.GetPosX() + shadowOffsetX;
		yPos = letter.GetPosY() + shadowOffsetY;
	}
	
	stroke(strokeColor);

  	line(xPos,
	    yPos,
	    letterWidth + xPos,
	    yPos);

  	line(xPos + letterWidth / 2,
 	 	letterHeight+yPos,
 	 	xPos + letterWidth / 2,
 	 	yPos);
}

public void printA(Letter letter, boolean isShadow){
  
  float xPos;
	float yPos;
	int strokeColor;
	
	if(!isShadow){
		printA(letter, true);
		strokeColor = letter.GetColor();
		xPos = letter.GetPosX();
		yPos = letter.GetPosY();
	}
	else{
		strokeColor = letter.GetShadow().GetColor();
		xPos = letter.GetPosX() + shadowOffsetX;
		yPos = letter.GetPosY() + shadowOffsetY;
	}

	stroke(strokeColor);

  	 line(xPos + letterWidth / 3.5f,
   		yPos + letterHeight / 2,
    	xPos + letterWidth - letterWidth / 3.5f,
        yPos + letterHeight / 2);

  	line(xPos + letterWidth / 2, 
  	   	yPos, 
  	   	xPos + letterWidth,
  	   	yPos + letterHeight);

  	line(xPos + letterWidth / 2,
   	   	yPos, 
  	 	xPos, 
   	   	yPos + letterHeight);
}

public void printZ(Letter letter, boolean isShadow){

	float xPos;
	float yPos;
	int strokeColor;
	
	if(!isShadow){
		printZ(letter, true);
		strokeColor = letter.GetColor();
		xPos = letter.GetPosX();
		yPos = letter.GetPosY();
	}
	else{
		strokeColor = letter.GetShadow().GetColor();
		xPos = letter.GetPosX() + shadowOffsetX;
		yPos = letter.GetPosY() + shadowOffsetY;
	}

	stroke(strokeColor);

  	line(xPos,
   	   	yPos,
   	   	letterWidth + xPos,
   	   	yPos);

  	line(xPos,
   	   	letterHeight + yPos,
   	   	letterWidth + xPos,
   	   	yPos);

  	line(xPos,
   	   	letterHeight + yPos,
   	   	letterWidth + xPos,
   	   	letterHeight + yPos);
}
public class Letter {

	int _id;
	String _name = "";
	float _moveDirectionX = 1;
	float _moveDirectionY = 1;
	float _xPos = 0;
	float _yPos = 0;
	int _fontColor;
	boolean _hasShadow;
	Letter _shadow;

	public Letter(int id, String name, int fontColor, boolean hasShadow){
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

	public int GetColor(){
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

	public void SetColor(int fontColor){
		_fontColor = fontColor;
	}

	public void SetShadow(Letter shadow){
		_shadow = shadow;
	}

	public boolean HasShadow(){
		return _hasShadow;
	}
	
}
  public void settings() { 	size(768, 432); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Main" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
