module prototype {
    requires com.google.api.apicommon;
    requires com.google.auth.oauth2;
    requires firebase.admin;
    requires google.cloud.firestore;
    requires javafx.controls;
    requires javafx.graphics;
    requires org.json;
    requires com.google.auth;
    requires com.google.gson;
    requires com.google.api.services.storage;

    exports prototype;
    exports prototype.users to firebase.admin;
    opens prototype to javafx.fxml, firebase.admin;
}