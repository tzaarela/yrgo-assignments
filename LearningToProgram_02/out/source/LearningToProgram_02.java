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
int horizontal = 10;
int vertical = 10;
int grabberOffset = 50;

int screenSizeX;
int screenSizeY;

double grabDistance = 10;
Point grabbedPoint;
Point[][] gridPoints; 
int[] gridColors;
ArrayList<Grabber> grabbers;

PGraphics grabGraphics;
PGraphics grabGraphics2;

PImage pict;
PImage pict2;

public void setup()
{
    frameRate(300);
    screenSizeX = 800;
    screenSizeY = 800;
    grabbers = new ArrayList<Grabber>();  
    grabbedPoint = new Point(0,0);
    gridPoints = new Point[12][12];
    gridColors = new int[] { 0xffFF46FF, 0xff0402FF, 0xff46FF01 };
    surface.setSize(screenSizeX + 1, screenSizeY + 1);
    pict = loadImage("grabber.png");
    pict2 = loadImage("grabberPressed.png");

    grabGraphics = createGraphics(100, 100);
    grabGraphics2 = createGraphics(100, 100);
    strokeWeight(1);
    createPoints();
    createGrabbers();
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

            if(p.down != null)
            {
                line(p.xPos, p.yPos, p.down.xPos, p.down.yPos);
            }

            if(p.right != null)
            {
                line(p.xPos, p.yPos, p.right.xPos, p.right.yPos);
            }

            drawGrabber(p);
        }
    }
}

public void drawGrabber(Point p)
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

public void mouseReleased()
{
    grabbedPoint.grabber.isGrabbed = false;
}

public void mouseDragged()
{   
    if (grabbedPoint.grabber.isGrabbed){
        grabbedPoint.xPos = mouseX;
        grabbedPoint.yPos = mouseY;
    }
}

public Point findClosestPoint(int xPos, int yPos)
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

public void createGrabbers()
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
public class Grabber
{
    
    boolean isGrabbed;

    public Grabber()
    {
       isGrabbed = false;
    }
}
public class Point{

    int xPos;
    int yPos;
    Point up;
    Point down;
    Point right;
    Point left;
    Grabber grabber;

    public Point(int xPos, int yPos){
        this.xPos = xPos;
        this.yPos = yPos;
        grabber = new Grabber();
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
