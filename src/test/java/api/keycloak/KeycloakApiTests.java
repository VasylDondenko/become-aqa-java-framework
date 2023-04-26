package api.keycloak;

import applications.api.KeycloakApi;
import configs.APIConfigInitializer;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.PropertyUtils;
import utils.StringUtils;

import static applications.api.KeycloakApi.countUsers;

public class KeycloakApiTests extends BaseKeycloakTest{
    private String realm = PropertyUtils.get("keycloak.realmName");

    @Test
    public void checkUserExists() {
        String userInfo = KeycloakApi.getUserInfo(APIConfigInitializer.getUserInfoUrl(realm));

        Assert.assertTrue(StringUtils.isStringContainsWord(userInfo, PropertyUtils.get("keycloak.user.username")));
    }

    @Test
    public void checkQuantityUsersMoreThanOne() {
        Assert.assertTrue(countUsers(APIConfigInitializer.getUsersUrl(realm)) > 1);
    }

    @Test
    public void checkSpecificUserInUsers() {
        String users = KeycloakApi.getUsers(APIConfigInitializer.getUsersUrl(realm)).toString();

        Assert.assertTrue(StringUtils.isStringContainsWord(users, PropertyUtils.get("keycloak.anotherUser.username")));
    }
}
