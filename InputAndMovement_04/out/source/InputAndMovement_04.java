import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class InputAndMovement_04 extends PApplet {

int resolutionX = 640;
int resolutionY = 480;
float deltaTime;
float time;

PlayerController player;

public void setup()
{
    frameRate(60);
    surface.setSize(resolutionX,resolutionY);
    player = new PlayerController();
    ellipseMode(CENTER);
    noStroke();
}

public void draw() 
{
	long currentTime = millis();
    deltaTime = (currentTime - time) * 0.001f;

	background(0);
    player.move();
    player.checkWalls();

    if(hasGravity)
        player.makeGravity();

    time = currentTime;
}   
boolean isLeft, isRight, isUp, isDown;
boolean hasGravity;

public void keyPressed()
{
    setInputs(keyCode, true);
}

public void keyReleased()
{
    setInputs(keyCode, false);
}

public void setInputs(int keyCode, boolean isPressed)
{

    switch (keyCode) {

        case 'A' :
        isLeft = isPressed;
        break;	

        case 'D' :
        isRight = isPressed;
        break;	

        case 'W' :
        isUp = isPressed;
        break;	

        case 'S' :
        isDown = isPressed;
        break;	

        case 'G' :
        {
            if(isPressed)
            hasGravity = !hasGravity;
        }
    }
    
}
public class PlayerController
{
	float maxSpeed = 600;
	float acceleration = 10;
	float friction = 300;
	float width = 20;
	float height = 20;
	float gravity = 9.8f;
	float bouncyness = 1;
	
	boolean bounce = true;

	PVector position;
	PVector direction;
	PVector velocity;

	public PlayerController()
	{
		position = new PVector(resolutionX / 2, resolutionY / 2);
    	direction = new PVector(0,0);
    	velocity = new PVector(0,0);
	}

	public void move()
	{
		text("dirX: " + direction.x, 5, 10);
		text("dirY: " + direction.y, 5, 20);

		//Get direction
		direction.x = PApplet.parseInt(isRight) - PApplet.parseInt(isLeft);
		direction.y = PApplet.parseInt(isDown) - PApplet.parseInt(isUp);

		//Acceleration
		velocity.x += direction.normalize().x * acceleration;
		velocity.x = velocity.x >= 0 ? min(velocity.x, maxSpeed) : max(velocity.x, -maxSpeed);
		velocity.y += direction.normalize().y * acceleration; 
		velocity.y = velocity.y >= 0 ? min(velocity.y, maxSpeed) : max(velocity.y, -maxSpeed);

		//friction
		velocity.x -= (velocity.x / friction);
		velocity.y -= (velocity.y / friction);

		//Set position
		position.x += velocity.x * deltaTime;
		position.y += velocity.y * deltaTime;

		text("positionX: " + (int)position.x, 5, 30);
		text("positionY: " + (int)position.y, 5, 40);
			
		ellipse(position.x, position.y, width, height);
	}

	public void checkWalls()
	{
		if(player.position.x - player.width / 2 >= resolutionX)
			player.position.x = 0;
		if(player.position.x + player.width / 2 <= 0)
			player.position.x = resolutionX;
		if(player.position.y - player.height / 2 <= 0)
			player.position.y = 0 + player.height / 2;
		if(player.position.y + player.height / 2 >= resolutionY)
			player.position.y = resolutionY - player.width / 2;

	}

	public void makeGravity()
	{
		if(player.position.y + player.height / 2 >= resolutionY)
		{
			println(velocity.y);
			velocity.y = (bouncyness * -velocity.y);
			bouncyness -= bouncyness > 0 ? 0.05f : 0;
			if(velocity.y == 0.0f)
				bouncyness = 1;
		}
		else
			velocity.y += gravity;
	}
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "InputAndMovement_04" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
