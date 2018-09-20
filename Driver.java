import java.util.Vector;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.application.Application;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Line;
import javafx.scene.shape.Circle;
import javafx.animation.AnimationTimer;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;


public class Driver extends Application {
  private GameState gameState;
  private DrawState drawState;

  public static void main(String[] args) {
    Application.launch(Driver.class, args);
  }

  private void addTableBoarder(Table table) {
    double x = table.x;
    double y = table.y;
    double r = 8.6;
    double arc = 10;
    double d = arc+1.25*r; 
    double offset = Math.sqrt(2)*d-arc;
      
    {
      LineSegment line1 = new LineSegment(x, y+offset, x, y+table.height/2.-d);
      table.addBoarder(line1);
      LineSegment line2 = new LineSegment(x, y+table.height/2+d, x, y+table.height-offset);
      table.addBoarder(line2);
      LineSegment line3 = new LineSegment(x+offset, y+table.height, x+table.width/2.-d, y+table.height);
      table.addBoarder(line3);
      LineSegment line4 = new LineSegment(x+table.width/2.+d, y+table.height, x+table.width-offset, y+table.height);
      table.addBoarder(line4);
    }
    {
      LineSegment line1 = new LineSegment(x+table.width, y+offset, x+table.width, y+table.height/2.-d);
      table.addBoarder(line1);
      LineSegment line2 = new LineSegment(x+table.width, y+table.height/2.+d, x+table.width, y+table.height-offset);
      table.addBoarder(line2);
      LineSegment line3 = new LineSegment(x+offset, y, x+table.width/2.-d, y);
      table.addBoarder(line3);
      LineSegment line4 = new LineSegment(x+table.width/2.+d, y, x+table.width-offset, y);
      table.addBoarder(line4);
    }
    {
      Arc arc1 = new Arc(x-arc, y+offset, arc, arc, 0, 45);
      table.addArc(arc1);
      Arc arc2 = new Arc(x-arc, y+table.width+d, arc, arc, 0, 45);
      table.addArc(arc2);
      Arc arc3 = new Arc(x+offset, y+table.height+arc, arc, arc, 90, 45);
      table.addArc(arc3);
      Arc arc4 = new Arc(x+table.width/2.+d, y+table.height+arc, arc, arc, 90, 45);
      table.addArc(arc4);
    }
    {
      Arc arc1 = new Arc(x+table.width+arc, y+offset, arc, arc, 180, -45);
      table.addArc(arc1);
      Arc arc2 = new Arc(x+table.width+arc, y+table.width+d, arc, arc, 180, -45);
      table.addArc(arc2);
      Arc arc3 = new Arc(x+offset, y-arc, arc, arc, 270, -45);
      table.addArc(arc3);
      Arc arc4 = new Arc(x+table.width/2.+d, y-arc, arc, arc, 270, -45);
      table.addArc(arc4);
    }
    {
      Arc arc1 = new Arc(x-arc, y+table.width-d, arc, arc, 0, -45);
      table.addArc(arc1);
      Arc arc2 = new Arc(x-arc, y+table.height-offset, arc, arc, 0, -45);
      table.addArc(arc2);
      Arc arc3 = new Arc(x+table.width/2-d, y-arc, arc, arc, -90, 45);
      table.addArc(arc3);
      Arc arc4 = new Arc(x+table.width-offset, y-arc, arc, arc, -90, 45);
      table.addArc(arc4);
    }
    {
      Arc arc1 = new Arc(x+table.width+arc, y+table.width-d, arc, arc, 180, 45);
      table.addArc(arc1);
      Arc arc2 = new Arc(x+table.width+arc, y+table.height-offset, arc, arc, 180, 45);
      table.addArc(arc2);
      Arc arc3 = new Arc(x+table.width/2.-d, y+table.height+arc, arc, arc, 90, -45);
      table.addArc(arc3);
      Arc arc4 = new Arc(x+table.width-offset, y+table.height+arc, arc, arc, 90, -45);
      table.addArc(arc4);
    }
    
    
  }

  public void init() {
    double x = 200;
    double y = 200;
    Table table = new Table(x,y, 914, 457);
    addTableBoarder(table);

    /*
    Vector<Circle> holes = new Vector<Circle>();
    Circle rec1 = new Circle(x+15, table.height+y, table.width-30, y);
    rec1.setArcHeight(y);
    rec1.setArcWidth(2*y);
    boarders.add(rec1);
    Circle rec2 = new Circle(table.width+x, y+15, x, table.height-30);
    rec2.setArcHeight(2*y);
    rec2.setArcWidth(y);
    boarders.add(rec2);
    Circle rec3 = new Circle(x+15, 0, table.width-30, y);
    rec3.setArcHeight(y);
    rec3.setArcWidth(2*y);
    boarders.add(rec3);
    Circle rec4 = new Circle(0, y+15, x, table.height-30);
    rec4.setArcHeight(2*y);
    rec4.setArcWidth(y);
    boarders.add(rec4);
    table.addBoarder(boarders);
    */
    
    Player p1 = new Player(), p2 = new Player();

    gameState = new GameState(table);
    gameState.addPlayer(p1,p2);
    drawState = new DrawState(gameState);
    drawState.setGameState(gameState);
  }
  
  
  @Override
  public void start(Stage stage) {
    final long appStartNanoTime = System.nanoTime();

    VBox outerPane = new VBox();
    outerPane.setStyle("-fx-background-color: seagreen;");
    outerPane.setPrefWidth(gameState.table.height+400);
    outerPane.setPrefHeight(gameState.table.width+400);
    Pane drawingPane = new Pane();
    drawingPane.setPrefHeight(gameState.table.width+345);
    outerPane.getChildren().add(drawingPane);
    outerPane.getChildren().add(drawState.restartBtn);
    Scene scene = new Scene(outerPane, gameState.table.width+400, gameState.table.height+400, Color.SEAGREEN);
    
    new AnimationTimer() {
      long startNanoTime = appStartNanoTime;
      public void handle(long currentNanoTime){
        double t = (currentNanoTime - startNanoTime) / 1000000000.0;
        if (t < 1e-6) {return;}
        else {
          gameState.updateGameState(t);
          update(drawingPane);
        }
        startNanoTime = currentNanoTime;
      }
    }.start();
    
    stage.setScene(scene);
    stage.setTitle("Billiards");
    stage.show();
  }

  public void update(Pane canvas) {
    canvas.getChildren().clear();
    canvas.setOnMouseMoved(null);
    canvas.setOnMousePressed(null);
    drawState.drawTable(canvas);
    drawState.drawBalls(canvas);
    drawState.drawCueBall(canvas);
    drawState.showHitVelocity(canvas);
    drawState.showPlayer(canvas);
  }
}
