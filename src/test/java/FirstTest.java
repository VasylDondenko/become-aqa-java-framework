import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import users.User;

import java.util.Objects;

public class FirstTest {

    private User user;

    @BeforeMethod
    public void setUp() {
        user = new User(52);
    }

    @AfterMethod
    public void tearDown() {
        if (Objects.nonNull(user)) {
            user = null;
        }
    }

    @Test
    public void isUser52() {
        Assert.assertEquals(user.getAge(), 52);
    }

    @Test
    public void isUser50() {
        Assert.assertEquals(user.getAge(), 50);
    }

    @Test
    public void firstTest() {
        Assert.assertTrue(1 == 1);
    }

}
