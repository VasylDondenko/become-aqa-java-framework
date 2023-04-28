package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {
    private static final Logger logger = LogManager.getLogger(JsonUtils.class);

    private JsonUtils() {}

    public static boolean isNodePresent(JSONObject json, String node) {
        try {
            JSONObject tempNode = json.getJSONObject(node);
            return tempNode != null;
        } catch (JSONException e) {
            logger.info("No node with name {} found", node);
            logger.debug("JSONObject is: {}", json);
            return false;
        }
    }

    public static int countObjects(JSONArray json) {
        logger.info("{} json objects found", json.length());
        logger.debug("json array is: {}", json);
        return json.length();
    }

    private static String getStringFromJson(JSONObject json, String key) {
        return json.get(key).toString();
    }

    public static boolean checkValueIsInJsonString(String key, String value, String json) throws JsonProcessingException {
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
}
