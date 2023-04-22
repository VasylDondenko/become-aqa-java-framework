package api.keycloak;

import applications.api.KeycloakApi;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.StringUtils;

import static applications.api.KeycloakApi.*;

public class KeycloakApiTests extends BaseKeycloakTest{

    @Test
    public void checkUserExists() {
        Assert.assertTrue(StringUtils.isStringContainsWord(KeycloakApi.getUserInfo(), getUsername()));
    }

    @Test
    public void checkQuantityUsersMoreThanOne() {
        Assert.assertTrue(countUsers() > 1);
    }

    @Test
    public void checkSpecificUserInUsers() {
        Assert.assertTrue(StringUtils.isStringContainsWord(KeycloakApi.getUsers().toString(), getAnotherUser()));
    }
}
