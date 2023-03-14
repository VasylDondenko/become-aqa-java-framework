import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import users.User;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FirstTest {
    private User user;

    @BeforeEach
    public void setUp() {
        user = new User(52);
    }

    @AfterEach
    public void tearDown() {
        if (Objects.nonNull(user)) {
            user = null;
        }
    }

    @Test
    public void testIsUser52() {
        assertEquals(user.getAge(), 52);
    }

    @Test
    public void testIsUser50() {
        assertEquals(user.getAge(), 50);
    }
    @Test
    public void firstTest() {
        assertEquals(2, 1+1);
    }
}
