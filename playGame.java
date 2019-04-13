package fxPractice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class playGame {
	int StageX;
	int StageY;
	Stage gameStage;
	Group playGame;
	Group grassBlocks;
	Group pipes;
	Timeline timeline;
	HashMap<Integer, Pipe> pipeTypes;
	int score;
	Circle bird;
	int birdX;
	int birdY;
	int birdR;
	int currentPipeX;
	boolean gameStarted;
	boolean gameOver;
	
	
	public playGame(int X, int Y, Stage stage) {
		StageX = X;
		StageY = Y;
		gameStage = stage;
		playGame = new Group();
		grassBlocks = new Group();
		pipes = new Group();
		timeline = new Timeline();
		pipeTypes = new HashMap<Integer, Pipe>();
		Pipe type1 = new Pipe(false,100);
		Pipe type2 = new Pipe(false,50);
		Pipe type3 = new Pipe(true,100);
		Pipe type4 = new Pipe(true,50);
		pipeTypes.put(0, type1);
		pipeTypes.put(1, type2);
		pipeTypes.put(2, type3);
		pipeTypes.put(3, type4);
		score = 0;
		birdX = 10;
		birdY = StageY/2;
		birdR = StageY/5/2;
		bird = new Circle(birdX,birdY,birdR);
		currentPipeX = 0;
		gameStarted = false;
		gameOver = false;
	}
	
	public Scene gamePlay() {
		Label textScore = new Label();
		textScore.setText(Integer.toString(score));
		Font Arial = new Font("Arial", 30);
		textScore.setFont(Arial);
		textScore.setTextFill(Color.PALEGOLDENROD);
		textScore.setLayoutX(StageX/2);
		textScore.setLayoutY(20);
		Rectangle firstGrassBlock = new Rectangle(0,StageY-50,StageX,StageY);
		firstGrassBlock.setFill(Color.FORESTGREEN);
		firstGrassBlock.setStroke(Color.FORESTGREEN);
		bird.setFill(Color.CORAL);
		
		while (gameOver == false) {
			Rectangle currentBlock = generateNewGrassBlock();
			Rectangle currentFirstPipe = generateNewPipe(true);
			Rectangle currentSecondPipe = generateNewPipe(false);
			movePipe1(currentFirstPipe);
			movePipe2(currentSecondPipe);
			//moveGrassBlock(currentBlock);
			
			
			timeline.setCycleCount(1);
			timeline.play();
			
			
			animateBird();
			gravity();
			
			if (birdHitsPipe(currentFirstPipe.getTranslateX(),currentFirstPipe.getTranslateY(),currentSecondPipe.getTranslateX(),
					currentSecondPipe.getTranslateY()) || birdHitsGround(firstGrassBlock.getHeight())) {
				gameOver = true;
				GameOver gameOver = new GameOver(StageX, StageY, gameStage);
				gameStage.setScene(gameOver.gameOverScreen());
			}
			

			if (birdPassesPipe(currentFirstPipe.getWidth(), currentSecondPipe.getWidth())) {
				score ++;
				System.out.println("yes");
			}
			
		
			System.out.println(currentFirstPipe.getTranslateX());
			playGame.getChildren().addAll(textScore, pipes, firstGrassBlock, bird);
		}
		
			
		
		
		Scene playGameScene = new Scene(playGame, StageX, StageY, Color.ALICEBLUE);
		return playGameScene;
	}
	
	public boolean birdPassesPipe(double FirstPipeWidth, double SecondPipeWidth) {
		if (birdX > FirstPipeWidth || birdX > SecondPipeWidth) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void gravity() {
		if (gameStarted) {
			new AnimationTimer() {
				public void handle(long now) {
					bird.setTranslateX(bird.getTranslateX() + 5);
				}
			}.start();
		}
		System.out.println(bird.getLayoutX());
	}
	
	public boolean birdHitsPipe(double FirstPipeX, double FirstPipeY, double SecondPipeX, double SecondPipeY) {
		if (FirstPipeX - birdX + birdR <= 0 && FirstPipeY - birdY + birdR <= 0) {
			return true;
		}
		if (FirstPipeX - birdX + birdR <= 0 && FirstPipeY - birdY + birdR <= 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean birdHitsGround(double grassBlockHeight) {
		if (birdY + birdR - grassBlockHeight <= 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	
	public void movePipe1(Rectangle pipe1) {
		KeyValue keyValuePipe1 = new KeyValue(pipe1.xProperty(), -StageY-100);
		KeyFrame framePipe1 = new KeyFrame(Duration.millis(5000), keyValuePipe1);
		timeline.getKeyFrames().add(framePipe1);
		System.out.println(pipe1.getTranslateX());
	}
	
	public void movePipe2(Rectangle pipe2) {
		KeyValue keyValuePipe2 = new KeyValue(pipe2.xProperty(), -StageX);
		KeyFrame framePipe2 = new KeyFrame(Duration.millis(20500), keyValuePipe2);
		timeline.getKeyFrames().add(framePipe2);
	}
	
	public void moveGrassBlock(Rectangle grassBlock) {
		KeyValue keyValueGrass = new KeyValue(grassBlock.xProperty(), -StageX);
		KeyFrame frameGrassBlock = new KeyFrame(Duration.millis(10000), keyValueGrass);
		timeline.getKeyFrames().add(frameGrassBlock);
	}
	
	public Rectangle generateNewGrassBlock() {
		Rectangle grassBlock = new Rectangle(StageX,StageY-50,StageX*2,StageY);
		grassBlock.setFill(Color.FORESTGREEN);
		grassBlock.setStroke(Color.FORESTGREEN);
		grassBlocks.getChildren().add(grassBlock);
		return grassBlock;
	}
	
	public void animateBird() {
		EventHandler moveBird = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent click) {
				Timeline timeline = new Timeline();
				timeline.setCycleCount(2);
				timeline.setAutoReverse(true);
				KeyValue kv = new KeyValue(bird.centerYProperty(), StageY/4,
				 Interpolator.EASE_BOTH);
				KeyFrame kf = new KeyFrame(Duration.millis(500), kv);
				System.out.println(bird.getTranslateY());
				timeline.getKeyFrames().add(kf);
				timeline.play();
			}
		};
		
		bird.addEventFilter(MouseEvent.MOUSE_CLICKED, moveBird);
		gameStarted = true;
	}
	
	public Rectangle generateNewPipe(boolean firstPipe) {
		Random rand = new Random();
		int pipeType = rand.nextInt(4);
		Pipe selectedPipe = pipeTypes.get(pipeType);
		int y = 0;
		int height = y + selectedPipe.pipeHeight;
		if (selectedPipe.topPipe == false) {
			y = StageY - selectedPipe.pipeHeight - 50;
			height = StageX/2;
		}
		int x = StageX+(StageX/3);
		if (firstPipe == false) {
			x = StageX+2*(StageX/3);
		}
		Rectangle pipe = new Rectangle(x,y,selectedPipe.width,height);
		pipe.setFill(Color.DARKGREEN);
		pipe.setStroke(Color.DARKGREEN);
		pipes.getChildren().add(pipe);
		return pipe;
	}

	

}
