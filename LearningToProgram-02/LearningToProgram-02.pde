import java.util.ArrayList;
import java.util.Collections;
import java.lang.Math;

int boxSize = 80;
int horizontal = 10;
int vertical = 10;
int grabberOffset = 50;

int screenSizeX;
int screenSizeY;

double grabDistance = 10;
Point grabbedPoint;
Point[][] gridPoints; 
color[] gridColors;
ArrayList<Grabber> grabbers;

PGraphics grabGraphics;
PGraphics grabGraphics2;

PImage pict;
PImage pict2;

void setup()
{
    frameRate(300);
    screenSizeX = 800;
    screenSizeY = 800;
    grabbers = new ArrayList<Grabber>();  
    grabbedPoint = new Point(0,0);
    gridPoints = new Point[12][12];
    gridColors = new color[] { #FF46FF, #0402FF, #46FF01 };
    surface.setSize(screenSizeX + 1, screenSizeY + 1);
    pict = loadImage("grabber.png");
    pict2 = loadImage("grabberPressed.png");
    grabGraphics = createGraphics(100, 100);
    grabGraphics2 = createGraphics(100, 100);
    strokeWeight(1);
    createPoints();
    createGrabbers();
}

void draw()
{
    background(0);
    drawGrid();
    debugMode();
}

void drawGrid()
{
    stroke(255,255,255);
    Point p;
    
    for (int x = 0; x <= horizontal; ++x)
    {   
        for (int y = 0; y <= vertical; ++y) 
        {   
            p = gridPoints[x][y];

            if(p.down != null)
            {
                float distBetweenPoints = dist(p.xPos, p.yPos, p.down.xPos, p.down.yPos);
                if(distBetweenPoints > 80){
                    p.xPos += 1;
                    p.yPos += 1;
                    p.down.xPos += 1;
                    p.down.yPos += 1;
                }
                
                line(p.xPos, p.yPos, p.down.xPos, p.down.yPos);
            }

            if(p.right != null)
            {
                float distBetweenPoints = dist(p.xPos, p.yPos, p.right.xPos, p.right.yPos);
                if(distBetweenPoints > 80){
                    p.xPos += 1;
                    p.yPos += 1;
                    p.right.xPos += 1;
                    p.right.yPos += 1;
                }
                line(p.xPos, p.yPos, p.right.xPos, p.right.yPos);
            }

            drawGrabber(p);
        }
    }
}

void drawGrabber(Point p)
{  
    if (p.grabber.isGrabbed)
    {
        image(pict2, p.xPos - 50, p.yPos - 50);
    }
    else
    {
        image(pict, p.xPos - 50, p.yPos - 50);
    }
}

void debugMode()
{
    showResolution();
    //showMousePosition();
}

void showMousePosition()
{
    println("MouseX: " + mouseX + " MouseY: " + mouseY);
}

void showResolution()
{
    text(String.format("Resolution: %s x %s", screenSizeX, screenSizeY), 10, 10, 10);
}

void mousePressed()
{
    Point closestPoint = findClosestPoint(mouseX, mouseY);

    if(closestPoint != null) 
    {
        println("closest X: " + closestPoint.xPos);
        println("closest Y: " + closestPoint.yPos);

        double distanceToPoint = dist(mouseX, mouseY, closestPoint.xPos, closestPoint.yPos);

        if (grabDistance >= distanceToPoint){
            grabbedPoint = closestPoint;
            grabbedPoint.grabber.isGrabbed = true;
        }
    }
}

void mouseReleased()
{
    grabbedPoint.grabber.isGrabbed = false;
}

void mouseDragged()
{   
    if (grabbedPoint.grabber.isGrabbed){
        grabbedPoint.xPos = mouseX;
        grabbedPoint.yPos = mouseY;
    }
}

Point findClosestPoint(int xPos, int yPos)
{
    float closestDistance = 0;
    Point closestPoint = new Point(0,0);

    for (int x = 0; x < horizontal; ++x)
    {
        for (int y = 0; y <= vertical; ++y)
        {
            Point p = gridPoints[x] [y];
            float distance = dist(xPos, yPos, p.xPos, p.yPos);

            if (closestDistance == 0 || distance < closestDistance)
            {
                closestDistance = distance;
                closestPoint = p;
            }
            println(distance);
        }
    }
    return closestPoint;
}

void createPoints()
{
    //2 Runs, first to create points, second to join them.  
    for (int z = 0; z < 2; z++)
    {
        for (int x = 0; x <= screenSizeX; ++x)
        {
            for (int y = 0; y <= screenSizeY; ++y)
            {
                if (x % boxSize  == 0 && y % boxSize == 0)
                {
                    println("X: " + x);
                    println("Y: " + y);
                    int gridPosX = x / boxSize;
                    int gridPosY = y / boxSize;
                    println("gridX: " + gridPosX);
                    println("gridY: " + gridPosY);
                    if(z == 0)
                    {
                        gridPoints[gridPosX][gridPosY] = new Point(x, y);
                    }
                    if(z == 1)
                    {
                        if(gridPosY > 0)
                        {
                            gridPoints[gridPosX][gridPosY].up = gridPoints[gridPosX][gridPosY - 1];
                        }
                        if (gridPosY < vertical)
                        {
                            gridPoints[gridPosX][gridPosY].down = gridPoints[gridPosX][gridPosY + 1];
                        }
                        if(gridPosX > 0)
                        {
                            gridPoints[gridPosX][gridPosY].left = gridPoints[gridPosX - 1][gridPosY];
                        }
                        if(gridPosX < horizontal)
                        {
                            gridPoints[gridPosX][gridPosY].right = gridPoints[gridPosX + 1][gridPosY];
                        }
                    }
                }
            }
        }
    }
}

void createGrabbers()
{
    grabGraphics.beginDraw();
    grabGraphics.background(0,0);
    grabGraphics.noFill();
    grabGraphics.noStroke();
    grabGraphics.fill(33,178,33);
    grabGraphics.ellipse(50,50,30,30);
    grabGraphics.filter(BLUR, 6);
    grabGraphics.fill(33,178,33);
    grabGraphics.ellipse(50,50,20,20);
    grabGraphics.endDraw();

    grabGraphics.save("grabber.png");

    grabGraphics2.beginDraw();
    grabGraphics2.background(0,0);
    grabGraphics2.noFill();
    grabGraphics2.noStroke();
    grabGraphics2.fill(228,31,22);
    grabGraphics2.ellipse(50,50,30,30);
    grabGraphics2.filter(BLUR, 6);
    grabGraphics2.fill(228,31,22);
    grabGraphics2.ellipse(50,50,20,20);
    grabGraphics2.endDraw();

    grabGraphics2.save("grabberPressed.png");
}
