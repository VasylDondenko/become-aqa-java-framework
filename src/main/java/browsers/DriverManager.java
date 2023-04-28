package browsers;

import org.openqa.selenium.WebDriver;

public final class DriverManager {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private DriverManager() {}

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void setDriver(WebDriver driverRef) {
        driver.set(driverRef);
    }

    public static void unload() {
        driver.remove();
    }
}