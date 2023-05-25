package api.github;

import applications.api.github.GithubApiClient;
import configs.APIGitHubConfigInitializer;
import org.testng.annotations.Test;
import utils.PropertyUtils;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

    //TODO Refactor tests
public class GitHubLoginTests extends BaseGitHubTest{

    @Test
    public void getUserInfoWithToken() {
        assertTrue(GithubApiClient
                .isUserHasAccessToUserPrivateInfo(APIGitHubConfigInitializer.getUserUrl(PropertyUtils.get("github.username")), PropertyUtils.get("github.token")));
    }

    @Test
    public void getAnotherUserInfo() {
        assertFalse(GithubApiClient
                .isUserHasAccessToUserPrivateInfo(APIGitHubConfigInitializer.getUserUrl(PropertyUtils.get("github.anotherUser")), PropertyUtils.get("github.token")));
    }

    @Test
    public void getPrivateRepos() {
        assertTrue(GithubApiClient.countPrivateRepositories(APIGitHubConfigInitializer.getCurrentUserPrivateReposUrl(), PropertyUtils.get("github.token")) > 0);
    }
}