package api.keycloak;

import api.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;

import static applications.api.KeycloakApi.*;

public class BaseKeycloakTest extends BaseTest {

    @BeforeClass
    public void setUpClass() {
        receiveAccessToken();
    }

    @BeforeMethod
    public void setUp() throws IOException {
        checkExpirationTime(getValueFromJsonString("exp", getAccessTokenInfo()));
    }
}
