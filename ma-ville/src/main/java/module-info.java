module prototype {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires firebase.admin;
    requires com.google.auth.oauth2;
    requires com.google.api.apicommon;
    requires google.cloud.firestore;
    requires com.google.gson;

    opens prototype to javafx.fxml;
    exports prototype;
}
