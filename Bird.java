package FlappyBird;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class Bird {
	int birdX;
	int birdY;
	double birdWidth;
	double birdHeight;
	ImageView birdImage;
	
	
	public Bird(String birdString, int X, int Y, double Width, double Height) {
		birdX = X;
		birdY = Y;
		birdWidth = Width;
		birdHeight = Height;
		Image FlappyBird = new Image(birdString, Width, Height, false, false);
		birdImage = new ImageView(FlappyBird);
		birdImage.setX(X);
		birdImage.setY(Y);
	}
	


}
