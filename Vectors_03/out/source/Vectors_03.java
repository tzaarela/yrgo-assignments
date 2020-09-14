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

public class Vectors_03 extends PApplet {

int maxTurns = 3;
int currentTurn = 1;
int score = 0;
int resolutionX = 800;
int resolutionY = 800;
float shootStrength = 0.2f;
float startAreaWidth = 100;
float startAreaHeight = 100;
float playerWidth = 40;
float playerHeight = 40;
float goalWidth = 100;
float goalHeight = 100;
float goalX;
float goalY;


ArrayList<Circle> playerCircles;
Circle goalCircle;
PlayerState playerState;

public void setup() {

	textSize(20);
	surface.setSize(resolutionX, resolutionY);
	playerCircles = new ArrayList<Circle>();
	playerState = PlayerState.alive;
	createGoal();
}

public void draw() {

	if (currentTurn > maxTurns)
	{
		background(0);
		textAlign(CENTER);
		text("GAME OVER", resolutionX / 2, resolutionY / 2);
		text("Press space to play again...", resolutionX / 2, resolutionY / 2 + 30);
		playerState = PlayerState.dead;
	}

	else 
	{
		background(0);
		drawHud();
		drawGoal();
		drawStartArea();
		drawLines();
		movePlayerCircles();
		drawPlayerCircles();
	}

}

public void createGoal()
{
	goalX = random(startAreaWidth + goalWidth, resolutionX - goalWidth);
	goalY = random(startAreaHeight + goalHeight, resolutionY - goalHeight);
	goalCircle =  new Circle(new Vector2(goalX, goalY), goalWidth, goalHeight, color(255, 255, 255));
}

public void drawHud()
{
	textAlign(LEFT);
	fill(255, 255, 255);
	text("Max turns: " + maxTurns, 10, 20);
	text("Current turn: " + currentTurn, 10, 40);
	text("Score: " + score, 10, 60);

}

public void drawGoal()
{
	goalCircle.Draw();
}

public void drawPlayerCircles()
{
	for (Circle c : playerCircles) {
		c.Draw();
	}
}

public void drawStartArea()
{
	noFill();
	stroke(50, 150, 50);
	rect(-1, resolutionY - startAreaHeight, startAreaWidth, startAreaHeight);
}

public void drawLines()
{
	if (mousePressed && mouseButton == LEFT && playerCircles.size() == currentTurn)
	{
		Circle playerCircle = playerCircles.get(currentTurn - 1);

		stroke(255);
		line(playerCircle.position.x, playerCircle.position.y, mouseX, mouseY);
	}
}

public void movePlayerCircles()
{	
	if (playerCircles.size() == currentTurn)
	{
		Circle playerCircle = playerCircles.get(currentTurn - 1);

		if(playerCircle.isMoving)
		{
			playerCircle.Move(playerCircle.direction);
			if(playerCircle.speed <= 0)
			{
				playerCircle.isMoving = false;
				currentTurn++;
				checkIfScore(playerCircle);
			}
		}
	}
}

public void checkIfScore(Circle playerCircle)
{
	if (playerCircle.position.Distance(goalCircle.position) < goalWidth / 2)
	{
		score++;
		resetBoard();
	}
}

public void keyPressed() {
	if (key == ' ' && playerState == PlayerState.dead)
	{
		println("Restarting...");
		resetBoard();
		playerState = PlayerState.alive;
	}
}

public void resetBoard()
{
	if (playerState == PlayerState.dead)
	{
		score = 0;
	}

	playerCircles.clear();
	currentTurn = 1;
	createGoal();
}

public void mousePressed() {

	if (mouseButton == LEFT && mouseX < startAreaWidth && mouseY > resolutionY - startAreaHeight) 
	{
		Circle playerCircle = new Circle(new Vector2(mouseX, mouseY), playerWidth, playerHeight, color(255, 255, 0));
		playerCircles.add(playerCircle);
	}
}

public void mouseReleased() 
{
	if (mouseButton == LEFT && playerCircles.size() == currentTurn)
	{
		Circle playerCircle = playerCircles.get(currentTurn - 1);

		Vector2 playerPosition = playerCircle.position;
		Vector2 mousePosition = new Vector2(mouseX, mouseY);

		println("playerX: " + playerPosition.x);
		println("playerY: " + playerPosition.y);

		println("mouseX: " + mousePosition.x);
		println("mouseY: " + mousePosition.y);
		
		playerCircle.direction = new Vector2(playerPosition.x - mousePosition.x, playerPosition.y - mousePosition.y).Normalize();
		playerCircle.speed = playerPosition.Distance(mousePosition) * shootStrength;

		println("directionX: " + playerCircle.direction.x);
		println("directionY: " + playerCircle.direction.y);
		println("speed: " + playerCircle.speed);

		playerCircle.isMoving = true;
	}
}



public class Circle
{
	Vector2 position;
	Vector2 direction;

	float speed;
	float width;
	float height;
	float friction = 2;

	int circleColor;

	boolean isMoving;

	public Circle(Vector2 position, float width, float height, int circleColor)
	{
		this.position = position;
		this.width = width;
		this.height = height;
		this.circleColor = circleColor;
	}

	public void Move(Vector2 direction)
	{
		if(speed > 0)
		{
			this.position.x -= direction.x * speed;
			this.position.y -= direction.y * speed;
			speed = speed - friction;
		}
		else
		{
			isMoving = false;
		}
	}

	public void Draw()
	{
		noStroke();
		fill(circleColor);
		ellipse(position.x, position.y, width, height);
	}

}
enum PlayerState
{
	alive,
	dead
}
public class Vector2
{
	float x;
	float y;

	public Vector2(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	public float Magnitude()
	{
		return sqrt(x * x + y * y);
	}

	public Vector2 Normalize()
	{
		float mag = Magnitude();
		if (mag > 0)
		{
			return new Vector2(x / mag, y / mag);
		}

		return new Vector2(0,0);
	}

	public float Distance(Vector2 target)
	{
		float targetDistance =  new Vector2(x - target.x, y - target.y).Magnitude();
		return targetDistance;
	}
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Vectors_03" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
