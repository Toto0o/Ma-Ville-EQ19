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
    exports prototype.controllers;
    exports prototype.entraves;
    exports prototype.notifications;
    exports prototype.projects;
    exports prototype.scenes;
    exports prototype.scenes.general.consult;
    exports prototype.scenes.general.login;
    exports prototype.scenes.general.menu;
    exports prototype.scenes.general.register;
    exports prototype.scenes.general.settings;
    exports prototype.scenes.intervenant;
    exports prototype.scenes.resident;
    exports prototype.services;
    exports prototype.users;
    opens prototype to javafx.fxml, firebase.admin;
}