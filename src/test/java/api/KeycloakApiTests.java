package api;

import applications.api.KeycloakApi;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.*;
import org.w3c.dom.ls.LSOutput;
import utils.EnvUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class KeycloakApiTests extends KeycloakApi {


//    @Parameters({"env"})
    @BeforeClass
    public void setUpClass() throws IOException {
        System.out.println(System.getProperty("env"));
        EnvUtils.setEnv(System.getProperty("env"));
        receiveAccessToken();
    }

    @BeforeMethod
    public void setUp() throws IOException {
        checkExpirationTime(getValueFromJsonString("exp", getAccessTokenInfo()));
    }

    @Test
    public void getUserInfo() {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet();
        try {
            request.setURI(new URI(getUserInfoUrl()));
            request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken());
            HttpEntity entity = httpClient.execute(request).getEntity();
            String response = EntityUtils.toString(entity);
            System.out.println(response);
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getUsers() {
        String response = "";

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet();
        try {
            request.setURI(new URI(getUsersUrl()));
            request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken());
            HttpResponse httpResponse = httpClient.execute(request);
            HttpEntity entity = httpResponse.getEntity();
            response = EntityUtils.toString(entity);
            System.out.println("http response = " + httpResponse);
            System.out.println("entity response = " + response);
            Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), 200);
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkSpecificUserInUsers() throws JsonProcessingException {
        String response = "";

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet();
        try {
            request.setURI(new URI(getUsersUrl()));
            request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken());
            HttpEntity entity = httpClient.execute(request).getEntity();
            response = EntityUtils.toString(entity);
            System.out.println(response);
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(checkValueIsInJsonString("username", "second-user", response));
    }
}
