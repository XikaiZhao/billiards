import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.geometry.Point2D;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import java.text.DecimalFormat;
import javafx.scene.shape.Circle;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.effect.DropShadow;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import java.util.Vector;
import javafx.scene.control.Label;
import javafx.geometry.Pos;
import javafx.scene.text.Font;

public class DrawState {
  private GameState gameState;

  private Line hitLine;
  public double hitspeed;
  public double hitangle;
  
  public Ball cueBall;
  public Button restartBtn;
  
  public boolean switchedPlayer;
  
  public DrawState(GameState state) {
    gameState = state;
    hitLine = new Line(0,0,-1,-1);
    hitspeed = 0;
    hitangle = 0;
    setupButton(); 
    
    setStartPosition();
    switchedPlayer = true;
  }

  public void setGameState(GameState state) {gameState = state;} 
  
  public void setStartPosition() {
    Vector<Ball> balls = new Vector<Ball>();
    
    Ball ball0 = new Ball(.176, 8.6, gameState.table.headSpot, Color.WHITE);

    {
      Ball ball1 = new Ball(.16, 8.6, gameState.table.footSpot, Color.YELLOW);
      balls.add(ball1);
    }
    {
      Ball ball1 = new Ball(.16, 8.6, gameState.table.footSpot.add(Math.sqrt(3)/2.*2*12, 12), Color.MAROON);
      balls.add(ball1);
      Ball ball2 = new Ball(.16, 8.6, gameState.table.footSpot.add(Math.sqrt(3)/2.*2*12, -12), Color.PURPLE);
      balls.add(ball2);
    }
    {
      Ball ball1 = new Ball(.16, 8.6, gameState.table.footSpot.add(Math.sqrt(3)/2.*4*12, 2*12), Color.MAROON);
      balls.add(ball1);
      Ball ball2 = new Ball(.16, 8.6, gameState.table.footSpot.add(Math.sqrt(3)/2.*4*12, 0), Color.BLACK);
      balls.add(ball2);
      Ball ball3 = new Ball(.16, 8.6, gameState.table.footSpot.add(Math.sqrt(3)/2.*4*12, -2*12), Color.YELLOW);
      balls.add(ball3);
    }
    {
      Ball ball1 = new Ball(.16, 8.6, gameState.table.footSpot.add(Math.sqrt(3)/2.*6*12, 3*12), Color.GREEN);
      balls.add(ball1);
      Ball ball2 = new Ball(.16, 8.6, gameState.table.footSpot.add(Math.sqrt(3)/2.*6*12, 12), Color.BLUE);
      balls.add(ball2);
      Ball ball3 = new Ball(.16, 8.6, gameState.table.footSpot.add(Math.sqrt(3)/2.*6*12, -12), Color.RED);
      balls.add(ball3);
      Ball ball4 = new Ball(.16, 8.6, gameState.table.footSpot.add(Math.sqrt(3)/2.*6*12, -3*12), Color.GREEN);
      balls.add(ball4);
    }
    {
      Ball ball1 = new Ball(.16, 8.6, gameState.table.footSpot.add(Math.sqrt(3)/2.*8*12, 4*12), Color.RED);
      balls.add(ball1);
      Ball ball2 = new Ball(.16, 8.6, gameState.table.footSpot.add(Math.sqrt(3)/2.*8*12, 2*12), Color.BLUE);
      balls.add(ball2);
      Ball ball3 = new Ball(.16, 8.6, gameState.table.footSpot.add(Math.sqrt(3)/2.*8*12, 0), Color.ORANGE);
      balls.add(ball3);
      Ball ball4 = new Ball(.16, 8.6, gameState.table.footSpot.add(Math.sqrt(3)/2.*8*12, -2*12), Color.PURPLE);
      balls.add(ball4);
      Ball ball5 = new Ball(.16, 8.6, gameState.table.footSpot.add(Math.sqrt(3)/2.*8*12, -4*12), Color.ORANGE);
      balls.add(ball5);
    }
    
    gameState.initilize(ball0, balls);
  }

  public void setHitLineStart(double x, double y) {
    hitLine.setStartX(x);
    hitLine.setStartY(y);
  }
  
  public void setHitLineEnd(double x, double y) {
    hitLine.setEndX(x);
    hitLine.setEndY(y);
  }

