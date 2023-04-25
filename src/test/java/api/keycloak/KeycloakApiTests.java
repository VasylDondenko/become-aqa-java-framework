package api.keycloak;

import applications.api.KeycloakApi;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.StringUtils;

import static applications.api.KeycloakApi.*;

public class KeycloakApiTests extends BaseKeycloakTest{

    @Test
    public void checkUserExists() {
        Assert.assertTrue(StringUtils.isStringContainsWord(KeycloakApi.getUserInfo(userInfoUrl), username));
    }

    @Test
    public void checkQuantityUsersMoreThanOne() {
        Assert.assertTrue(countUsers(usersUrl) > 1);
    }

    @Test
    public void checkSpecificUserInUsers() {
        Assert.assertTrue(StringUtils.isStringContainsWord(KeycloakApi.getUsers(usersUrl).toString(), anotherUser));
    }
}
