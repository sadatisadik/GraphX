module dev.sadik.graphingcalculator {
    requires javafx.controls;
    requires javafx.fxml;
    requires exp4j;
    requires java.desktop;
    requires javafx.swing;
    requires jlatexmath;


    opens dev.sadik.graphingcalculator to javafx.fxml;
    exports dev.sadik.graphingcalculator;
}