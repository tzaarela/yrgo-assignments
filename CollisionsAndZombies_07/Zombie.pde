public class Zombie extends Character
{
    public Zombie(float size, float positionX, float positionY, color ellipseColor, float speed)
    {
        super(size, positionX, positionY, ellipseColor, speed);
    }

    public Zombie convert(Character human)
    {
        println("Eating brains...");
        return new Zombie(size, human.position.x, human.position.y, ellipseColor, speed);
    }
}