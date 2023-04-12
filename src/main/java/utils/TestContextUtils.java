package utils;

import org.testng.ITestContext;

public class TestContextUtils {
    private static ITestContext context; // default

    public static void setContext(ITestContext testContext) {
        context = testContext;
    }

    public static ITestContext getContext() {
        return context;
    }
}
