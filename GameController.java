package FlappyBird;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameController extends Application {
	Stage gameStage;
	Group root;
	int StageX;
	int StageY;
	
	public GameController() {
		gameStage = new Stage();
		root = new Group();
		StageX = 500;
		StageY = 400;
	}

	@Override
	public void start(Stage stage){
		stage.setTitle("Flappy Bird");
		gameStage.setScene(welcomeScreen());
		gameStage.show();
	}

	
	public Scene welcomeScreen() {
		Image skyAndGrass = new Image("FlappyBird/welcomeScreenBackground.jpg");
		BackgroundImage skyAndGrass1 = new BackgroundImage(skyAndGrass, BackgroundRepeat.NO_REPEAT,  
                BackgroundRepeat.NO_REPEAT,  
                BackgroundPosition.DEFAULT,  
                   BackgroundSize.DEFAULT);
		Image cloud1 = new Image("FlappyBird/cloud3.png", 100.0, 50.0, false, false);
		Image cloud2 = new Image("FlappyBird/cloud3.png", 100.0, 50.0, false, false);
		ImageView img1 = new ImageView(cloud1);
		img1.setX(-50);
		img1.setY(StageY/3);
		ImageView img2 = new ImageView(cloud2);
		img2.setX(StageX+50);
		img2.setY(StageY/4);
		Timeline moveCloud = new Timeline();
		moveCloud.setCycleCount(moveCloud.INDEFINITE);
		KeyValue moveCloud1Across = new KeyValue(img1.xProperty(), StageX+50);
		KeyFrame moveCloud1ToRight = new KeyFrame(Duration.millis(10000), moveCloud1Across);
		KeyValue moveCloud2Across = new KeyValue(img2.xProperty(), -50);
		KeyFrame moveCloud2ToRight = new KeyFrame(Duration.millis(10000), moveCloud2Across);
		moveCloud.getKeyFrames().addAll(moveCloud1ToRight, moveCloud2ToRight);
		moveCloud.play();
		Background welcomeScreenBackground = new Background(skyAndGrass1);
		Button startButton = new Button();
		startButton.setText("Play");
		startButton.setLayoutX(StageX/2-10);
		startButton.setLayoutY(StageY/2);
		startButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				System.out.println("Transitioning to game play mode...");
				gameStage.setScene(playGame());
			}
		});
		Label welcomeText = new Label();
		welcomeText.setText("Welcome to Flappy Bird!");
		Font Arial = new Font("Arial", 30);
		welcomeText.setFont(Arial);
		welcomeText.setTextFill(Color.PALEGOLDENROD);
		welcomeText.setLayoutX(StageX/5);
		welcomeText.setLayoutY(20);
		Pane welcomeScreen = new Pane();
		welcomeScreen.setBackground(welcomeScreenBackground);
		welcomeScreen.getChildren().addAll(welcomeText,startButton,img1,img2);
		Scene welcomeScreenScene = new Scene(welcomeScreen, StageX, StageY);
		return welcomeScreenScene;
	}
	
	public Scene gameOverScreen() {
		Label gameOverText = new Label("Game Over!");
		Font Arial = new Font("Arial", 30);
		gameOverText.setFont(Arial);
		gameOverText.setTextFill(Color.PALEGOLDENROD);
		gameOverText.setLayoutX(StageX/5);
		gameOverText.setLayoutY(20);
		
		Button playAgain = new Button();
		playAgain.setText("Play Again");
		playAgain.setLayoutX(StageX/2-10);
		playAgain.setLayoutY(StageY/2);
		playAgain.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				System.out.println("Transitioning to game play mode...");
				gameStage.setScene(playGame());
			}
		});
		Group gameOver = new Group();
		gameOver.getChildren().addAll(gameOverText, playAgain);
		Scene gameOverScene = new Scene(gameOver, StageX, StageY, Color.ALICEBLUE);
		return gameOverScene;
	}
	
	public Scene playGame() {
		Bird flappyBird = new Bird("FlappyBird/FlappyBird.jpg", 50, StageY/2, 50.0, 50.0);
		Group playGame = new Group();
		playGame.getChildren().add(flappyBird.birdImage);
		Scene playGameScene = new Scene(playGame, StageX, StageY, Color.ALICEBLUE);
		return playGameScene;
	}

	
	public static void main(String[] args) {
		launch();
	}
		
	

}
