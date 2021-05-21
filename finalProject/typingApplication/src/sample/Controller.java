package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.util.Duration;


import java.net.URL;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class Controller implements Initializable {


    @FXML    private TextArea textReader;
    @FXML    private ProgressBar TimeBar;
    @FXML    private TextArea textWriter;


    String text1 = "This is a simple paragraph" +
            " that is meant to be nice and easy to type which is why " +
           "there will be commas no periods or any capital letters so i guess this means that it " +
                   "cannot really be considered a paragraph but just a series of run on sentences this should " +
            "help you get faster at typing as im trying not to use too many difficult words in it although " +
            "i think that i might start making it hard by including some more difficult " +
            "letters I'm typing pretty quickly so forgive me for any mistakes i think that i " +
            "will not just tell you a story about the time";
    String[] ReaderLen = text1.split(" ");
    double max = 100.0;
    double min = 0.0;
    int  wordCount= 0;
    Timeline timeline = new Timeline();

    @Override
    public void initialize(URL url, ResourceBundle rb){

        textReader.setText(text1);
        textReader.setWrapText(true);

        textWriter.setOnKeyPressed((KeyEvent event)-> {

            if (event.getCode() == KeyCode.SPACE) {
                String[] WriterLen = textWriter.getText().split(" ");

                if(Arrays.equals(ReaderLen, WriterLen)){
                    StartPressed(null);
                    timeline.stop();
                    min=max;
                    TimeBar.setProgress(0.0);
                    completed();
                }else {
                    if (Arrays.equals(WriterLen, Arrays.copyOfRange(ReaderLen, 0, WriterLen.length))) {

                        int wordLen = 0;
                        for (int i = 0; i < WriterLen.length; i++) {
                            wordLen += ReaderLen[i].length();
                        }
                        textReader.selectRange(WriterLen.length + wordLen, ReaderLen[WriterLen.length].length() + wordLen + WriterLen.length);
                        wordCount += 1;
                }
            } }


        });


    }

    @FXML
    void StartPressed(ActionEvent event) {
        min = 0;
        textWriter.setText("");
        timeline = new Timeline(new KeyFrame(Duration.millis(1000),
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        min += 1.0;
                        TimeBar.setProgress(min / max);
                        if (TimeBar.getProgress()==1.0){

                            timesUp();
                            TimeBar.setProgress(0.0);
                            min=0;
                            timeline.stop();

                        }
                    }
                }
        ));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        textWriter.setEditable(true);
        textReader.selectRange(0, ReaderLen[0].length());
    }

    void timesUp(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Times up");
        alert.setHeaderText(null);
        alert.setContentText("Times up");
        alert.setContentText("WPM: "+wordCount/(max/60));
        alert.show();
    }

    void completed(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Completed");
        alert.setHeaderText(null);
        alert.setContentText("Completed");
        alert.setContentText("WPM: "+wordCount/(max/60));
        alert.show();
    }


}
