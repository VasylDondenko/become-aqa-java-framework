package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.MalformedParametersException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

public final class PropertyUtils {
    private static final Logger logger = LogManager.getLogger(PropertyUtils.class);

    private static final Map<String, String> CONFIGMAP = new HashMap<>();
    private static Properties property = new Properties();
    private static String env;

    static {
        if (System.getProperty("env") != null) {
            env = System.getProperty("env");
            logger.info("System property for Environment is: {}", env);
        } else {
            env = "qa";
            logger.info("No system property for Environment is found. Default value '{}' is set", env);
        }
        try (FileInputStream file = new FileInputStream("src/main/resources/" + env + ".properties")) {
            property.load(file);
            for (Map.Entry<Object, Object> entry : property.entrySet()) {
                CONFIGMAP.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private PropertyUtils() {
    }

    public static String get(String key) {
        return getSystemProperty(key);
    }

    private static String getSystemProperty(String key) {
        logger.info("Searching system property '{}'", key);
        String property = System.getProperty(key);
        if (Objects.isNull(property)) {
            logger.info("No system property for '{}' is found.", key);
            return getConfigProperty(key);
        } else {
            logger.info("System property for '{}' is: {}].", key, property);
            return property;
        }
    }

    public static String getConfigProperty(String key) {
        logger.info("Searching property '{}' inside {}.properties file", key, env);
        String property = CONFIGMAP.get(key);
        if (Objects.nonNull(property)) {
            logger.info("Property for '{}' is: {}.", key, property);
            return property;
        } else {
            throw new MalformedParametersException("No '" + key + "' property found. Please check the documentation for instructions to setting up testing data");
        }
    }
}