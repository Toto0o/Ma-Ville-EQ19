package protoype.test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.beans.Transient;

import org.junit.jupiter.api.Test;

import com.sun.jdi.connect.LaunchingConnector;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import prototype.Scenes.LaunchScene;
import prototype.Scenes.LoginScene;
import prototype.Scenes.RoleSelectionScene;

public class Test2 {

    LoginScene login;
    LaunchScene launch;
    RoleSelectionScene roleSelection;
    Scene scene;

    public Test2() {
        BorderPane root = new BorderPane();
        scene = new Scene(root);
    }

    @Test
    public void testLogin() {
        assertEquals(scene, login.getScene());
    }

    @Test
    public void testLaunch() {
        assertEquals(scene, launch.getScene());
    }

    @Test
    public void testRoleSelection() {
        assertEquals(scene, roleSelection.getScene());
    }

}
