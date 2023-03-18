import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import users.User;
import utils.PropertyUtils;

import java.util.Objects;

public class FirstTest {

    private User user;

    @BeforeMethod
    public void setUp(ITestContext context) {
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
