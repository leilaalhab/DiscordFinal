module com.example.finalproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.finalproject to javafx.fxml;
    exports com.example.finalproject;
    exports com.example.finalproject.welcome;
    opens com.example.finalproject.welcome to javafx.fxml;
    exports com.example.finalproject.myAccount;
    opens com.example.finalproject.myAccount to javafx.fxml;
}