import com.fasterxml.jackson.databind.ObjectMapper;
import com.gluonhq.cloudlink.client.data.RemoteFunctionBuilder;
import com.gluonhq.cloudlink.client.data.RemoteFunctionObject;
import com.gluonhq.connect.GluonObservableObject;
import com.gluonhq.connect.provider.DataProvider;
import com.gluonhq.connect.provider.RestClient;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Model;
import model.Weather;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class WeatherApp extends Application {
    private static final String API_KEY = "f708ce8b8b46c5e63a05ed23d1ebd0d2";
    private static final String CITY = "Karachi";
    private ImageView imageView;
    private Label weatherLabel;
    private Label descriptionLabel;
    private Label tempLabel;

    @Override
    public void start(Stage stage) {
        imageView = new ImageView();
        imageView.setFitHeight(100);
        imageView.setPreserveRatio(true);
        imageView.setEffect(new DropShadow());

        Label label = new Label("The weather in " + CITY);
        weatherLabel = new Label();
        descriptionLabel = new Label();
        descriptionLabel.getStyleClass().add("desc");
        tempLabel = new Label();
        tempLabel.getStyleClass().add("temp");

        VBox root = new VBox(10, label, imageView, weatherLabel, descriptionLabel, tempLabel);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 600, 400);
        scene.getStylesheets().add(WeatherApp.class.getResource("css/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("The Weather App");
        stage.show();
        retrieveWeather();
    }

    // method 1
    private void retrieveWeather() {
        try {
            String restUrl = "https://api.openweathermap.org/data/2.5/weather?appid=" + API_KEY + "&q=" + CITY;
            ObjectMapper objectMapper = new ObjectMapper();
            Model model = objectMapper.readValue(new URL(restUrl), Model.class);
            updateModel(model);
        } catch (Throwable e) {
            System.out.println("Error: " + e);
            e.printStackTrace();
        }
    }

    // method 2
//    private void retrieveWeather() {
//        GluonObservableObject<Model> weather = getWeather();
//        weather.setOnFailed(e -> System.out.println("Error: " + e));
//        weather.setOnSucceeded(e -> {
//            try {
//                updateModel(weather.get());
//            } catch (MalformedURLException malformedURLException) {
//                malformedURLException.printStackTrace();
//            } catch (URISyntaxException uriSyntaxException) {
//                uriSyntaxException.printStackTrace();
//            }
//        });
//    }
//    private GluonObservableObject<Model> getWeather() {
//        RestClient client = RestClient.create()
//                .method("GET")
//                .host("http://api.openweathermap.org/")
//                .connectTimeout(10000)
//                .readTimeout(1000)
//                .path("data/2.5/weather")
//                .header("accept", "application/json")
//                .queryParam("appid", API_KEY)
//                .queryParam("q", CITY);
//        return DataProvider.retrieveObject(client.createObjectDataReader(Model.class));
//    }

    // method 3
//    private void retrieveWeather() {
//        GluonObservableObject<Model> weather = getWeather();
//        weather.setOnFailed(e -> System.out.println("Error: " + e));
//        weather.setOnSucceeded(e -> {
//            try {
//                updateModel(weather.get());
//            } catch (MalformedURLException malformedURLException) {
//                malformedURLException.printStackTrace();
//            } catch (URISyntaxException uriSyntaxException) {
//                uriSyntaxException.printStackTrace();
//            }
//        });
//    }
//    private GluonObservableObject<Model> getWeather() {
//        RemoteFunctionObject functionObject = RemoteFunctionBuilder
//                .create("weather")
//                .param("q", CITY)
//                .object();
//        return functionObject.call(Model.class);
//    }

    private void updateModel(Model model) throws MalformedURLException, URISyntaxException {
        if (model != null) {
            if (!model.getWeather().isEmpty()) {
                Weather w = model.getWeather().get(0);
                imageView.setImage(new Image(new URL("http://openweathermap.org/img/wn/" + w.getIcon() + "@2x.png").toURI().toString()));
                weatherLabel.setText(w.getMain());
                descriptionLabel.setText(w.getDescription());
            }
            tempLabel.setText(String.format("%.2f C - %.1f%%", model.getMain().getTemp() - 273.15, model.getMain().getHumidity()));
        }
    }
}
