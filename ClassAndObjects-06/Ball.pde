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
	color circleColor;

	boolean debugOn;
	boolean hasCollided;

	PVector position;
	PVector acceleration;
	PVector velocity;
	PImage image;

	AnimationController animator;

	public Ball(float size, float spawnPosX, float spawnPosY, color circleColor, String spriteSheetFile)
	{
		this.position = new PVector(spawnPosX, spawnPosY);
    	this.acceleration = new PVector(0,0);
    	this.velocity = new PVector(0,0);
		this.size = size;
		this.spawnPosX = spawnPosX;
		this.spawnPosY = spawnPosY;
		this.circleColor = circleColor;

		this.maxSpeed = 80;
		this.acc = 0.8;
		this.friction = 2;
		this.gravity = 0.58;
		this.eatCooldown = 0;
		this.debugOn = false;
		this.hasCollided = false;
		this.animator = new AnimationController(spriteSheetFile);
	}

	void move(PVector acceleration)
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

	void checkWalls()
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

	void eat(Ball target)
	{
		size += size * 0.05;
		target.position.mult(100000);
		eatCooldown = millis();
	}

	void addGravity()
	{
		velocity.y += gravity * maxSpeed * deltaTime;
	}
}
