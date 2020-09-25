public class CharacterManager
{
    float humanSpeed = 500;
    float humanSize = 10;
    color humanColor = color(100, 0, 0);

    float zombieSpeed = 500;
    float zombieSize = 10;
    color zombieColor = color(0, 230, 0);

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