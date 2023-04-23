package utils;

import org.testng.ITestContext;

public final class TestContextUtils {
    private static ITestContext context;

    private TestContextUtils() {}

    public static void setContext(ITestContext testContext) {
        context = testContext;
    }

    public static ITestContext getContext() {
        return context;
    }
}
