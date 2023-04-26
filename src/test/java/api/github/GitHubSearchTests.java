package api.github;

import users.GitHubUser;
import applications.api.GithubApiClient;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;
import utils.RetrieveUtils;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class GitHubSearchTests extends BaseGitHubTest {

    @Test
    public void testRandomRepositoryNameDoesNotFoundOnGitHub() {
        assertEquals(GithubApiClient.countFoundRepos(searchReposUrl, randomName), 0);
    }

    @Test
    public void testRepositoriesFoundOnGitHub() {
        assertTrue(GithubApiClient.countFoundRepos(searchReposUrl, repoName) > 0);
    }

    @Test
    public void testRandomUserNameDoesNotFoundOnGitHub() {
        assertEquals(GithubApiClient.searchUser(usersUrl, randomName).getStatusLine().getStatusCode(), HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void testCorrectUserIsFound() {
        HttpResponse response = GithubApiClient.searchUser(usersUrl, username);
        GitHubUser resource = RetrieveUtils.retrieveResourceFromResponse(response, GitHubUser.class);

        assertEquals(resource.getLogin(), username);
    }
}
