package api.github;

import applications.api.GithubApiClient;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GitHubLoginTests extends BaseGitHubTest{

    @Test
    public void getUserInfoWithToken() {
        Assert.assertTrue(GithubApiClient.isUserHasAccessToUserPrivateInfo(userUrl, token));
    }

    @Test
    public void getAnotherUserInfo() {
        Assert.assertFalse(GithubApiClient.isUserHasAccessToUserPrivateInfo(usersUrl + "sergii-butenko-gl", token));
    }

    @Test
    public void getPrivateRepos() {
        Assert.assertTrue(GithubApiClient.countPrivateRepositories(userPrivateRepoUrl, token) > 0);
    }
}