import java.util.*;

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

void setup() {

  	size(768, 432);
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

void draw(){
	//TestValues
  	letterWidth = 51;
  	letterHeight = 59;
	shadowOffsetX = 3;
	shadowOffsetY = 2;

  	
  	background(255, 253, 253);
  	
	showFrameRate();
  	showCoordinates();

  	CreateTrail();
  	strokeWeight(10.0);
  	PrintLetters();
  	MoveLetters(letterList);
}

void showCoordinates(){
  
	fill(color(0,0,0));
	text("X: " + textPositionX, 20, 50, 20);
	text("Y: " + textPositionY, 20, 80, 20);
}

void showFrameRate(){	

	if (frameCounter == 0){
		currentFrameRate = frameRate;
	}
	
	text("fps: " + currentFrameRate, 20, 20, 20);
	frameCounter++;

	if (frameCounter > 100){
  		frameCounter = 0;
	}
}

void BlinkLetters(){
	for (Letter l : letterList) {

		color fontColor = l.GetColor();
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

void PrintLetters(){

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

float MoveX(boolean canBounce, float xPos){	
	
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

float MoveY(boolean canBounce, float moveY ){
	
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

void MoveLetters(ArrayList<Letter> letters){

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

void CreateTrail() {

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

void printT(Letter letter, boolean isShadow){

	float xPos;
	float yPos;
	color strokeColor;
	
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

void printA(Letter letter, boolean isShadow){
  
  float xPos;
	float yPos;
	color strokeColor;
	
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

  	 line(xPos + letterWidth / 3.5,
   		yPos + letterHeight / 2,
    	xPos + letterWidth - letterWidth / 3.5,
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

void printZ(Letter letter, boolean isShadow){

	float xPos;
	float yPos;
	color strokeColor;
	
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
