package dev.sadik.GraphX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import dev.sadik.GraphX.AppController;

public class ErrorController extends AppController {
    @FXML Button Okay;
    @FXML Label lbl;

    public void initialize() {
        String message = "Please enter a valid equation. " + errorMessage;
        lbl.setText(message);
        lbl.setWrapText(true);
    }

    public void okayButton(ActionEvent e) {
        Node source = (Node) e.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
