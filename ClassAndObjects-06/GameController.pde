public class GameController
{
	float currentTime;
	Ball player;
	ArrayList<Ball> enemies;
	color enemyColor;

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