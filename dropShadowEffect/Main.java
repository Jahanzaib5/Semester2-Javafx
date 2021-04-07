package dropShadow;

import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
public class Main extends Application {
    public void start(Stage stage) throws FileNotFoundException {



        //Creating a text object
        String str = "JavaFX";
        Text text = new Text(30.0, 80.0, str);
        //Setting the font
        //Font font = Font.font("Brush Script MT", FontWeight.BOLD, FontPosture.REGULAR, 65);
        text.setFont(Font.font(null, FontWeight.BOLD, 40));
        //Setting the color of the text
        text.setFill(Color.BROWN);
        //Setting the width and color of the stroke
        text.setStrokeWidth(2);
        text.setStroke(Color.BLUE);
        //Setting the deep shadow effect to the text
        DropShadow dropShadow2 = new DropShadow();
        dropShadow2.setOffsetX(6.0);
        dropShadow2.setOffsetY(4.0);
        text.setEffect(dropShadow2);


        //DropShadow shadow = new DropShadow();
        //text.setEffect(shadow);




        //Setting the stage
        Group root = new Group(text);
        Scene scene = new Scene(root, 200, 150, Color.BEIGE);
        stage.setTitle("Drop Shadow Effect");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String args[]){
        launch(args);
    }
}