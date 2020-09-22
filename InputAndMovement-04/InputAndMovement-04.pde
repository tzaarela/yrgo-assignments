int resolutionX = 1280;
int resolutionY = 720;
int totalEnemies = 100;
int enemyCount; 
float enemySpawnRate = 10; // per second
float scaleZoom = 0.1;
float deltaTime;
float frameTime;
float currentTime;

Ball player;
ArrayList<Ball> enemies;
MovementController moveController;
GameController gameController;

void setup()
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

void draw() 
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


