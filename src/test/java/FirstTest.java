import api.base.BaseTest;
import org.testng.annotations.*;
import users.User;
import utils.EnvUtils;
import utils.PropertyUtils;

import java.util.Objects;

public class FirstTest extends BaseTest {

    private User user;

    @Parameters({"env"})
    @BeforeMethod
    public void setUp(@Optional("dev") String env) {
        EnvUtils.setEnv(env);
        String name = PropertyUtils.get("name");
        int age = Integer.valueOf(PropertyUtils.get("age"));

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
