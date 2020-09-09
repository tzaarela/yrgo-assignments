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