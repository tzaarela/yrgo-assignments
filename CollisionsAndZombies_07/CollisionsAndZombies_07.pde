int resolutionX = 1280;
int resolutionY = 720;
CharacterManager characterManager;

float deltaTime;
float frameTime;

void setup() 
{
    surface.setSize(resolutionX, resolutionY);
    surface.setLocation(0, 0);

    characterManager = new CharacterManager();
    characterManager.spawnCharactersOfType(99, Human.class);
    characterManager.spawnCharactersOfType(1, Zombie.class);
}

void draw() 
{
    float currentTime = millis();
    deltaTime = (currentTime - frameTime) * 0.001f;

    background(0);
    characterManager.runSimulation();
    characterManager.checkEndCondition();

    frameTime = currentTime;
}

