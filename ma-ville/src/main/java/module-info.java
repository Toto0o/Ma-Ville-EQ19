module prototype {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires com.google.gson;
    requires firebase.admin;
    requires com.google.auth;
    requires com.google.api.apicommon;
    requires google.cloud.firestore;

    opens prototype to javafx.fxml;
    exports prototype;
}
