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

public class ClassAndObjects_06 extends PApplet {

int resolutionX = 1280;
int resolutionY = 720;
int totalEnemies = 100;
int enemyCount; 
float enemySpawnRate = 10; // per second
float scaleZoom = 0.1f;
float deltaTime;
float frameTime;
float currentTime;

Ball player;
ArrayList<Ball> enemies;
MovementController moveController;
GameController gameController;

public void setup()
{
    frameRate(600);
    surface.setLocation(400, 200);
    surface.setSize(resolutionX,resolutionY);
    
    player = new Ball(200, resolutionX / scaleZoom / 2,  resolutionY / scaleZoom / 2, color(0, 200, 0), "circleEater.png");
    player.debugOn = true;
    enemies = new ArrayList<Ball>();

    moveController = new MovementController(player, enemies);
    gameController = new GameController(player, enemies);

    ellipseMode(CENTER);
    noStroke();
}

public void draw() 
{
	currentTime = millis();
    deltaTime = (currentTime - frameTime) * 0.001f;
    gameController.currentTime = currentTime;

    scale(scaleZoom);
	background(0);
    gameController.spawnEnemy();
    moveController.checkColissions();
    moveController.movePlayer();
    moveController.moveEnemies();
    
    frameTime = currentTime;
}   


public class AnimationController
{
	float animationFps = 1;
	float startTime = 0;
	float rotation = 0;
	int frameCounter = 0;
	int spriteCount = 3;

	PImage spritesheet;
	PImage currentFrame;
	PImage[] sprites;
	
	public AnimationController(String spriteSheetFile)
	{
		if (spriteSheetFile != "")
		{
			this.spritesheet = loadImage(spriteSheetFile);
			this.sprites = new PImage[spriteCount];
		}
	}

	public void loadAnimations()
	{
		imageMode(CENTER);

  		int spriteWidth = spritesheet.width/spriteCount;
		int spriteHeight = spritesheet.height;

		for (int i = 0; i < sprites.length; i++)
		{
			int x = i % spriteCount * spriteWidth;
			int y = i / spriteCount * spriteHeight;
			sprites[i] = spritesheet.get(x, y, spriteWidth, spriteHeight);
 		}
	}

	public PImage animate(float rotation)
	{
		if (currentTime - startTime > animationFps * 200)
		{
			this.rotation = rotation;
			// spush and pop the current matrix stack
			startTime = currentTime;
			
			// spush and pop the current matrix stack
			currentFrame = sprites[frameCounter];
			frameCounter = frameCounter < spriteCount - 1 ? frameCounter + 1 : 0;
		}

		return currentFrame;
	}
}
public class Ball
{
	float maxSpeed;
	float acc;
	float friction;
	float gravity;
	float size;
	float spawnPosX;
	float spawnPosY;
	float eatCooldown;
	int circleColor;

	boolean debugOn;
	boolean hasCollided;

	PVector position;
	PVector acceleration;
	PVector velocity;
	PImage image;

	AnimationController animator;

	public Ball(float size, float spawnPosX, float spawnPosY, int circleColor, String spriteSheetFile)
	{
		this.position = new PVector(spawnPosX, spawnPosY);
    	this.acceleration = new PVector(0,0);
    	this.velocity = new PVector(0,0);
		this.size = size;
		this.spawnPosX = spawnPosX;
		this.spawnPosY = spawnPosY;
		this.circleColor = circleColor;

		this.maxSpeed = 80;
		this.acc = 0.8f;
		this.friction = 2;
		this.gravity = 0.58f;
		this.eatCooldown = 0;
		this.debugOn = false;
		this.hasCollided = false;
		this.animator = new AnimationController(spriteSheetFile);
	}

	public void move(PVector acceleration)
	{
		acceleration.normalize();
		acceleration.setMag(acc);

		if(debugOn)
		{
			fill(255);
			text("dirX: " + acceleration.x, 5, 10);
			text("dirY: " + acceleration.y, 5, 20);
		}

		velocity.add(acceleration.mult(maxSpeed * deltaTime));
    	velocity.limit(maxSpeed);

		velocity.sub(velocity.copy().mult(friction * deltaTime)); 
		position.add(velocity.copy().mult(maxSpeed * deltaTime));

		if(debugOn)
		{
			text("positionX: " + (int)position.x, 5, 30);
			text("positionY: " + (int)position.y, 5, 40);
			text("velocityX: " + velocity.x, 5, 50);
			text("velocityY: " + velocity.y, 5, 60);
		}
		
		fill(circleColor);
		if (image != null)
		{
			imageMode(CENTER);
			pushMatrix();
				translate(position.x, position.y);
				rotate(animator.rotation);
				image(image, 0, 0, size, size);
			popMatrix();
			imageMode(CORNER);
			println("rotation: " + animator.rotation);
		}
		else
			ellipse(position.x, position.y, size, size);
	}

