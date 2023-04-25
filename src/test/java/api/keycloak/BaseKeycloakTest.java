package api.keycloak;

import api.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import utils.PropertyUtils;
import utils.TestContextUtils;

import java.io.IOException;

import static applications.api.KeycloakApi.*;

public class BaseKeycloakTest extends BaseTest {
    private static String realmName;

    protected static String baseUrl;
    protected static String openidConnectUrl;
    protected static String tokenUrl;
    protected static String introspectUrl;
    protected static String userInfoUrl;
    protected static String usersUrl;

    protected static String username;
    protected static String password;

    protected static String anotherUser;

    protected static String clientId;
    protected static String clientSecret;

    protected static String grantType = "password";

    protected static String[] receiveAccessTokenParams;

    protected static String[] getTokenInfoParams;

    @BeforeClass
    public void setUpClass() {
        realmName = PropertyUtils.get("keycloak.realmName", TestContextUtils.getContext());
        baseUrl = PropertyUtils.get("keycloak.baseUrl", TestContextUtils.getContext());
        openidConnectUrl = baseUrl + "/realms/" + realmName + "/protocol/openid-connect";
        tokenUrl = openidConnectUrl + "/token";
        introspectUrl = tokenUrl + "/introspect";
        userInfoUrl = openidConnectUrl + "/userinfo";
        usersUrl = baseUrl + "/admin/realms/" + realmName + "/users";
        username = PropertyUtils.get("keycloak.user.username", TestContextUtils.getContext());
        password = PropertyUtils.get("keycloak.user.password", TestContextUtils.getContext());
        anotherUser = PropertyUtils.get("keycloak.anotherUser.username", TestContextUtils.getContext());
        clientId = PropertyUtils.get("keycloak.clientID", TestContextUtils.getContext());
        clientSecret = PropertyUtils.get("keycloak.clientSecret", TestContextUtils.getContext());

        receiveAccessTokenParams = new String[] {
                "username", username,
                "password", password,
                "client_id", clientId,
                "client_secret", clientSecret,
                "grant_type", grantType};
        getTokenInfoParams = new String[] {
                "client_id", clientId,
                "client_secret", clientSecret};

        receiveAccessToken(receiveAccessTokenParams, tokenUrl);
    }

    @BeforeMethod
    public void setUp() throws IOException {
        checkExpirationTime(getValueFromJsonString("exp", getAccessTokenInfo(getTokenInfoParams, introspectUrl, tokenUrl)), getTokenInfoParams, tokenUrl);
    }
}
