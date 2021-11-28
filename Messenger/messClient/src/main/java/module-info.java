module com.example.messclient {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.messclient to javafx.fxml;
    exports com.example.messclient;
}