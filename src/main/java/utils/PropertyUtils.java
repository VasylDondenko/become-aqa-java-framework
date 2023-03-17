package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

public final class PropertyUtils {

    private PropertyUtils() {}

    private static Properties property = new Properties();
    private static final Map<String, String> CONFIGMAP = new HashMap<>();

    static {
        try(FileInputStream file = new FileInputStream("src/main/resources/config.properties")) {
            property.load(file);
            for(Map.Entry<Object, Object> entry : property.entrySet()) {
                CONFIGMAP.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        String property = "";
        if (Objects.nonNull(CONFIGMAP.get(key))) {
            property = CONFIGMAP.get(key);
        }
        return property;
    }

}