public class ParabolicMachine
{
    float xAxis;
    float yAxis;
    float pixelDividerX;
    float pixelDividerY;
    int numberOfLines;
    PGraphics curve;
        
    public ParabolicMachine(float xAxis, float yAxis, int numberOfLines)
    {
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.numberOfLines = numberOfLines;
        pixelDividerX = xAxis / numberOfLines;
        pixelDividerY = yAxis / numberOfLines;
        
    }

    void drawCurve(int width, int height)
    {
        curve = createGraphics(width, height);
        curve.beginDraw();
        for (int line = 0; line < numberOfLines; ++line) 
        {
            curve.stroke(20,20,20);
            if(line % 3 == 2)
            {
                curve.stroke(150, 0, 150);
            }

            float lineStartY = line * pixelDividerY;
            float lineStartX = 0;
            float lineEndX = pixelDividerX + (pixelDividerX * line);
            float lineEndY = pixelDividerY * numberOfLines;
            curve.line(lineStartX, lineStartY, lineEndX, lineEndY);
        }
        curve.endDraw();
        curve.save("curve.png");
    }

    void drawCircle(float diameter, float circlePointSpan, color strokeColor)
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

                line(x1 + 500 ,y1 + 500, x2 + 500, y2 + 500);
            }
        }
    }
}