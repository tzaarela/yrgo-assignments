import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class CollisionsAndZombies-07 extends PApplet {
  public void setup() {
public class Character
{
    float size;
    float speed;
    int ellipseColor; 
    PVector position;
    PVector direction;
    PVector velocity;

    public Character(float size, float positionX, float positionY, int ellipseColor, float speed)
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
public class CharacterManager
{
    float humanSpeed = 500;
    float humanSize = 10;
    int humanColor = color(100, 0, 0);

    float zombieSpeed = 500;
    float zombieSize = 10;
    int zombieColor = color(0, 230, 0);

    ArrayList<Character> characters;

    public CharacterManager()
    {
        characters = new ArrayList<Character>();
    }

    public void runSimulation()
    {
        for (int i = 0; i < characters.size(); ++i) 
        {
            Character character = characters.get(i);
            checkCollisions(character, i);
            character.move();
            character.screenWrap();
            character.draw();
        }
    }

    public void checkEndCondition()
    {
        if(isAllDead())
        {
            textMode(CENTER);
            textSize(30);
            text("GAME OVER", resolutionX / 2, resolutionY / 2);
            text("seconds elapsed: " + millis() / 1000, resolutionX / 2, resolutionY / 2 + 60);
        }
    }

    public boolean isAllDead()
    {
        for (Character character : characters) 
        {
            if(character instanceof Human)
                return false;
        }
        return true;
    }

    public void checkCollisions(Character character, int index)
    {
        for (int i = 0; i < characters.size(); ++i) 
        {
            Character target = characters.get(i);
            if(character.isColliding(target))
            {
                if(character instanceof Human && target instanceof Zombie)
                {
                        Zombie newZombie = ((Zombie)target).convert(character);
                        newZombie.velocity = character.velocity;
                        characters.set(index, newZombie); 
                }
                else if (character instanceof Zombie && target instanceof Human) 
                {
                        Zombie newZombie = ((Zombie)character).convert(target);
                        newZombie.velocity = target.velocity;
                        characters.set(i, newZombie); 
                }
                character.bounce(target);
            }
        }
    }

    public void spawnCharactersOfType(int amount, Class classType)
    {
        for (int i = 0; i < amount; ++i)
        {
            if (classType.equals(Human.class))
            {
                characters.add(new Human(humanSize, random(100, 1000), 
                    random(100, 600), humanColor, humanSpeed));  
            }

            else if (classType.equals(Zombie.class))
            {
                characters.add(new Zombie(zombieSize, random(100, 1000),
                    random(100, 600), zombieColor, zombieSpeed));     
            }
        }
    }
}
public class Human extends Character
{
    public Human(float size, float positionX, float positionY, int ellipseColor, float speed)
    {
        super(size, positionX, positionY, ellipseColor, speed);
    }
}
public class Zombie extends Character
{
    public Zombie(float size, float positionX, float positionY, int ellipseColor, float speed)
    {
        super(size, positionX, positionY, ellipseColor, speed);
    }

    public Zombie convert(Character human)
    {
        println("Eating brains...");
        return new Zombie(size, human.position.x, human.position.y, ellipseColor, speed);
    }
}
    noLoop();
  }

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "CollisionsAndZombies-07" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
