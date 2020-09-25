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

public class RealLearningToProgram_02 extends PApplet {

int resolutionX = 1000;
int resolutionY = 1000;
ParabolicMachine pm;
PImage curveImage;
PImage curveImage2;


int rotationAngle = 0;

public void setup()
{
    frameRate(300);
    pm = new ParabolicMachine(resolutionX, resolutionY, 50);
    surface.setSize(resolutionX, resolutionY);
    surface.setLocation(600, 0);
    strokeWeight(2);
    
}

public void draw()
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
public class ParabolicMachine
{
    float xAxis;
    float yAxis;
    float pixelDividerX;
    float pixelDividerY;
    float centerOfScreenX;
    float centerOfScreenY;
    int numberOfLines;
    PGraphics curve;

        
    public ParabolicMachine(float xAxis, float yAxis, int numberOfLines)
    {
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.numberOfLines = numberOfLines;
        
        centerOfScreenX = xAxis / 2;
        centerOfScreenY = yAxis / 2;
        curve = createGraphics(200, 200);
    }

    public void drawCurve(float width, float height)
    {
        float pixelDividerX = width / numberOfLines;
        float pixelDividerY = height / numberOfLines;

        for (int line = 0; line < numberOfLines; ++line) 
        {
            stroke(20,20,20);
            if(line % 3 == 0)
            {
                stroke(150, 0, 150);
            }

            float lineStartY = line * (height / numberOfLines);
            float lineStartX = 0;
            float lineEndX = line * (width / numberOfLines);
            float lineEndY = height;
            line(lineStartX, lineStartY, lineEndX, lineEndY);
        }
        
    }

    public void drawCircle(float diameter, float circlePointSpan, int strokeColor)
    {
        stroke(strokeColor);
        float radius = diameter / 2;
        float angle = 360 / numberOfLines + 1;
        
        for (int i = 0; i < numberOfLines; ++i)
        {
            if (i * angle <= 360)
            {
                float x1 = radius * cos(radians(i * angle));
                float y1 = radius * sin(radians(i * angle));

                float x2 = radius * cos(radians((i + 30) * angle));
                float y2 = radius * sin(radians((i + 30) * angle));

                line(x1 + centerOfScreenX ,y1 + centerOfScreenY, x2 + centerOfScreenX, y2 + centerOfScreenY);
            }
        }
    }
}
  public void settings() {  smooth(2); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "RealLearningToProgram_02" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
