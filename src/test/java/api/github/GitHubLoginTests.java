package api.github;

import applications.api.GithubApiClient;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;

public class GitHubLoginTests extends BaseGitHubTest{

    @Test
    public void getUserInfoWithToken() {
        assertTrue(GithubApiClient.isUserHasAccessToUserPrivateInfo(userUrl, token));
    }

    @Test
    public void getAnotherUserInfo() {
        assertFalse(GithubApiClient.isUserHasAccessToUserPrivateInfo(usersUrl + "sergii-butenko-gl", token));
    }

    @Test
    public void getPrivateRepos() {
        assertTrue(GithubApiClient.countPrivateRepositories(userPrivateRepoUrl, token) > 0);
    }
}