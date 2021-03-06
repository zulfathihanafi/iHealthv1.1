package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Calendar;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Front.fxml"));
        primaryStage.getIcons().add(new Image("image_iHealth\\logo.jpeg"));//create logo
        primaryStage.setTitle("iHealth v1.0");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        
    }


    public static void main(String[] args) {
        launch(args);
    }
}
