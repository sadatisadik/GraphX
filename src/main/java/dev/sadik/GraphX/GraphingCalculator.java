package dev.sadik.GraphX;

//start of the main app

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.util.Objects;

public class GraphingCalculator extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dev/sadik/GraphX/App.fxml"));

        Parent root = loader.load();

        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/dev/sadik/GraphX/icon.png")));

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);

        primaryStage.setTitle("GraphX - A Graphing Calculator By Sadati Sadik Pranto");

        primaryStage.setResizable(false);

        primaryStage.getIcons().add(icon);

        primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
        });

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
