package utils;

import java.util.Objects;

public class EnvUtils {
    private static String env = "dev"; // default

    public static void setEnv(String envString) {
        env = envString;
    }

    public static String getEnv() {
        env = System.getProperty("env");
        if (Objects.isNull(env)) {
            env = "dev";
        }
        return env;
    }
}
