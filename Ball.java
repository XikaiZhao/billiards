import java.util.Vector;

import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.paint.Color;

public class Ball 
{
  private final double mass;
  private final double radius;
  private Point2D center;

  private final Color color;

  private Point2D vel;

  private boolean active;

  public int number;
  public int group;;

  public Ball(double mass, double radius, Point2D center, Color color){
    this.mass = mass;
    this.radius = radius;
    this.center = center;
    this.color = color;
    vel = new Point2D(0, 0);
    active = false;
  }
  
  public double getMass() {return mass;}
  public double getRadius() {return radius;}
  public double getCenterX() {return center.getX();}
  public double getCenterY() {return center.getY();}
  public Point2D getCenter() {return center;}
  public Color getColor() {return color;}
  public Point2D getVelocity(){ return vel;}
  public double getSpeed(){ return vel.distance(0,0);}
  
  public void updateVelocity(Point2D v){ vel = v;}
  public void updateCenter(Point2D c){ center = c;}

  public void activate(boolean hit) { active = hit; }
  public boolean isActive() { return active; }

}
