package api.github;

import api.base.BaseTest;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import utils.PropertyUtils;

public class BaseGitHubTest extends BaseTest {

    protected String token;
    protected String username;
    protected String password;
    protected String anotherUser;
    protected String baseUrl;
    protected String userUrl;
    protected String anotherUserUrl;
    protected String usersUrl;
    protected String searchReposUrl;
    protected String userPrivateRepoUrl;
    protected String repoName = "AQAJava";
    protected String randomName = RandomStringUtils.randomAlphabetic(8);

    @BeforeClass
    public void setUp(ITestContext context) {
        token = PropertyUtils.get("github.token", context);
        username = PropertyUtils.get("github.username", context);
        password = PropertyUtils.get("github.password", context);
        anotherUser = PropertyUtils.get("github.anotherUser", context);
        baseUrl = PropertyUtils.get("github.api.baseUrl", context);
        userUrl = baseUrl + "/users/" + username;
        anotherUserUrl = baseUrl + "/users/" + anotherUser;
        usersUrl = baseUrl + "/users/";
        searchReposUrl = baseUrl + "/search/repositories?q=";
        userPrivateRepoUrl = baseUrl + "/user/repos?type=private";
    }
}
