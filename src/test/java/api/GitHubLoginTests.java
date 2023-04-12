package api;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.EnvUtils;
import utils.PropertyUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class GitHubLoginTests {

    private String token;
    private String username;
    private String password;
    private String baseUrl;
    private String userUrl = baseUrl + "/users/" + username;
    private String userPrivateReposUrl = baseUrl + "/user/repos?type=private";


    @Parameters({"env"})
    @BeforeMethod
    public void setUp(@Optional("dev") String env, ITestContext context) {
        EnvUtils.setEnv(env);
        token = PropertyUtils.get("github.token", context);
        username = PropertyUtils.get("github.username", context);
        password = PropertyUtils.get("github.password", context);
        baseUrl = PropertyUtils.get("github.baseUrl", context);
    }


    @Test
    public void getUserInfoWithToken() {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet();
        try {
            request.setURI(new URI(userUrl));
            request.setHeader(HttpHeaders.AUTHORIZATION, "token " + token);
            request.setHeader(HttpHeaders.ACCEPT, "application/vnd.github.v3+json");
            HttpEntity entity = httpClient.execute(request).getEntity();
            String response = EntityUtils.toString(entity);
            System.out.println(response);
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getPrivateReposWithToken() {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet();
        try {
            request.setURI(new URI(userPrivateReposUrl));
            request.setHeader(HttpHeaders.AUTHORIZATION, "token " + token);
            request.setHeader(HttpHeaders.ACCEPT, "application/vnd.github.v3+json");
            HttpEntity entity = httpClient.execute(request).getEntity();
            String response = EntityUtils.toString(entity);
            System.out.println(response);
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getPrivateReposWithBasicAuth() {
        String auth = username + ":" + password;

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet();
        try {
            request.setURI(new URI(userPrivateReposUrl));
            request.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + java.util.Base64.getEncoder().encodeToString(auth.getBytes()));
            request.setHeader(HttpHeaders.ACCEPT, "application/vnd.github.v3+json");
            HttpEntity entity = httpClient.execute(request).getEntity();
            String response = EntityUtils.toString(entity);
            System.out.println(response);
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }
}