import utils.EnvUtils;
import org.testng.ITestContext;
import org.testng.annotations.*;
import users.User;
import utils.PropertyUtils;

import java.util.Objects;

public class FirstTest {

    private User user;

    @Parameters({"env"})
    @BeforeMethod
    public void setUp(@Optional("dev") String env, ITestContext context) {
        EnvUtils.setEnv(env);
        String name = PropertyUtils.get("name", context);
        int age = Integer.valueOf(PropertyUtils.get("age", context));

        user = new User(name, age);
    }

    @AfterMethod
    public void tearDown() {
        if (Objects.nonNull(user)) {
            user = null;
        }
    }

    @Test
    public void userName() {
        System.out.println("user.getName() = " + user.getName());
    }

    @Test
    public void userAge() {
        System.out.println("user.getAge() = " + user.getAge());
    }

}
