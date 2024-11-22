import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import prototype.Projects.Project;
import prototype.service.UserCredentialsVerifier;

public class Test3 {

    private UserCredentialsVerifier verifier;

    public Test3() {
        verifier = new UserCredentialsVerifier();
    }

    @Test
    public void projectTest1() {
        assertEquals(true, verifier.verifiyMatchingPasswords("test", "test"));
    }

    @Test
    public void projectTest2() {
        assertEquals(true, verifier.verifyBirthdayFormat("2001-11-10"));
    }

    @Test
    public void projecttest3() {
        assertEquals(true, verifier.verifyCredentials("resident1@gmail.com", "123456"));
    }

}
