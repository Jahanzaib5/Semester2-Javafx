package AnimationTimer;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.scene.canvas.GraphicsContext;

import java.awt.*;
import java.security.SecureRandom;

public class Controller {

    @FXML Pane displayPane;
    //@FXML
    //private Canvas cv;


    private SecureRandom random = new SecureRandom();
    private int[] a;
    private int[] b;
    private GraphicsContext gc;

    @FXML
    public void initialize() {

        //gc = cv.getGraphicsContext2D();

        a = new int[50];
        b = new int[50];
        for (int i = 0; i < 50; i++) {
            Circle circle = new Circle();
            Rectangle rectangle = new Rectangle();
            rectangle.setY(random.nextInt(150));
            rectangle.setWidth(random.nextInt(150));
            rectangle.setHeight(random.nextInt(150));
            rectangle.setFill(pickRandomColor());
            rectangle.setStrokeWidth(random.nextInt(20));
            rectangle.setStroke(pickRandomColor());


            displayPane.getChildren().add(rectangle);
            a[i] = 1 + random.nextInt(5);
            b[i] = 1 + random.nextInt(5);
        }
        Timeline timelineAnimation = new Timeline(
                new KeyFrame(Duration.millis(8), e -> animateRectangles())
        );
        timelineAnimation.setCycleCount(Timeline.INDEFINITE);
        timelineAnimation.play();
    }

    private void animateRectangles() {
        for (int i = 0; i < displayPane.getChildren().size(); i++) {
            Rectangle r = (Rectangle) displayPane.getChildren().get(i);
            r.setX(r.getX() + a[i]);
            r.setY(r.getY() + b[i]);
            if (r.getX() + r.getWidth()/2 > displayPane.getWidth() || r.getX() - r.getX()/2 < 0) a[i] = -a[i];
            if (r.getY() + r.getWidth()/2 > displayPane.getHeight() || r.getY() - r.getY()/2 < 0) b[i] = -b[i];
        }
    }


    private Color pickRandomColor(){
        return Color.rgb(
                random.nextInt(256),
                random.nextInt(256),
                random.nextInt(256),
                (double) random.nextInt(101) / 100);
    }
}