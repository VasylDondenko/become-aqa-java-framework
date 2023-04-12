package utils;

import org.testng.ITestContext;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.MalformedParametersException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

public final class PropertyUtils {

    private static final Map<String, String> CONFIGMAP = new HashMap<>();
    private static Properties property = new Properties();

    static {
        System.out.println("EnvUtils.env = " + EnvUtils.env);
        try (FileInputStream file = new FileInputStream("src/main/resources/" + EnvUtils.env + ".properties")) {
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

    public static String get(String key, ITestContext context) {
        return getSystemProperty(key, context);
    }

    private static String getSystemProperty(String key, ITestContext context) {
        String property = System.getProperty(key);
        if (Objects.isNull(property)) {
            return getXmlProperty(key, context);
        } else {
            return property;
        }
    }

    private static String getXmlProperty(String key, ITestContext context) {
        String property = context.getCurrentXmlTest().getParameter(key);
        if (Objects.isNull(property)) {
            return getConfigProperty(key);
        } else {
            return property;
        }
    }

    private static String getConfigProperty(String key) {
        String property = CONFIGMAP.get(key);
        if (Objects.nonNull(property)) {
            return property;
        } else {
            throw new MalformedParametersException("No Properties found. Please check the documentation for instructions to setting up testing data");
        }
    }
}