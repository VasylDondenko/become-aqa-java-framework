package utils;

import java.util.Objects;

public class EnvUtils {

    private static String env;

    public static void setEnv(String envString) {
        env = envString;
    }

    public static String getEnv() {
        if (Objects.isNull(env)) {
            env = "dev";
        }
        return env;
    }
}
