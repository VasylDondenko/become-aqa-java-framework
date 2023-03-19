package utils;

public class EnvUtils {
    public static String env = "dev"; // default

    public static void setEnv(String envString) {
        env = envString;
    }
}
