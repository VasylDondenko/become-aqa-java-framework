package applications.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import utils.JsonUtils;
import utils.PropertyUtils;
import utils.TestContextUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public final class KeycloakApi {
    private static final Logger logger = LogManager.getLogger(KeycloakApi.class);

    private static HttpResponse response;
    private static String realmName = PropertyUtils.get("keycloak.realmName", TestContextUtils.getContext());

    private static String baseUrl = PropertyUtils.get("keycloak.baseUrl", TestContextUtils.getContext());
    private static String openidConnectUrl = baseUrl + "/realms/" + realmName + "/protocol/openid-connect";
    private static String tokenUrl = openidConnectUrl + "/token";
    private static String introspectUrl = tokenUrl + "/introspect";
    private static String userInfoUrl = openidConnectUrl + "/userinfo";
    private static String usersUrl = baseUrl + "/admin/realms/" + realmName + "/users";

    private static String username = PropertyUtils.get("keycloak.user.username", TestContextUtils.getContext());
    private static String password = PropertyUtils.get("keycloak.user.password", TestContextUtils.getContext());

    private static String anotherUser = PropertyUtils.get("keycloak.anotherUser.username", TestContextUtils.getContext());

    private static String clientId = PropertyUtils.get("keycloak.clientID", TestContextUtils.getContext());
    private static String clientSecret = PropertyUtils.get("keycloak.clientSecret", TestContextUtils.getContext());

    private static String accessToken;
    private static String grantType = "password";
    private static String expValue;

    private static String[] receiveAccessTokenParams = {
            "username", username,
            "password", password,
            "client_id", clientId,
            "client_secret", clientSecret,
            "grant_type", grantType};

    private static String[] getTokenInfoParams = {
            "client_id", clientId,
            "client_secret", clientSecret};


    public static String getUserInfoUrl() {
        return userInfoUrl;
    }

    public static String getAccessToken() {
        return accessToken;
    }

    public static String getUsername() {
        return username;
    }

    public static String getAnotherUser() {
        return anotherUser;
    }


    public static void receiveAccessToken() {
        logger.info("TRYING TO RECEIVE ACCESS TOKEN");

        HttpClient client = HttpClientBuilder.create().build();

        HttpPost post = executeHttpPost(receiveAccessTokenParams, tokenUrl);
        try {
            response = client.execute(post);
        } catch (HttpHostConnectException e) {
            throw new ConnectionException("Failed to connect to server: " + tokenUrl);
        } catch (IOException e) {
            throw new HttpRequestException("Failed to execute HTTP request: " + e.getMessage(), e);
        }
        logger.debug("HTTP request to obtain access token completed");
        try {
            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            String responseBody = EntityUtils.toString(entity);

            logger.debug("DEBUG response status code = {}", statusCode);
            logger.debug("DEBUG response body = {}", responseBody);

            if (statusCode == 200) {
                accessToken = responseBody.split("\"")[3];
                logger.debug("access token = {}", accessToken);

                if (accessToken == null) {
                    logger.error("Access token is null");
                    throw new AccessTokenNullException("Access token is null");
                }
            } else {
                logger.error("Failed to get access token. Response status code = {}", statusCode);
                throw new AccessTokenException("Failed to get access token. Response status code = " + statusCode);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to convert entity to String", e);
        }
        logger.debug("ACCESS TOKEN IS: {}", accessToken);
    }

    private static HttpPost executeHttpPost(String[] paramsPairs, String url) {
        logger.info("EXECUTING POST WITH PARAMETERS");
        HttpPost post = new HttpPost(url);
        if (paramsPairs.length % 2 != 0) {
            throw new IllegalArgumentException("Number of parameters must be even");
        }
        List<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("token", accessToken));
        for (int i = 0; i < paramsPairs.length; i += 2) {
            parameters.add(new BasicNameValuePair(paramsPairs[i], paramsPairs[i + 1]));
            logger.debug("Parameter added {} = {}", paramsPairs[i], paramsPairs[i + 1]);
        }
        try {
            post.setEntity(new UrlEncodedFormEntity(parameters));
        } catch (UnsupportedEncodingException e) {
            logger.error("Failed to encode parameters: {}", e.getMessage());
            throw new EncodingException("Failed to encode parameters with message: " + e.getMessage(), e);
        }
        return post;
    }

    public static String getAccessTokenInfo() {
        logger.info("TRYING TO GET ACCESS TOKEN INFO");

        HttpClient client = HttpClientBuilder.create().build();

        HttpPost post = executeHttpPost(getTokenInfoParams, introspectUrl);
        try {
            response = client.execute(post);
        } catch (HttpHostConnectException e) {
            throw new ConnectionException("Failed to connect to server: " + introspectUrl);
        } catch (IOException e) {
            throw new HttpRequestException("Failed to execute HTTP request: " + e.getMessage(), e);
        }
        try {
            client.execute(post);
        } catch (HttpHostConnectException e) {
            throw new ConnectionException("Failed to connect to server: " + tokenUrl);
        } catch (IOException e) {
            throw new HttpRequestException("Failed to execute HTTP request: " + e.getMessage(), e);
        }
        try {
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to convert entity to String", e);
        }
    }

    public static String getValueFromJsonString(String value, String json) throws JsonProcessingException {
        logger.debug("ACCESS TOKEN IS: {}", accessToken);
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonNode = objectMapper.readTree(json);

        logger.debug("json argument text: {}", json);
        logger.debug("JSON Node as text: {}", jsonNode.asText());

        expValue = jsonNode.get(value).toString();

        logger.info("expValue: {}", expValue);

        return expValue;
    }

    public static void checkExpirationTime(String expirationTimeStr) {
        logger.debug("ACCESS TOKEN IS: {}", accessToken);
        long expirationTime = Long.parseLong(expirationTimeStr);
        Instant now = Instant.now();
        Instant expiration = Instant.ofEpochSecond(expirationTime);
        Instant threshold = now.plusSeconds(30);
        if (expiration.isBefore(threshold)) {
            receiveAccessToken();
        }
    }

    public static String getUserInfo() {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet();
        try {
            request.setURI(new URI(getUserInfoUrl()));
        } catch (URISyntaxException e) {
            throw new ConnectionException("Failed to connect to server: " + getUserInfoUrl());
        }
        request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken());
        try {
            HttpEntity entity = httpClient.execute(request).getEntity();
            return EntityUtils.toString(entity);
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to convert entity to String", e);
        }
    }

    public static JSONArray getUsers() {
        String response;

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet();
        try {
            request.setURI(new URI(usersUrl));
            request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken());
            HttpResponse httpResponse = httpClient.execute(request);
            HttpEntity entity = httpResponse.getEntity();
            response = EntityUtils.toString(entity);
            logger.debug("http response = {}", httpResponse);
            logger.debug("entity response = {}", response);
            return new JSONArray(response);
        } catch (URISyntaxException e) {
            logger.error("Bad URL: {}, ", usersUrl);
            throw new ConnectionException("Failed to connect to server: " + usersUrl);
        } catch (IOException e) {
            throw new EncodingException("Failed to encode parameters with message: " + e.getMessage(), e);
        }
    }

    public static int countUsers() {
        return JsonUtils.countObjects(getUsers());
    }
}