	public void checkWalls()
	{
		if(position.x - size / 2 >= resolutionX / scaleZoom)
			position.x = 0;

		if(position.x + size / 2 <= 0)
			position.x = resolutionX / scaleZoom;

		if(position.y - size / 2 <= 0)
		{
			position.y = 0 + size / 2;
			velocity.y = velocity.y * -1;
		}

		if(position.y + size / 2 >= resolutionY / scaleZoom)
		{
			position.y = resolutionY / scaleZoom - size / 2;
			velocity.y = velocity.y * -1;
		}
	}

	public void eat(Ball target)
	{
		size += size * 0.05f;
		target.position.mult(100000);
		eatCooldown = millis();
	}

	public void addGravity()
	{
		velocity.y += gravity * maxSpeed * deltaTime;
	}
}
public class GameController
{
	float currentTime;
	Ball player;
	ArrayList<Ball> enemies;
	int enemyColor;

	public GameController(Ball player, ArrayList<Ball> enemies)
	{
		this.player = player;
		this.enemies = enemies;
		
		enemyColor = color(255);
	}

	public void spawnEnemy()
	{
		if(currentTime * enemySpawnRate >= 600 * (enemyCount + 1) && enemyCount < totalEnemies)
    	{
			float ballSize = random(40, 600);
			PVector randomPos = new PVector(
				random(0, resolutionX / scaleZoom), random(0, resolutionY / scaleZoom));

			if (ballSize / 2 + player.size / 2 < player.position.dist(randomPos))
			{
				enemyColor = color(random(0, 255), random(0, 255), random(0, 255));
				Ball enemy = new Ball(ballSize, randomPos.x, randomPos.y, enemyColor, "");

				enemies.add(enemy);
				enemyCount++;
			}
    	}		
	}
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

public PVector getInputVector()
{
    PVector input = new PVector();
    input.x = PApplet.parseInt(isRight) - PApplet.parseInt(isLeft);
    input.y = PApplet.parseInt(isDown) - PApplet.parseInt(isUp);
    return input;
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
public class MovementController
{
	Ball player;
	ArrayList<Ball> enemies;

	public MovementController(Ball player, ArrayList<Ball> enemies)
	{
		this.player = player;
		this.enemies = enemies;
		player.animator.loadAnimations();
	}

	public void movePlayer()
	{
		if (hasGravity)
			player.addGravity();

		PVector input = getInputVector();
		float radians = (float)Math.atan2(-input.y, -input.x);
		if(input.x == 0 && input.y == 0)
		{
			radians = player.animator.rotation;
		}

		player.image = player.animator.animate(radians);  //TODO add input Vector as radians (rotation)
    	player.move(input);
    	player.checkWalls();
	}

	public void moveEnemies()
	{
		for (Ball enemy : enemies)
   		{
			if (hasGravity)
				enemy.addGravity();

			enemy.move(new PVector(random(-1, 1), random(-1, 1)));
			enemy.checkWalls();
		}	
	}

	public void checkColissions()
	{
		for (Ball enemy : enemies) 
		{
			float maxDistance = player.size / 2 + enemy.size / 2;
			if (maxDistance >= dist(player.position.x, player.position.y, enemy.position.x, enemy.position.y) && !enemy.hasCollided)
			{
				if (player.size > enemy.size && millis() - player.eatCooldown > 1000)
				{
					player.eat(enemy);
				}

				//Bouncing off enemies
				enemy.velocity = player.velocity.copy().mult(1.1f);
				enemy.hasCollided = true;

				//Slowing down player on colission
				player.velocity.add(player.velocity.copy().mult(-1).div(50));
			}
			else
				enemy.hasCollided = false;
		}
	}
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "ClassAndObjects_06" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
