module prototype {
    requires javafx.controls;
    requires javafx.fxml;

    opens prototype to javafx.fxml;
    exports prototype;
}
