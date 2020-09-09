import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.ArrayList; 
import java.util.Collections; 
import java.lang.Math; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class LearningToProgram_02 extends PApplet {





int boxSize = 80;
int horizontal = 5;
int vertical = 5;
int grabberOffset = 50;

int screenSizeX;
int screenSizeY;

double grabDistance = 5;
Point grabbedPoint;
Point[][] gridPoints; 
int[] gridColors;

public void setup()
{
    frameRate(300);
    screenSizeX = 400;
    screenSizeY = 400;
    grabbedPoint = new Point(0,0, new PGraphics());
    gridPoints = new Point[6][6];
    gridColors = new int[] { 0xffFF46FF, 0xff0402FF, 0xff46FF01 };
    surface.setSize(screenSizeX + 1, screenSizeY + 1);
    strokeWeight(1);
    createPoints();
}

public void draw()
{
    background(0);
    drawGrid();
    debugMode();
}

public void drawGrid()
{
    stroke(255,255,255);
    Point p;
    
    for (int x = 0; x <= horizontal; ++x)
    {   
        for (int y = 0; y <= vertical; ++y) 
        {   
            p = gridPoints[x][y];

            if(p.up != null)
            {
                line(p.xPos, p.yPos, p.up.xPos, p.up.yPos);
            }

            if(p.down != null)
            {
                line(p.xPos, p.yPos, p.down.xPos, p.down.yPos);
            }

            if(p.left != null)
            {
                line(p.xPos, p.yPos, p.left.xPos, p.left.yPos);
            }

            if(p.right != null)
            {
                line(p.xPos, p.yPos, p.right.xPos, p.right.yPos);
            }

            drawGrabber(p);
        }
    }
}

public void drawGrabber(Point point)
{   
    int xPos = point.xPos - grabberOffset;
    int yPos = point.yPos - grabberOffset;
    PGraphics grabber = point.grabber;

    image(grabber,xPos,yPos);
}

public void debugMode()
{
    showResolution();
    //showMousePosition();
}

public void showMousePosition()
{
    println("MouseX: " + mouseX + " MouseY: " + mouseY);
}

public void showResolution()
{
    text(String.format("Resolution: %s x %s", screenSizeX, screenSizeY), 10, 10, 10);
}

public void mousePressed()
{
    Point closestPoint = findClosestPoint(mouseX, mouseY);

    println("closest X: " + closestPoint.xPos);
    println("closest Y: " + closestPoint.yPos);

    double distanceToPoint = dist(mouseX, mouseY, closestPoint.xPos, closestPoint.yPos);

    if (grabDistance >= distanceToPoint){
        grabbedPoint = closestPoint;
    }
}

public void mouseReleased()
{
    grabbedPoint = null;
}

public void mouseDragged()
{   
    if (grabbedPoint != null){
        grabbedPoint.xPos = mouseX;
        grabbedPoint.yPos = mouseY;
    }
}

public Point findClosestPoint(int xPos, int yPos)
{
    float closestDistance = 0;
    Point closestPoint = new Point(0,0, null);

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

public void createPoints()
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
                        // PGraphics grabber = 
                        gridPoints[gridPosX][gridPosY] = new Point(x, y, createGrabber(x, y));
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

public PGraphics createGrabber(int xPos, int yPos)
{
    
    PGraphics grabber = createGraphics(100, 100);

    println("grab X: " + xPos);
    println("grab Y: " + yPos);

    grabber.beginDraw();
    grabber.background(0,0,0,0);
    grabber.noFill();
    grabber.noStroke();  
    grabber.fill(33,178,33);
    grabber.ellipse(xPos - 30,yPos- 30,40,40);
    grabber.filter(BLUR, 6);
    grabber.fill(33,178,33);
    grabber.ellipse(xPos - 30,yPos -30,20,20);

    grabber.endDraw();
    
    return grabber;
}

public class Point{

    int xPos;
    int yPos;
    Point up;
    Point down;
    Point right;
    Point left;

    PGraphics grabber;

    public Point(int xPos, int yPos, PGraphics grabber){
        this.xPos = xPos;
        this.yPos = yPos;
        this.grabber = grabber;
    }
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "LearningToProgram_02" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
