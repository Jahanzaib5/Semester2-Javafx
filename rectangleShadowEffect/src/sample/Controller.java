package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class Controller {






    @FXML private Slider xSlider;
    @FXML private Slider ySlider;

    @FXML private TextField xTextField;
    @FXML private TextField yTextField;

    @FXML
    private AnchorPane root;


    // instance variables for managing
    private double x = -14;
    private double y = -16;




    // set user data for the RadioButtons
    public void initialize() {
        // user data on a control can be any Object
//        blackRadioButton.setUserData(Color.BLACK);
//        redRadioButton.setUserData(Color.RED);
//        greenRadioButton.setUserData(Color.GREEN);
//        blueRadioButton.setUserData(Color.BLUE);

        Rectangle r = new Rectangle();
        r.setX(130);
        r.setY(50);
        r.setWidth(300);
        r.setHeight(150);
        //r.setArcWidth(20);
        //r.setArcHeight(20);
        r.setFill(Color.GREEN);
        r.setStroke(Color.DARKGREEN);

        DropShadow dropShadow2 = new DropShadow();
        dropShadow2.setOffsetX(x);
        dropShadow2.setOffsetY(y);




        // bind TextField values to corresponding Slider values
        xTextField.textProperty().bind(
                xSlider.valueProperty().asString("%.0f"));
        yTextField.textProperty().bind(
                ySlider.valueProperty().asString("%.0f"));

        // listeners that set Rectangle's fill based on Slider changes
        xSlider.valueProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> ov,
                                        Number oldValue, Number newValue) {
                        x = newValue.intValue();
                        dropShadow2.setOffsetX(x);
                        //colorRectangle.setFill(Color.rgb(red, green, blue, alpha));
                        //brushColor = Color.rgb(red, green, blue, alpha);
                    }

                }
        );

        ySlider.valueProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> ov,
                                        Number oldValue, Number newValue) {
                        y = newValue.intValue();
                        dropShadow2.setOffsetY(y);
                        //colorRectangle.setFill(Color.rgb(red, green, blue, alpha));
                        //brushColor = Color.rgb(red, green, blue, alpha);
                    }

                }
        );








        r.setEffect(dropShadow2);
        root.getChildren().add(r);




        //brushColor = colorRectangle.getFill();//(Color) colorToggleGroup.getSelectedToggle().getUserData();
    }

}

