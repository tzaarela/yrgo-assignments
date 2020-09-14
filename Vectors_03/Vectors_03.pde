int maxTurns = 3;
int currentTurn = 1;
int score = 0;
int resolutionX = 800;
int resolutionY = 800;

float shootStrength = 0.2;
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

void setup() {

	textSize(20);
	surface.setSize(resolutionX, resolutionY);
	playerCircles = new ArrayList<Circle>();
	playerState = PlayerState.alive;
	createGoal();
}

void draw()
{
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

void createGoal()
{
	goalX = random(startAreaWidth + goalWidth, resolutionX - goalWidth);
	goalY = random(startAreaHeight + goalHeight, resolutionY - goalHeight);
	goalCircle =  new Circle(new Vector2(goalX, goalY), goalWidth, goalHeight, color(255, 255, 255));
}

void drawHud()
{
	textAlign(LEFT);
	fill(255, 255, 255);
	text("Max turns: " + maxTurns, 10, 20);
	text("Current turn: " + currentTurn, 10, 40);
	text("Score: " + score, 10, 60);
}

void drawGoal()
{
	goalCircle.Draw();
}

void drawPlayerCircles()
{
	for (Circle c : playerCircles) {
		c.Draw();
	}
}

void drawStartArea()
{
	noFill();
	stroke(50, 150, 50);
	rect(-1, resolutionY - startAreaHeight, startAreaWidth, startAreaHeight);
}

void drawLines()
{
	if (mousePressed && mouseButton == LEFT && playerCircles.size() == currentTurn)
	{
		Circle playerCircle = playerCircles.get(currentTurn - 1);

		stroke(255);
		line(playerCircle.position.x, playerCircle.position.y, mouseX, mouseY);
	}
}

void movePlayerCircles()
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

void checkIfScore(Circle playerCircle)
{
	if (playerCircle.position.Distance(goalCircle.position) < goalWidth / 2)
	{
		score++;
		resetBoard();
	}
}

void keyPressed()
{
	if (key == ' ' && playerState == PlayerState.dead)
	{
		println("Restarting...");
		resetBoard();
		playerState = PlayerState.alive;
	}
}

void resetBoard()
{
	if (playerState == PlayerState.dead)
	{
		score = 0;
	}

	playerCircles.clear();
	currentTurn = 1;
	createGoal();
}

void mousePressed() 
{

	if (mouseButton == LEFT && mouseX < startAreaWidth && mouseY > resolutionY - startAreaHeight) 
	{
		Circle playerCircle = new Circle(new Vector2(mouseX, mouseY), playerWidth, playerHeight, color(255, 255, 0));
		playerCircles.add(playerCircle);
	}
}

void mouseReleased() 
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



