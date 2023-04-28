package api.keycloak;

import base.BaseTest;
import configs.APIConfigInitializer;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import utils.PropertyUtils;

import java.io.IOException;

import static applications.api.KeycloakApi.getAccessTokenInfo;
import static applications.api.KeycloakApi.receiveAccessToken;
import static applications.api.KeycloakApi.getValueFromJsonString;
import static applications.api.KeycloakApi.getNewAccessTokenIfExpired;

public class BaseKeycloakTest extends BaseTest {
    String realm = PropertyUtils.get("keycloak.realmName");


    @BeforeClass
    public void setUpClass() {
        receiveAccessToken(
                APIConfigInitializer.getAccessTokenGetParams(),
                APIConfigInitializer.getTokenUrl(realm));
    }

    @BeforeMethod
    public void setUp() throws IOException {
        String[] tokenInfoParameters = APIConfigInitializer.getTokenInfoParams();
        String tokenUrl = APIConfigInitializer.getTokenUrl(PropertyUtils.get("keycloak.realmName"));
        String accessTokenInfo = getAccessTokenInfo(tokenInfoParameters, APIConfigInitializer.getIntrospectUrl(realm), tokenUrl);
        String expirationTime = getValueFromJsonString("exp", accessTokenInfo);
        getNewAccessTokenIfExpired(expirationTime, tokenInfoParameters, tokenUrl);
    }
}
