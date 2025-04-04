
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Ellipse;
import javafx.scene.layout.HBox;
import javafx.scene.control.Alert;

public class TicTacToe extends Application {
  // Indicate which player has a turn, initially it is the X player
  private char whoseTurn = 'X';

  // Create and initialize cell
    private Cell[][] cell = new Cell[5][5]; //change to 5x5

  // Create and initialize a status label
  private Label lblStatus = new Label("X's turn");
                

  @Override 
  public void start(Stage primaryStage) {
    // Pane to hold cell
    GridPane pane = new GridPane(); 
    for (int i = 0; i < 5; i++)
      for (int j = 0; j < 5; j++)
        pane.add(cell[i][j] = new Cell(), j, i);

    HBox statusBox = new HBox(lblStatus);
    statusBox.setAlignment(Pos.CENTER);

    lblStatus.setAlignment(Pos.CENTER);
    
    
    BorderPane borderPane = new BorderPane();
    borderPane.setCenter(pane);
  
    lblStatus.setStyle("-fx-font-size: 20px; -fx-text-fill: blue; -fx-font-weight: bold;");

    borderPane.setBottom(statusBox);
    
    // Create a scene and place it in the stage
    Scene scene = new Scene(borderPane, 500, 500);
    primaryStage.setTitle("TicTacToe"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage   
  }

  /** Determine if the cell are all occupied */
  public boolean isFull() {
    for (int i = 0; i < 5; i++)
      for (int j = 0; j < 5; j++)
        if (cell[i][j].getToken() == ' ')
          return false;

    return true;
  }

  /** Determine if the player with the specified token wins */
  public boolean isWon(char token) {
        // Check rows
        for (int i = 0; i < 5; i++) {
          for (int j = 0; j <= 0; j++) { 
            if (cell[i][j].getToken() == token &&
                cell[i][j+1].getToken() == token &&
                cell[i][j+2].getToken() == token &&
                cell[i][j+3].getToken() == token &&
                cell[i][j+4].getToken() == token)
              return true;
          }
        }
      
        // Check columns
        for (int j = 0; j < 5; j++) {
          for (int i = 0; i <= 0; i++) {
            if (cell[i][j].getToken() == token &&
                cell[i+1][j].getToken() == token &&
                cell[i+2][j].getToken() == token &&
                cell[i+3][j].getToken() == token &&
                cell[i+4][j].getToken() == token)
              return true;
          }
        }
      
        // check diagonal down-right 
        for (int i = 0; i <= 0; i++) {
          for (int j = 0; j <= 0; j++) {
            if (cell[i][j].getToken() == token &&
                cell[i+1][j+1].getToken() == token &&
                cell[i+2][j+2].getToken() == token &&
                cell[i+3][j+3].getToken() == token &&
                cell[i+4][j+4].getToken() == token)
              return true;
          }
        }
    
        // check diagonal up-right
        for (int i = 4; i >= 4; i--) {
          for (int j = 0; j <= 0; j++) {
            if (cell[i][j].getToken() == token &&
                cell[i-1][j+1].getToken() == token &&
                cell[i-2][j+2].getToken() == token &&
                cell[i-3][j+3].getToken() == token &&
                cell[i-4][j+4].getToken() == token)
              return true;
          }
        }
      
        return false;
   }

  // An inner class for a cell
  public class Cell extends Pane {
    // Token used for this cell
    private char token = ' ';

    public Cell() {
      setStyle("-fx-border-color: black"); 
      this.setPrefSize(800, 800);
      this.setOnMouseClicked(e -> handleMouseClick());
    }

    /** Return token */
    public char getToken() {
      return token;
    }

    /** Set a new token */
    public void setToken(char c) {
      token = c;
      
      if (token == 'X') {
        Line line1 = new Line(10, 10, 
          this.getWidth() - 10, this.getHeight() - 10);
        line1.endXProperty().bind(this.widthProperty().subtract(10));
        line1.endYProperty().bind(this.heightProperty().subtract(10));
        Line line2 = new Line(10, this.getHeight() - 10, 
          this.getWidth() - 10, 10);
        line2.startYProperty().bind(
          this.heightProperty().subtract(10));
        line2.endXProperty().bind(this.widthProperty().subtract(10));
        
        // Add the lines to the pane
        this.getChildren().addAll(line1, line2); 
      }
      else if (token == 'O') {
        Ellipse ellipse = new Ellipse(this.getWidth() / 2, 
          this.getHeight() / 2, this.getWidth() / 2 - 10, 
          this.getHeight() / 2 - 10);
        ellipse.centerXProperty().bind(
          this.widthProperty().divide(2));
        ellipse.centerYProperty().bind(
            this.heightProperty().divide(2));
        ellipse.radiusXProperty().bind(
            this.widthProperty().divide(2).subtract(10));        
        ellipse.radiusYProperty().bind(
            this.heightProperty().divide(2).subtract(10));   
        ellipse.setStroke(Color.BLACK);
        ellipse.setFill(Color.WHITE);
        
        getChildren().add(ellipse); // Add the ellipse to the pane
      }
    }

    /* Handle a mouse click event */
    private void handleMouseClick() {
      // If cell is empty and game is not over
      if (token == ' ' && whoseTurn != ' ') {
        setToken(whoseTurn); // Set token in the cell

        // Check game status
        if (isWon(whoseTurn)) {
          showAlert(whoseTurn + " won! The game is over");
          resetGame();
        }
        else if (isFull()) {
          showAlert("Draw! The game is over");
          resetGame();
        }
        else {
          // Change the turn
          whoseTurn = (whoseTurn == 'X') ? 'O' : 'X';
          // Display whose turn
          lblStatus.setText(whoseTurn + "'s turn");
        }
      }
    }
  }

  public void showAlert(String message) {       // method to show alert instead of updating label
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Game Over");
    alert.setHeaderText(message);
    alert.setContentText("Press OK to continue playing");
    alert.showAndWait();
  }

  private void resetGame() {  // method to reset the game
    for (int i = 0; i < 5; i++) {
        for (int j = 0; j < 5; j++) {
            cell[i][j].token = ' ';
            cell[i][j].getChildren().clear();
        }
    }
    whoseTurn = 'X';
    lblStatus.setText("X's turn to play"); // Optional if you still use label
}
  
  public static void main(String[] args) {
    launch(args);
  }
}