  public Line getHitLine() {return hitLine;}
  public void resetHitLine() {
    hitLine.setStartX(0);
    hitLine.setStartY(0);
    hitLine.setEndX(-1);
    hitLine.setEndY(-1);
  }
  
  public void drawTable(Pane canvas) {
    for (LineSegment line : gameState.table.boarders) {
      Line ln = new Line(line.getStart().getX(),line.getStart().getY(),
                         line.getEnd().getX(),line.getEnd().getY()
                        );
      canvas.getChildren().add(ln);
    }
    /*
    for (Arc arc : gameState.gameState.table.arcs) {
      arc.setFill(Color.TRANSPARENT);
      arc.setStroke(Color.BLACK);
      canvas.getChildren().add(arc);
    }
    
    double x = gameState.gameState.table.x, y = gameState.gameState.table.y;
    double r = 1.5*8.6;
    {
      Circle circle1 = new Circle(x-8.6, y-8.6, r);
      canvas.getChildren().add(circle1);
      Circle circle2 = new Circle(x-8.6, y+gameState.gameState.table.length, r);
      canvas.getChildren().add(circle2);
      Circle circle3 = new Circle(x, y+r+gameState.gameState.table.width+r, r);
      canvas.getChildren().add(circle3);
      Circle circle4 = new Circle(x+r+gameState.gameState.table.length/2.+ r, y+r+gameState.gameState.table.width+r, r);
      canvas.getChildren().add(circle4);
    }
    */

  }

