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
    requires org.apache.httpcomponents.httpclient;
    requires org.junit.jupiter.api;
    requires org.checkerframework.checker.qual;

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

    opens prototype to javafx.fxml, firebase.admin, org.junit.platform.commons;
    opens prototype.projects to firebase.admin;
    opens prototype.notifications to firebase.admin;
    opens prototype.users to firebase.admin;
}