module com.example.mess {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.mess to javafx.fxml;
    exports com.example.mess;
}