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