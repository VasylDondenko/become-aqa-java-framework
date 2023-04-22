package applications.api;

import exceptions.ConnectionException;
import exceptions.EncodingException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.JsonUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class GithubApiClient {
    private static final Logger logger = LogManager.getLogger(GithubApiClient.class);

    public static int countFoundRepos(String searchReposUrl, String repoName) {
        return getIntFromJson(searchRepo(searchReposUrl, repoName), "total_count");
    }

    private static JSONObject searchRepo(String searchReposUrl, String repoName) {
        HttpResponse response;
        String responseBody;

        HttpUriRequest request = new HttpGet(searchReposUrl + repoName);
        try {
            response = HttpClientBuilder.create().build().execute(request);
        } catch (IOException e) {
            throw new ConnectionException("Search repository request failed with a message: " + e.getMessage());
        }
        logger.info("searchRepo response status code {}", response.getStatusLine().getStatusCode());
        try {
            responseBody = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            throw new EncodingException("responseBody toString failed with a message: " + e.getMessage());
        }
        return new JSONObject(responseBody);
    }

    private static int getIntFromJson(JSONObject json, String key) {
        return json.getInt(key);
    }

    public static HttpResponse searchUser(String usersUrl, String name) {
        logger.debug("usersUrl: {} | name: {}", usersUrl, name);
        HttpUriRequest request = new HttpGet(usersUrl + name);
        try {
            return HttpClientBuilder.create().build().execute(request);
        } catch (IOException e) {
            throw new ConnectionException("Search user request failed with a message: " + e.getMessage());
        }
    }

    public static JSONObject requestWithToken(String url, String token) {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet();
        HttpEntity entity;
        String response;
        try {
            request.setURI(new URI(url));
            request.setHeader(HttpHeaders.AUTHORIZATION, "token " + token);
            request.setHeader(HttpHeaders.ACCEPT, "application/vnd.github.v3+json");
            entity = httpClient.execute(request).getEntity();
            response = EntityUtils.toString(entity);
            return new JSONObject(response);
        } catch (URISyntaxException e) {
            logger.error("Bad URL: {}, ", url);
            throw new ConnectionException("Failed to connect to server: " + url);
        } catch (IOException e) {
            throw new EncodingException("Failed to encode parameters with message: " + e.getMessage(), e);
        }
    }

    public static JSONArray requestJsonArrayWithToken(String url, String token) {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet();
        HttpEntity entity;
        String response;
        try {
            request.setURI(new URI(url));
            request.setHeader(HttpHeaders.AUTHORIZATION, "token " + token);
            request.setHeader(HttpHeaders.ACCEPT, "application/vnd.github.v3+json");
            entity = httpClient.execute(request).getEntity();
            response = EntityUtils.toString(entity);
            return new JSONArray(response);
        } catch (URISyntaxException e) {
            logger.error("Bad URL: {}, ", url);
            throw new ConnectionException("Failed to connect to server: " + url);
        } catch (IOException e) {
            throw new EncodingException("Failed to encode parameters with message: " + e.getMessage(), e);
        }
    }

    public static boolean isUserHasAccessToUserPrivateInfo(String url, String token) {
        return JsonUtils.isNodePresent(requestWithToken(url, token), "plan");
    }

    public static int countPrivateRepositories(String url, String token) {
        return JsonUtils.countObjects(requestJsonArrayWithToken(url, token));
    }


}
