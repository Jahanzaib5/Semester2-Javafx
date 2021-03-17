package Canvas;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.Stack;


public class Controller {



    @FXML private Slider size;

    @FXML
    private Canvas cv;
    @FXML private Rectangle colorRectangle;

    @FXML
    private ToggleGroup tg;

    @FXML
    private ToggleButton rect, line, elipse, eraser, fill, Clear;
    @FXML private Slider redSlider;
    @FXML private Slider greenSlider;
    @FXML private Slider blueSlider;

    private double x;
    private double y;
    private GraphicsContext gc;
    private int red = 0;
    private int green = 0;
    private int blue = 0;

    private double alpha = 1.0;



    @FXML
    public void initialize() {
        gc = cv.getGraphicsContext2D();
        gc.setFill(Color.color(1, 1, 1));
        gc.fillRect(0, 0, this.cv.getWidth(), this.cv.getHeight());

        cv.setOnMouseDragged(this::drawDragged);
        cv.setOnMousePressed(this::mousePressed);
        cv.setOnMouseReleased(this::mouseReleased);
        redSlider.valueProperty().addListener(
                (ov, oldValue, newValue) -> {
                    red = newValue.intValue();
                    colorRectangle.setFill(Color.rgb(red, green, blue, alpha));
                }
        );
        greenSlider.valueProperty().addListener(
                (ov, oldValue, newValue) -> {
                    green = newValue.intValue();
                    colorRectangle.setFill(Color.rgb(red, green, blue, alpha));
                }
        );
        blueSlider.valueProperty().addListener(
                (ov, oldValue, newValue) -> {
                    blue = newValue.intValue();
                    colorRectangle.setFill(Color.rgb(red, green, blue, alpha));
                }
        );

    }

    private void mouseReleased(MouseEvent event) {
        gc.setFill(Color.rgb(red, green, blue));
        Toggle current = tg.getSelectedToggle();
        if (current == rect) {
            Point2D p2d = new Point2D(Math.min(event.getSceneX(), x), Math.min(event.getSceneY(), y));
            Point2D p2dd = new Point2D(Math.abs(event.getSceneX() - x), Math.abs(event.getSceneY() - y));
            gc.fillRect(p2d.getX(), p2d.getY(), p2dd.getX(), p2dd.getY());
        } else if (current == line) {
            gc.setLineWidth((this.size.getValue()));
            gc.setStroke(Color.rgb(red, green, blue));
            gc.strokeLine(x, y, event.getSceneX(), event.getSceneY());
        } else if (current == elipse) {
            Point2D p2d = new Point2D(Math.min(event.getSceneX(), x), Math.min(event.getSceneY(), y));
            Point2D p2dd = new Point2D(Math.abs(event.getSceneX() - x), Math.abs(event.getSceneY() - y));
            gc.fillOval(p2d.getX(), p2d.getY(), p2dd.getX(), p2dd.getY());
        } else if (current == eraser) {
            gc.setFill(Color.color(1, 1, 1));
            gc.fillRect(event.getSceneX(), event.getSceneY(), 15, 15);
        } else if (current == fill) {
            gc.setFill(Color.rgb(red, green, blue));
            flood_fill((int) event.getSceneX(), (int) event.getSceneY());
        }else if (current == Clear){
            gc.setFill(Color.color(1, 1, 1));
            gc.fillRect(0, 0, this.cv.getWidth(), this.cv.getHeight());
        }
    }


    private void flood_fill(int x, int y) {
        WritableImage wi = new WritableImage((int) this.cv.getWidth(), (int) this.cv.getHeight());
        boolean[][] visited = new boolean[(int) this.cv.getWidth()][(int) this.cv.getHeight()];
        cv.snapshot(null, wi);
        PixelReader pr = wi.getPixelReader();
        Stack<Point2D> stack = new Stack();
        Color source = pr.getColor(x, y);
        stack.add(new Point2D(x, y));

        while (!stack.isEmpty()) {
            Point2D newPosition = stack.pop();
            if (newPosition.getX() < wi.getWidth() && newPosition.getX() > 0 && newPosition.getY() < wi.getHeight() && newPosition.getY() > 0) {
                if (pr.getColor((int) newPosition.getX(), (int) newPosition.getY()).equals(source)) {
                    if (!visited[(int) newPosition.getX()][(int) newPosition.getY()]) {
                        gc.fillRect(newPosition.getX(), newPosition.getY(), 1, 1);
                        visited[(int) newPosition.getX()][(int) newPosition.getY()] = true;
                        stack.add(new Point2D(newPosition.getX() - 1, newPosition.getY()));
                        stack.add(new Point2D(newPosition.getX() + 1, newPosition.getY()));
                        stack.add(new Point2D(newPosition.getX(), newPosition.getY() - 1));
                        stack.add(new Point2D(newPosition.getX(), newPosition.getY() + 1));
                    }
                }
            }
        }
    }

    @FXML
    private void clearButtonPressed(ActionEvent event) {
        gc.setFill(Color.color(1, 1, 1));
        gc.fillRect(0, 0, this.cv.getWidth(), this.cv.getHeight());
    }


    private void mousePressed(MouseEvent event) {
        gc.setFill(Color.rgb(red, green, blue));
        Toggle current = tg.getSelectedToggle();
        if (current == null) {
            gc.fillRect(event.getSceneX(), event.getSceneY(), (size.getValue()), (size.getValue()));
        } else if (current == rect || current == line || current == elipse) {
            x = event.getSceneX();
            y = event.getSceneY();
        } else if (current == eraser) {
            gc.setFill(Color.rgb(red, green, blue));
            gc.fillRect(event.getSceneX(), event.getSceneY(), 15, 15);
        }
    }

    private void drawDragged(MouseEvent event) {
        gc.setFill(Color.rgb(red, green, blue));
        Toggle current = tg.getSelectedToggle();
        if (current == null) {
            gc.fillRect(event.getSceneX(), event.getSceneY(), (size.getValue()), (size.getValue()));
        } else if (current == eraser) {
            gc.setFill(Color.rgb(red, green, blue));
            gc.fillRect(event.getSceneX(), event.getSceneY(), 15, 15);
        }
    }







}