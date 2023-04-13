package applications.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import utils.Property2Utils;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class KeycloakApi {


    private String realmName = Property2Utils.get("keycloak.realmName");

    private String baseUrl = Property2Utils.get("keycloak.baseUrl");
    private String openidConnectUrl = baseUrl + "/realms/" + realmName + "/protocol/openid-connect";
    private String tokenUrl = openidConnectUrl + "/token";
    private String introspectUrl = tokenUrl + "/introspect";
    private String userInfoUrl = openidConnectUrl + "/userinfo";
    private String usersUrl = baseUrl + "/admin/realms/" + realmName + "/users";

    private String username = Property2Utils.get("keycloak.user.username");
    private String password = Property2Utils.get("keycloak.user.password");

    private String clientId = Property2Utils.get("keycloak.clientID");
    private String clientSecret = Property2Utils.get("keycloak.clientSecret");

    private String accessToken;

    private String grantType = "password";

    private String expValue;


    public String getUserInfoUrl() {
        return userInfoUrl;
    }

    public String getUsersUrl() {
        return usersUrl;
    }

    public String getIntrospectUrl() {
        return introspectUrl;
    }

    public String getAccessToken() {
        return accessToken;
    }


    protected void receiveAccessToken() throws IOException {
        HttpClient client = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost(tokenUrl);
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("client_id", clientId));
        params.add(new BasicNameValuePair("client_secret", clientSecret));
        params.add(new BasicNameValuePair("grant_type", grantType));
        post.setEntity(new UrlEncodedFormEntity(params));

        HttpResponse response = client.execute(post);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);

        HttpEntity entity = response.getEntity();
        String responseBody = EntityUtils.toString(entity);
        accessToken = responseBody.split("\"")[3];
        System.out.println("responseBody = " + responseBody);
        System.out.println("accessToken = " + accessToken);
    }

    protected String getAccessTokenInfo() throws IOException {
        HttpClient client = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost(getIntrospectUrl());
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("token", accessToken));
        params.add(new BasicNameValuePair("client_id", clientId));
        params.add(new BasicNameValuePair("client_secret", clientSecret));
        post.setEntity(new UrlEncodedFormEntity(params));

        HttpResponse response = client.execute(post);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);

        HttpEntity entity = response.getEntity();
        String responseBody = EntityUtils.toString(entity);

        System.out.println("responseBody = " + responseBody);
        System.out.println("accessToken = " + accessToken);

//        Assert.assertFalse(accessToken.isEmpty());
        return responseBody;
    }

    protected String getValueFromJsonString(String value, String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonNode = objectMapper.readTree(json);

        expValue = jsonNode.get(value).toString();

        System.out.println("expValue: " + expValue);

        return expValue;
    }

    protected boolean checkValueIsInJsonString(String key, String value, String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(json);

        boolean foundValue = false;
        for (JsonNode node : jsonNode) {
            String jsonValue = node.get(key).asText();
            if (jsonValue.equals(value)) {
                foundValue = true;
                break;
            }
        }

        return foundValue;
    }

    protected void checkExpirationTime(String expirationTimeStr) throws IOException {
        long expirationTime = Long.parseLong(expirationTimeStr);
        Instant now = Instant.now();
        Instant expiration = Instant.ofEpochSecond(expirationTime);
        Instant threshold = now.plusSeconds(30);
        if (expiration.isBefore(threshold)) {
            receiveAccessToken();
        }
    }
}
