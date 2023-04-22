package api;

import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import utils.EnvUtils;

public class BaseTest {

    @Parameters({"env"})
    @BeforeClass
    public void setUp(@Optional("dev") String env, ITestContext context) {
        EnvUtils.setEnv(env);
    }
}
