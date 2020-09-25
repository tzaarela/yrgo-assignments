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