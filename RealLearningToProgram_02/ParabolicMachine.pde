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

    void drawCurve(float width, float height)
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

                line(x1 + centerOfScreenX ,y1 + centerOfScreenY, x2 + centerOfScreenX, y2 + centerOfScreenY);
            }
        }
    }
}