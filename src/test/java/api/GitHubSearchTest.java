package api;

import applications.api.GitHubUser;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.annotations.Test;
import utils.RetrieveUtils;

import java.io.IOException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class GitHubSearchTest {

    @Test
    public void testRandomRepositoryNameDoesNotFoundOnGitHub() throws IOException {

        String name = RandomStringUtils.randomAlphabetic(8);
        HttpUriRequest request = new HttpGet( "https://api.github.com/search/repositories?q=" + name);

        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        String responseBody = EntityUtils.toString(response.getEntity());
        JSONObject json = new JSONObject(responseBody);
        int totalCount = json.getInt("total_count");
        assertEquals(totalCount, 0);
    }

    @Test
    public void testRepositoriesFoundOnGitHub() throws IOException {

        String name = "AQAJava";
        HttpUriRequest request = new HttpGet( "https://api.github.com/search/repositories?q=" + name);

        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        String responseBody = EntityUtils.toString(response.getEntity());
        JSONObject json = new JSONObject(responseBody);
        int totalCount = json.getInt("total_count");

        assertTrue(totalCount > 0);
    }
}
