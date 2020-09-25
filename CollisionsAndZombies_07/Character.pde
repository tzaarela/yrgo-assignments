public class Character
{
    float size;
    float speed;
    color ellipseColor; 
    PVector position;
    PVector direction;
    PVector velocity;

    public Character(float size, float positionX, float positionY, color ellipseColor, float speed)
    {
        this.size = size;
        this.speed = speed;
        this.ellipseColor = ellipseColor;
        position = new PVector(positionX, positionY);
        direction = new PVector(random(-1, 1), random(-1, 1));
        velocity = new PVector(0,0);
        velocity.add(direction);
    }

    public void move()
    {
        position.add(velocity.copy().mult(speed * deltaTime));
    }

    public void draw()
    {
        fill(ellipseColor);
        ellipse(position.x, position.y, size, size);
    }

    public void screenWrap()
    {
        if(position.x > resolutionX)
            position.x = 1;
        else if(position.x < 0)
            position.x = resolutionX -1;
        if(position.y > resolutionY)
            position.y = 1;
        else if(position.y < 0)
            position.y = resolutionY - 1;
    }
    
    public void bounce(Character target)
    {
        this.velocity.mult(-1);
        target.velocity.mult(-1);
    }
    

    public boolean isColliding(Character target)
    {
        float maxDistance = this.size / 2 + target.size / 2;

        if(abs(this.position.x - target.position.x) > maxDistance ||  
            abs(this.position.y - target.position.y) > maxDistance)
        {
            return false;
        }
        
        else if(dist(this.position.x, this.position.y, target.position.x, target.position.y) > maxDistance)
        {
            return false;
        }
        return true;
    }
    
}