package api.base;

import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import utils.EnvUtils;
import utils.TestContextUtils;

public class BaseTest {

    @Parameters({"env"})
    @BeforeClass
    public void setUp(@Optional("qa") String env, ITestContext context) {
        EnvUtils.setEnv(env);
        TestContextUtils.setContext(context);
    }
}
