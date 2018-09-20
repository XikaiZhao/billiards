import java.util.Vector;
import javafx.geometry.Point2D;
import java.lang.*;

public class GameState 
{   
    public Vector<Ball> balls;
    public final Table table;

    public boolean isCueBallPoketed;
    public boolean isPocketed;

    public Player player1;
    public Player player2;

    GameState(Table table) {
      this.table = table;
    }

    public void addCueBall(Ball cueBall) {
      for (Ball ball : balls) {
        double d = cueBall.getCenter().distance(ball.getCenter());
        if (d < ball.getRadius() + cueBall.getRadius()) {
          return; 
        }
      }

      this.balls.add(cueBall);
      isCueBallPoketed = false;
    }

    public void initilize(Ball cueBall, Vector<Ball> balls) {
      this.balls = balls;
      this.balls.add(cueBall);
      isCueBallPoketed = false;
      isPocketed = false;
      player1.isTurn = true;
      player2.isTurn = false;
      player1.score = 0;
      player2.score = 0;
    }

    public void addPlayer(Player p1, Player p2) {
      player1 = p1;
      player2 = p2;
    }
    
    public void updateGameState(double time_step) {
      for (int i = 0; i < balls.size(); i++) {
        updateBallCenter(balls.get(i), time_step);
        if (isBallOnTable(balls.get(i))) {
          for (LineSegment line : table.boarders) {
            if (collideWall(line, balls.get(i))) break;
          }
        } else {
          if (i == balls.size()-1) {
            isCueBallPoketed = true;
            player1.isTurn = !player1.isTurn;
            player2.isTurn = !player2.isTurn;
          } else {
            isPocketed = true;
            if (player1.isTurn) player1.score += 1; 
            if (player2.isTurn) player2.score += 1; 
          }
          balls.removeElementAt(i);
          i--;
        }
      }
      for (int i = 0; i < balls.size(); i++) {
        for (int j = i+1; j < balls.size(); j++) {
          collideBalls(balls.get(i), balls.get(j));         
        }
      }
      for (Ball ball : balls) {
        updateBallVelocity(ball, time_step); 
      }
    }

    private void updateBallCenter(Ball ball, double time_step) {
       Point2D c = ball.getCenter()
                  .add(ball.getVelocity().multiply(time_step));
       ball.updateCenter(c);
    }

    private void updateBallVelocity(Ball ball, double time_step) {
       double ax = (ball.getVelocity().getX() < 0) ? 1 : -1;
       double ay = (ball.getVelocity().getY() < 0) ? 1 : -1;
       
       Point2D dir = ball.getVelocity().normalize();
       Point2D v = ball.getVelocity()
                   .add(ax*Math.min(table.friction*Math.abs(dir.getX())*time_step, 
                                    Math.abs(ball.getVelocity().getX())),
                        ay*Math.min(table.friction*Math.abs(dir.getY())*time_step, 
                                    Math.abs(ball.getVelocity().getY())));
       ball.updateVelocity(v);
    }
    
    // line: P -> Q
    private boolean collideWall(LineSegment line, Ball ball) {
      Point2D c = ball.getCenter();
      Point2D normal = line.getNormal();
      Point2D startToCenter = c.subtract(line.getStart());
      Point2D proj = line.getStart()
                     .add(line.getDir().multiply(startToCenter.dotProduct(line.getDir())));
      
      double projD = proj.distance(c);
      if (projD > ball.getRadius()) return false;
      
      double minD = projD;
      Point2D dir = proj.subtract(c).normalize(); 
      // check if the projection pt is in the line
      double startProj = line.getStart().distance(proj);
      double endProj = line.getEnd().distance(proj);
      if (startProj + endProj > line.getLength() + 1e-6) {
        double startD = line.getStart().distance(c);
        double endD = line.getEnd().distance(c);
        if (startD < endD) {
          minD = startD;
          dir = line.getStart().subtract(c).normalize();
        } else {
          minD = endD;
          dir = line.getEnd().subtract(c).normalize();
        } 
      }

      // fix the center
      double diff = ball.getRadius()-minD;
      if (diff < 0) return false;
      ball.updateCenter(c.add(dir.multiply(-diff)));
      Point2D v = ball.getVelocity();
      if (startToCenter.dotProduct(normal) < 0 ){ 
        normal = normal.multiply(-1);
      }
      Point2D vn = v.subtract(normal.multiply(2*v.dotProduct(normal)));
      ball.updateVelocity(vn);
      return true;
    }
    
    private void collideBalls(Ball b1, Ball b2) {
      double diff = b1.getRadius()+b2.getRadius() 
                    - b1.getCenter().distance(b2.getCenter());
      if (diff < 0) return;

      Point2D c1 = b1.getCenter();
      Point2D c2 = b2.getCenter();
      Point2D dir = c1.subtract(c2);
      dir = dir.normalize();
      b1.updateCenter(c1.add(dir.multiply(diff/2.)));
      b2.updateCenter(c2.subtract(dir.multiply(diff/2.)));

      Point2D u1 = b1.getVelocity();
      Point2D u2 = b2.getVelocity();
      double m1 = b1.getMass();
      double m2 = b2.getMass();

      {
        Point2D d = c1.subtract(c2);
        double s = 2*m2/(m1+m2)*d.dotProduct(u1.subtract(u2))/d.dotProduct(d);
        b1.updateVelocity ( u1.subtract(d.multiply(s)) );
      }
      {
        Point2D d = c2.subtract(c1);
        double s = 2*m1/(m1+m2)*d.dotProduct(u2.subtract(u1))/d.dotProduct(d);
        b2.updateVelocity ( u2.subtract(d.multiply(s)) );
      }
    }

    public boolean isBallOnTable(Ball ball) {
      double r = ball.getRadius();
      if (ball.getCenterX() - table.x < -r/2.) return false;
      else if (table.x + table.width - ball.getCenterX() < -r/2.) return false;
      else if (ball.getCenterY() - table.y < -r/2.) return false;
      else if (table.y + table.height - ball.getCenterY() < -r/2.) return false;
      else return true;
    }
    
    public boolean isAllStopped() {
      double vel = 1;
      for (Ball ball : balls) {
        if(ball.getVelocity().distance(0,0) > 1e-6) return false;
      }
      return true;
    }

    public void switchPlayer() {
      player1.isTurn = !player1.isTurn;
      player2.isTurn = !player2.isTurn;
    }
}
