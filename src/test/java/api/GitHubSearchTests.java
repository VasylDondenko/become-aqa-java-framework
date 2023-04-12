package api;

import applications.api.GitHubUser;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.*;
import utils.EnvUtils;
import utils.PropertyUtils;
import utils.RetrieveUtils;

import java.io.IOException;
import java.util.Objects;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class GitHubSearchTests {

    private CloseableHttpClient httpClient;

    private String baseUrl;
    private String usersUrl;
    private String searchReposUrl;
    private String username;
    private String repoName = "AQAJava";

    private String randomName = RandomStringUtils.randomAlphabetic(8);


    @Parameters({"env"})
    @BeforeMethod
    public void setUp(@Optional("dev") String env, ITestContext context) {
        EnvUtils.setEnv(env);
        baseUrl = PropertyUtils.get("github.baseUrl", context);
        usersUrl = baseUrl + "/users/";
        searchReposUrl = baseUrl + "/search/repositories?q=";
        username = PropertyUtils.get("github.username", context);
    }

    @AfterMethod
    public void tearDown() throws IOException {
        if (Objects.nonNull(httpClient)) {
            httpClient.close();
        }
    }

    @Test
    public void testRandomRepositoryNameDoesNotFoundOnGitHub() throws IOException {
        httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(searchReposUrl + randomName);
        CloseableHttpResponse response = httpClient.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        assertEquals(statusCode, HttpStatus.SC_OK);
        JSONObject jsonResponse = new JSONObject(EntityUtils.toString(response.getEntity()));
        int totalCount = jsonResponse.getInt("total_count");
        assertEquals(totalCount, 0);
    }

    @Test
    public void testRepositoriesFoundOnGitHub() throws IOException {
        HttpUriRequest request = new HttpGet(searchReposUrl + repoName);
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        String responseBody = EntityUtils.toString(response.getEntity());
        JSONObject json = new JSONObject(responseBody);
        int totalCount = json.getInt("total_count");
        System.out.println("totalCount = " + totalCount);

        assertTrue(totalCount > 0);
    }

    @Test
    public void testRandomUserNameDoesNotFoundOnGitHub() throws IOException {
        HttpUriRequest request = new HttpGet( usersUrl + randomName);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void givenUserExists_whenUserInformationIsRetrieved_thenRetrievedResourceIsCorrect() throws IOException {
        HttpUriRequest request = new HttpGet( usersUrl + username );
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        GitHubUser resource = RetrieveUtils.retrieveResourceFromResponse(response, GitHubUser.class);

        assertEquals(resource.getLogin(), username);
    }
}
