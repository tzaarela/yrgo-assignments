public class PlayerController
{
	float maxSpeed = 600;
	float acceleration = 10;
	float friction = 300;
	float width = 20;
	float height = 20;
	float gravity = 9.8;
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

	void move()
	{
		text("dirX: " + direction.x, 5, 10);
		text("dirY: " + direction.y, 5, 20);

		//Get direction
		direction.x = int(isRight) - int(isLeft);
		direction.y = int(isDown) - int(isUp);

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

	void checkWalls()
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

	void makeGravity()
	{
		if(player.position.y + player.height / 2 >= resolutionY)
		{
			println(velocity.y);
			velocity.y = (bouncyness * -velocity.y);
			bouncyness -= bouncyness > 0 ? 0.05 : 0;
			if(velocity.y == 0.0)
				bouncyness = 1;
		}
		else
			velocity.y += gravity;
	}
}