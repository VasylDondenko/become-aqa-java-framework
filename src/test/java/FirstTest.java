import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import users.User;

import java.util.Objects;

public class FirstTest {

    private User user;

    @Before
    public void setUp() {
        user = new User(52);
    }

    @After
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
