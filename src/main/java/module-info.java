module dev.sadik.GraphX {
    requires javafx.controls;
    requires javafx.fxml;
    requires exp4j;
    requires java.desktop;
    requires jlatexmath;
    requires javafx.swing;
    requires java.sql;


    opens dev.sadik.GraphX to javafx.fxml;
    exports dev.sadik.GraphX;
}