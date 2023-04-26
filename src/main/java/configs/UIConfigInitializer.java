package configs;

import utils.PropertyUtils;

public final class UIConfigInitializer {

    private UIConfigInitializer() {
    }

    public static String getGithubUsername() {
        return PropertyUtils.get("github.username");
    }

    public static String getGithubPassword() {
        return PropertyUtils.get("github.password");
    }
}
