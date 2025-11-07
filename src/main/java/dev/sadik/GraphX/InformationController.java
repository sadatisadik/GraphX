package dev.sadik.GraphX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;

import java.awt.*;
import java.net.URI;

public class InformationController {
    @FXML Hyperlink github;

    public void visitGithub(ActionEvent e) throws Exception{
        Desktop.getDesktop().browse(new URI("https://github.com/sadatisadik"));
    }
}
