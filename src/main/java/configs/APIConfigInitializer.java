package configs;

import utils.PropertyUtils;

public class APIConfigInitializer {

    private static String[] accessTokenGetParams = new String[]{
            "username", PropertyUtils.get("keycloak.user.username"),
            "password", PropertyUtils.get("keycloak.user.password"),
            "client_id", PropertyUtils.get("keycloak.clientID"),
            "client_secret", PropertyUtils.get("keycloak.clientSecret"),
            "grant_type", "password"};
    private static String[] tokenInfoParams = new String[]{
            "client_id", PropertyUtils.get("keycloak.clientID"),
            "client_secret", PropertyUtils.get("keycloak.clientSecret")};


    public static String getBaseUrl() {
        return PropertyUtils.get("keycloak.baseUrl");
    }

    public static String getOpenidConnectUrl(String realmName) {
        return getBaseUrl() + "/realms/" + realmName + "/protocol/openid-connect";
    }

    public static String getTokenUrl(String realmName) {
        return getOpenidConnectUrl(realmName) + "/token";
    }

    public static String getIntrospectUrl(String realmName) {
        return getTokenUrl(realmName) + "/introspect";
    }

    public static String getUserInfoUrl(String realmName) {
        return getOpenidConnectUrl(realmName) + "/userinfo";
    }

    public static String getUsersUrl(String realmName) {
        return getBaseUrl() + "/admin/realms/" + realmName + "/users";
    }

    public static String[] getAccessTokenGetParams() {
        return accessTokenGetParams;
    }

    public static String[] getTokenInfoParams() {
        return tokenInfoParams;
    }
}
