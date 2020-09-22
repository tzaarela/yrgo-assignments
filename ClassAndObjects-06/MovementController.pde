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
				enemy.velocity = player.velocity.copy().mult(1.1);
				enemy.hasCollided = true;

				//Slowing down player on colission
				player.velocity.add(player.velocity.copy().mult(-1).div(50));
			}
			else
				enemy.hasCollided = false;
		}
	}
}