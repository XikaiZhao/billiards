import javafx.geometry.Point2D;

public class LineSegment
{
  public Point2D start;
  public Point2D end;

  public Point2D dir;
  public Point2D normal;

  public double length;

  public LineSegment(double startx, double starty, double endx, double endy) {
    start = new Point2D(startx, starty);
    end = new Point2D(endx, endy);
    dir = end.subtract(start);
    dir = dir.normalize();
    length = start.distance(end);
    normal = new Point2D(-dir.getY(), dir.getX());
    normal = normal.normalize();
  }

  public Point2D getStart() {return start;}
  public Point2D getEnd() {return end;};
  public Point2D getDir() {return dir;}
  public Point2D getNormal() {return normal;}
  public double getLength() {return length;}
}
