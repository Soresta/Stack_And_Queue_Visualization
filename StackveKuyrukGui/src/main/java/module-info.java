module com.example.stackvekuyrukgui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.stackvekuyrukgui to javafx.fxml;
    exports com.example.stackvekuyrukgui;
}