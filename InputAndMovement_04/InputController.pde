boolean isLeft, isRight, isUp, isDown;
boolean hasGravity;

void keyPressed()
{
    setInputs(keyCode, true);
}

void keyReleased()
{
    setInputs(keyCode, false);
}

void setInputs(int keyCode, boolean isPressed)
{

    switch (keyCode) {

        case 'A' :
        isLeft = isPressed;
        break;	

        case 'D' :
        isRight = isPressed;
        break;	

        case 'W' :
        isUp = isPressed;
        break;	

        case 'S' :
        isDown = isPressed;
        break;	

        case 'G' :
        {
            if(isPressed)
            hasGravity = !hasGravity;
        }
    }
    
}
