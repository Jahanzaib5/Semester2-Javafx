package alarmClock;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

class Animation {

    static void animateNode(Node node) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(1000), node);
        transition.setFromX(0);
        transition.setFromY(0);
        transition.setToX(5);
        transition.setToY(5);
        transition.playFromStart();
    }
}