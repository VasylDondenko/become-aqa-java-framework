package api.github;

import configs.APIGitHubConfigInitializer;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import users.GitHubUser;
import applications.api.GithubApiClient;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;
import utils.PropertyUtils;
import utils.RetrieveUtils;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

//TODO Refactor tests
public class GitHubSearchTests extends BaseGitHubTest {
    String randomName = RandomStringUtils.randomAlphabetic(8);
    String username = PropertyUtils.get("github.username");

    @Test
    public void testRandomRepositoryNameDoesNotFoundOnGitHub() {
        assertEquals(GithubApiClient.countFoundRepos(APIGitHubConfigInitializer.getSearchRepoUrl(), randomName), 0);
    }

    @Test
    public void testRepositoriesFoundOnGitHub() {
        assertTrue(GithubApiClient.countFoundRepos(APIGitHubConfigInitializer.getSearchRepoUrl(), "AQAJava") > 0);
    }

    @Test
    public void testRandomUserNameDoesNotFoundOnGitHub() {
        assertEquals(GithubApiClient.searchUser(APIGitHubConfigInitializer.getUsersUrl(), randomName).getStatusLine().getStatusCode(), HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void testCorrectUserIsFound() {
        HttpResponse response = GithubApiClient.searchUser(APIGitHubConfigInitializer.getUsersUrl(), username);
        GitHubUser resource = RetrieveUtils.retrieveResourceFromResponse(response, GitHubUser.class);

        assertEquals(resource.getLogin(), username);
    }
}
