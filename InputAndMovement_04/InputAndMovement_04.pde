int resolutionX = 640;
int resolutionY = 480;

PlayerController player;

void setup()
{
    surface.setSize(resolutionX,resolutionY);
    player = new PlayerController();
    ellipseMode(CENTER);
    noStroke();
}

void draw() 
{
	background(0);
    player.move();
    player.checkWalls();

    if(hasGravity)
        player.makeGravity();
}   