package api.base;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import utils.EnvUtils;

import java.lang.reflect.Method;

public class BaseTest {

    @Parameters({"env"})
    @BeforeClass
    public void setUpClass(@Optional("qa") String env) {
        EnvUtils.setEnv(env);
    }

    @BeforeMethod
    public void setUp(Method m) {
        System.out.println("\n" + "****** starting test: " + m.getName() + " ******");
    }
}
