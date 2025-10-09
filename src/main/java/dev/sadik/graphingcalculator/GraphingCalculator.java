package dev.sadik.graphingcalculator;

//start of the main app

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class GraphingCalculator extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dev/sadik/graphingcalculator/App.fxml"));

        Parent root = loader.load();
        root.setStyle(
                "-fx-background-color: linear-gradient(from 0% 50% to 100% 50%, #3b8d99 0%, #6b6b83 50%, #aa4b6b 100%);"
        );

        Image icon = new Image(getClass().getResourceAsStream("/dev/sadik/graphingcalculator/icon.png"));

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);

        primaryStage.setTitle("GraphX");

        primaryStage.setResizable(false);

        primaryStage.getIcons().add(icon);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
