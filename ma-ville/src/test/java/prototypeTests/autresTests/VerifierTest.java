package prototypeTests.autresTests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import prototype.controllers.ApiController;
import prototype.users.Address;
import prototype.users.UserCredentialsVerifier;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class VerifierTest {

    private static UserCredentialsVerifier verifier;
    private static final String postalCode = "h2s2b8";

    @BeforeAll
    public static void init() throws Exception {
        ApiController controller = mock(ApiController.class);
        when(controller.getCity("h2s2b8")).thenReturn("Montreal");
        when(controller.getCity("notavalidpostlcoade")).thenReturn("notinMontral");
        verifier = new UserCredentialsVerifier(controller);
    }

    @Test
    public void verifyMatchingPassword() {
        String password1 = "password";
        String password2 = "notthesamepassword";

        verifier.verifiyMatchingPasswords(password1, password1);
        assertThrows(IllegalArgumentException.class, () -> verifier.verifiyMatchingPasswords(password1, password2));
    }

    @Test
    public void verifyBirthday() {
        LocalDate date1 = LocalDate.of(2020, 1, 1);
        LocalDate date2 = LocalDate.of(2000, 1, 2);

        verifier.verifyBirthday(date2);
        assertThrows(IllegalArgumentException.class, () -> verifier.verifyBirthday(date1));
    }

    @Test
    public void verifyAddress() {
        Address address = mock(Address.class);
        when(address.getPostalCode()).thenReturn("h2s2b8");
        Address addressInvalid = mock(Address.class);
        when(addressInvalid.getPostalCode()).thenReturn("notavalidpostlcoade");
        verifier.verifyAdress(address);
        assertThrows(IllegalArgumentException.class, () -> verifier.verifyAdress(addressInvalid));
    }
}
