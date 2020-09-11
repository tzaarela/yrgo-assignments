int resolutionX = 1000;
int resolutionY = 1000;
ParabolicMachine pm;
PImage curveImage;
PImage curveImage2;


int rotationAngle = 0;

void setup()
{
    frameRate(300);
    pm = new ParabolicMachine(resolutionX, resolutionY, 50);
    surface.setSize(resolutionX, resolutionY);
    surface.setLocation(600, 0);
    strokeWeight(2);
    smooth(2);
}

void draw()
{   
    background(0);

    pm.drawCircle(200, 45, color(150, 0, 150));
    pm.drawCircle(500, 45, color(20, 20, 20));
    pm.drawCircle(700, 35, color(100, 0, 150));

    translate(500, 500);
    rotate(radians(90 + rotationAngle));
    pm.drawCurve(200, 200);
    scale(1,-1);
    pm.drawCurve(200, 200);
    rotate(radians(180));
    pm.drawCurve(200, 200);
    scale(1,-1);
    pm.drawCurve(200, 200);

    rotationAngle += 1;

}
