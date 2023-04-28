package configs;

import utils.PropertyUtils;

public class APIGitHubConfigInitializer {

    public static String getBaseUrl() {
        return PropertyUtils.get("github.api.baseUrl");
    }

    public static String getUsersUrl() {
        return getBaseUrl() + "/users/";
    }

    public static String getUserUrl(String user) {
        return getUsersUrl() + user;
    }

    public static String getSearchRepoUrl() {
        return getBaseUrl() + "/search/repositories?q=";
    }

    public static String getCurrentUserPrivateReposUrl() {
        return getBaseUrl() + "/user/repos?type=private";
    }
}
