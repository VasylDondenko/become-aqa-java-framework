package applications.api.keycloak;

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
    private static String accessToken;
    private static String expValue;


    public static void receiveAccessToken(String[] receiveAccessTokenParams, String tokenUrl) {
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

    public static String getAccessTokenInfo(String[] getTokenInfoParams, String introspectUrl, String tokenUrl) {
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

    //TODO Use refresh token strategy
    public static void getNewAccessTokenIfExpired(String expirationTimeStr, String[] receiveAccessTokenParams, String tokenUrl) {
        logger.debug("ACCESS TOKEN IS: {}", accessToken);
        long expirationTime = Long.parseLong(expirationTimeStr);
        Instant now = Instant.now();
        Instant expiration = Instant.ofEpochSecond(expirationTime);
        Instant threshold = now.plusSeconds(30);
        if (expiration.isBefore(threshold)) {
            receiveAccessToken(receiveAccessTokenParams, tokenUrl);
        }
    }

    public static String getUserInfo(String userInfoUrl) {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet();
        try {
            request.setURI(new URI(userInfoUrl));
        } catch (URISyntaxException e) {
            throw new ConnectionException("Failed to connect to server: " + userInfoUrl);
        }
        request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        try {
            HttpEntity entity = httpClient.execute(request).getEntity();
            return EntityUtils.toString(entity);
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to convert entity to String", e);
        }
    }

    public static JSONArray getUsers(String usersUrl) {
        String response;

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet();
        try {
            request.setURI(new URI(usersUrl));
            request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
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

    public static int countUsers(String usersUrl) {
        JSONArray users = getUsers(usersUrl);
        return JsonUtils.countObjects(users);
    }
}
