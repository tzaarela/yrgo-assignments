int resolutionX = 640;
int resolutionY = 480;
float deltaTime;
float time;

PlayerController player;

void setup()
{
    frameRate(300);
    surface.setSize(resolutionX,resolutionY);
    player = new PlayerController();
    ellipseMode(CENTER);
    noStroke();
}

void draw() 
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