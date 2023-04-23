package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.MalformedParametersException;

public final class TimeoutUtils {
    private static final Logger logger = LogManager.getLogger(TimeoutUtils.class);

    private static Long timeout;
    private static Long defaultTimeout = 15L;

    private TimeoutUtils() {}

    public static Long getTimeOut() {
        setTimeOut();
        return timeout;
    }

    private static void setTimeOut() {
        try {
            timeout = Long.parseLong(PropertyUtils.getConfigProperty("explicitWaitTimeout"));
        } catch (MalformedParametersException e) {
            logger.info("No 'explicitWaitTimeout' parameter is found: {}", e.getMessage());
            logger.info("Default value will be set instead: {} seconds", defaultTimeout);
        } catch (NumberFormatException e) {
            logger.info("Error while parsing timeout value from properties: {}", e.getMessage());
            logger.info("Default value will be set instead: {} seconds", defaultTimeout);
        } finally {
            timeout = defaultTimeout;
        }

    }


}