  public void setupButton() {
    restartBtn = new Button("restart");
    restartBtn.setMaxWidth(200);
    restartBtn.setMaxHeight(20);
    restartBtn.setLayoutX(0);
    restartBtn.setLayoutY(gameState.table.width+400-35);
    restartBtn.setOnAction(new EventHandler<ActionEvent>() {
      @Override 
      public void handle(ActionEvent e) {
          setStartPosition();
          hitspeed = 0;
          hitangle = 0;
      }
    });
    
    DropShadow shadow = new DropShadow();
    //Adding the shadow when the mouse cursor is on
    restartBtn.addEventHandler(MouseEvent.MOUSE_ENTERED, 
        new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent e) {
                restartBtn.setEffect(shadow);
            }
    });
    //Removing the shadow when the mouse cursor is off
    restartBtn.addEventHandler(MouseEvent.MOUSE_EXITED, 
        new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent e) {
                restartBtn.setEffect(null);
            }
    });
  }

  public void drawBalls(Pane canvas) {
    int last = gameState.isCueBallPoketed ? 0 : 1;
    for (int i = 0; i < gameState.balls.size() - last; i++) {
      Color color = gameState.balls.get(i).getColor();
      Circle circle = new Circle(gameState.balls.get(i).getCenterX(), 
                                 gameState.balls.get(i).getCenterY(),
                                 gameState.balls.get(i).getRadius(), 
                                 gameState.balls.get(i).getColor());
      canvas.getChildren().add(circle);
    }
  }

  public void drawCueBall(Pane canvas) {
    if (!gameState.isCueBallPoketed) {
      Color color = gameState.balls.lastElement().getColor();
      Circle circle = new Circle(gameState.balls.lastElement().getCenterX(), 
                                 gameState.balls.lastElement().getCenterY(),
                                 gameState.balls.lastElement().getRadius(), 
                                 gameState.balls.lastElement().getColor());
      if (gameState.isAllStopped()) {
        if (!gameState.isPocketed && !switchedPlayer) {
          gameState.switchPlayer();
          switchedPlayer = true;
        };
        circle.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
          gameState.balls.lastElement().activate(true);
        });
      }
      canvas.getChildren().add(circle);
      if (gameState.balls.lastElement().isActive()) {
          canvas.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
              double x = event.getSceneX();
              double y = event.getSceneY();
              
              double d = gameState.balls.lastElement().getCenter().distance(x, y) 
                          - gameState.balls.lastElement().getRadius();
              if (d > 0) {
                Point2D dir = new Point2D(x-gameState.balls.lastElement().getCenterX(), 
                                          y-gameState.balls.lastElement().getCenterY());
                dir = dir.normalize();
                setHitLineStart(gameState.balls.lastElement().getCenterX()
                                 + gameState.balls.lastElement().getRadius()*dir.getX(), 
                                gameState.balls.lastElement().getCenterY()
                                 + gameState.balls.lastElement().getRadius()*dir.getY());
                setHitLineEnd(x, y);
                
                hitspeed = computeHitSpeed(d)/300.;

                double sign = (dir.dotProduct(0,1) > 0) ? -1 : 1;
                hitangle = dir.angle(1,0)*sign;
              }
            }
          });
        canvas.setOnMousePressed(new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
             double x = event.getSceneX();
             double y = event.getSceneY();
             double d = gameState.balls.lastElement().getCenter().distance(x, y)
                         - gameState.balls.lastElement().getRadius();
             Point2D dir = new Point2D(x - gameState.balls.lastElement().getCenterX(), 
                                       y - gameState.balls.lastElement().getCenterY());
             dir = dir.normalize();

             gameState.balls.lastElement().updateVelocity(dir.multiply(-computeHitSpeed(d)));
             gameState.balls.lastElement().activate(false);
             resetHitLine();
             switchedPlayer = false;
             gameState.isPocketed = false;
          }
        });
        canvas.getChildren().add(hitLine);
      }
    } else {
      canvas.setOnMousePressed(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
           double x = event.getSceneX();
           double y = event.getSceneY();
           Ball ball = new Ball(.16, 8.6, new Point2D(x, y), Color.WHITE);
           gameState.addCueBall(ball);
           switchedPlayer = true;
        }
      });
    }
  }

  public void showHitVelocity(Pane canvas) {
    DecimalFormat twoDForm = new DecimalFormat("0.00");
    
    Rectangle rec = new Rectangle(120,20);
    rec.setFill(Color.WHITE);
    rec.setStroke(Color.BLACK);
    Text text = new Text(twoDForm.format(hitspeed) +" m/s");
    StackPane stack = new StackPane();
    stack.getChildren().addAll(rec, text); 
    stack.setLayoutX(0);
    stack.setLayoutY(0);
    
    Rectangle rec2 = new Rectangle(120,20);
    rec2.setFill(Color.WHITE);
    rec2.setStroke(Color.BLACK);
    Text text2 = new Text(twoDForm.format(hitangle) +" degree");
    StackPane stack2 = new StackPane();
    stack2.getChildren().addAll(rec2, text2); 
    stack2.setLayoutX(0);
    stack2.setLayoutY(20);
    
    canvas.getChildren().add(stack);
    canvas.getChildren().add(stack2);
  }
  
  public void showPlayer(Pane canvas) {
    DecimalFormat twoDForm = new DecimalFormat("0.00");
    
    Rectangle rec = new Rectangle(120,20);
    rec.setFill(Color.WHITE);
    rec.setStroke(Color.BLACK);
    Text text = new Text("Player1: " + String.format("%2d", gameState.player1.score));
    StackPane stack = new StackPane();
    stack.getChildren().addAll(rec, text); 
    stack.setLayoutX(canvas.getWidth()-120);
    stack.setLayoutY(0);
    
    Rectangle rec2 = new Rectangle(120,20);
    rec2.setFill(Color.WHITE);
    rec2.setStroke(Color.BLACK);
    Text text2 = new Text("Player2: " + String.format("%2d", gameState.player2.score));
    StackPane stack2 = new StackPane();
    stack2.getChildren().addAll(rec2, text2); 
    stack2.setLayoutX(canvas.getWidth()-120);
    stack2.setLayoutY(20);
    
    String s = (gameState.player1.isTurn) ? "Player1" : "Player2";
    StackPane stack3 = new StackPane();
    Label lb = new Label(s + "'s turn");
    lb.setFont(new Font("Cambria", 16)); 
    lb.setMinWidth(120);
    lb.setAlignment(Pos.BASELINE_CENTER);
    stack3.setLayoutX(canvas.getWidth()-120);
    stack3.setLayoutY(40);
    stack3.getChildren().add(lb); 
    
    canvas.getChildren().add(stack);
    canvas.getChildren().add(stack2);
    canvas.getChildren().add(stack3);
  }
  
  private double computeHitSpeed(double length) {
    if (length < 5) return 0;
    return Math.min(length-5, 300)*13.0;
  }
}
