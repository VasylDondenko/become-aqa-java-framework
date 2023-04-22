package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.ConnectionException;
import exceptions.EncodingException;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class RetrieveUtils {


    public static <T> T retrieveResourceFromResponse(HttpResponse response, Class<T> clazz) {
        String jsonFromResponse;
        try {
            jsonFromResponse = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            throw new ConnectionException("Error while reading the response: " + e.getMessage());
        }

        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        T result;
        try {
            result = mapper.readValue(jsonFromResponse, clazz);
        } catch (JsonProcessingException e) {
            throw new EncodingException("Error while deserializing JSON: " + e.getMessage());
        }
        return result;
    }

}
