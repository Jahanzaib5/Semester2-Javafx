package alarmClock;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Controller {

    public Label labelSeconds;
    public Label labelMinutes;
    public Label labelHours;
    public ImageView imageSeconds;
    public ImageView imageMinute;
    public ImageView imageHour;
    public ComboBox<String> comboHH;
    public ComboBox<String> comboMM;
    public Rectangle alarmRectangle;
    public Label labelInfo;

    private short currentMinute;
    private short currentHour;
    private short alarmMinute = -1;
    private short alarmHour = -1;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private boolean isTurnOff = false;

    @FXML
    protected void initialize() {
        new Thread(task1).start();
        new Thread(task2).start();

        String[] arrayMinutes = new String[61];
        String[] arrayHours = new String[25];
        arrayMinutes[0] = "none";
        arrayHours[0] = "none";

        for (int i = 1; i < 61; i++) {
            arrayMinutes[i] = i < 11 ? "0" + (i - 1) : String.valueOf(i - 1);
        }

        for (int i = 1; i < 25; i++) {
            arrayHours[i] = i < 11 ? "0" + (i - 1) : String.valueOf(i - 1);
        }

        ObservableList<String> listHours = FXCollections.observableArrayList(arrayHours);
        ObservableList<String> listMinutes = FXCollections.observableArrayList(arrayMinutes);

        comboHH.setItems(listHours);
        comboMM.setItems(listMinutes);
        comboHH.setValue(null);
        comboMM.setValue(null);
    }

    private final Task<Void> task1 = new Task<>() {   // Конструкция для правильного взаимодействия с UI потоками
        protected Void call() throws Exception {
            for (int i = 0; i < 72000; i++) {
                Thread.sleep(500);
                Platform.runLater(() -> showRealTime());
            }
            return null;
        }
    };

    private final Task<Void> task2 = new Task<>() {   // По аналогии делаем task для будильника
        protected Void call() throws Exception {
            for (int i = 0; i < 72000; i++) {
                Thread.sleep(500);
                Platform.runLater(() -> checkAlarm());
            }
            return null;
        }
    };

    private void checkAlarm() {
        if (currentHour == alarmHour && currentMinute == alarmMinute) {
            if (!isTurnOff) {
                labelInfo.setText("CLICK HERE\nTo Turn Off the Alarm");
                comboMM.setVisible(false);
                comboHH.setVisible(false);
                alarmRectangle.setVisible(false);
                Animation.animateNode(labelInfo);

            }
        }
    }

    private void showRealTime() {
        String currentTime = LocalTime.now().format(dateTimeFormatter);
        String[] strings = currentTime.split(":");
        currentHour = Short.parseShort(strings[0]);
        currentMinute = Short.parseShort(strings[1]);
        short currentSeconds = Short.parseShort(strings[2]);

        labelHours.setText(currentHour < 10 ? "0" + String.valueOf(currentHour) : String.valueOf(currentHour));
        labelMinutes.setText(currentMinute < 10 ? "0" + String.valueOf(currentMinute) : String.valueOf(currentMinute));
        labelSeconds.setText(currentSeconds < 10 ? "0" + String.valueOf(currentSeconds) : String.valueOf(currentSeconds));
    }

    public void actionAddAlarm() {
        System.out.println("Add alarm");
        comboHH.setVisible(true);
        comboMM.setVisible(true);
        comboHH.setValue(String.valueOf(currentHour));
        comboMM.setValue(String.valueOf(currentMinute + 1));
        comboMM.setValue(currentMinute + 1 < 10 ? "0" + (currentMinute + 1) : String.valueOf(currentMinute + 1));
        alarmRectangle.setVisible(true);
    }

    public void actionPlayAlarm() {
        if (comboMM.getValue() != null && comboHH.getValue() != null) {
            if (comboMM.getValue().equals("none") || comboHH.getValue().equals("none")) {
                labelInfo.setText("Alarm is turned Off!");
                comboHH.setVisible(false);
                comboMM.setVisible(false);
                alarmRectangle.setVisible(false);
                isTurnOff = true;
            } else {
                isTurnOff = false;
                alarmMinute = Short.parseShort(comboMM.getValue());

                alarmHour = Short.parseShort(comboHH.getValue());
                comboHH.setVisible(true);
                comboMM.setVisible(true);
                labelInfo.setText("Alarm has been set for: " + (alarmHour < 10 ? "0" + alarmHour : alarmHour) + ":" + (alarmMinute < 10 ? "0" + alarmMinute : alarmMinute) + ".\nCLICK HERE to turn off the Alarm");

                System.out.println("Alarm time: " + alarmHour + ":" + alarmMinute);
            }
        }
    }

    public void actionTurnOffAlarm() {
        if (!isTurnOff) {
            isTurnOff = true;
            comboHH.setValue("none");
            comboMM.setValue("none");
            labelInfo.setText("Alarm is turned off");

            System.out.println("alarm is turned off");
        }
    }
}