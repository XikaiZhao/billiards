import java.util.Vector;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Line;
import javafx.scene.shape.Circle;
import javafx.geometry.Point2D;

public class Table 
{ 
  public final double x;
  public final double y;
  public final double width;
  public final double height;
  
  public final Point2D centreSpot;
  public final Point2D headSpot;
  public final Point2D footSpot;
  
  public static final double friction = 600;

  public Vector<LineSegment> boarders;
  public Vector<Arc> arcs;
  public Vector<Circle> holes;

  public Table(double x, double y, double width, double height){
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;

    centreSpot = new Point2D(x +   width/2., y + height/2.); 
    headSpot   = new Point2D(x + 1*width/4., y + height/2.); 
    footSpot   = new Point2D(x + 3*width/4., y + height/2.); 
    
    boarders = new Vector<LineSegment>();
    arcs = new Vector<Arc>();
  }
  
  // cct
  public void addBoarder(LineSegment boarder) {
    boarders.add(boarder);
  }
  public void addArc(Arc arc) {
    arcs.add(arc);
  }
  
  
  public void addHoles(Circle hole) {
    holes.add(hole); 
  }

  
}
