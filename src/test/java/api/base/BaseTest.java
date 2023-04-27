package api.base;

import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;

public class BaseTest {

    @BeforeMethod
    public void setUp(Method m) {
        System.out.println("\n" + "****** starting test: " + m.getName() + " ******");
    }
}
