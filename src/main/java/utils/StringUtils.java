package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class StringUtils {
    private static final Logger logger = LogManager.getLogger(StringUtils.class);

    private StringUtils() {}

    public static boolean isStringContainsWord(String text, String word) {
        logger.debug("Looking for word: {} in text: {}", word, text);
        return text.contains(word);
    }
}
