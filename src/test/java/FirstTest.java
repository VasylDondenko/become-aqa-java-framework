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
        String name = "Default Name";
        int age = 255;

        try {
            name = System.getProperty("name");
            age = Integer.valueOf(System.getProperty("age"));
            System.out.println(name + " " + age + " are set from command line");
        } catch (Exception e) {
            System.out.println("No command line arguments are set");
            name = "Default Name";
            age = 255;
        }

        if (name.equals("Default Name")) {
            try {
                if (Objects.nonNull(context.getCurrentXmlTest().getParameter("name"))) {
                    name = context.getCurrentXmlTest().getParameter("name");
                }
                System.out.println(name + " is set from the XML file");
            } catch (Exception e) {
                System.out.println("Not running using XML");
                name = "Default Name";
            }
            if (age == 255) {
                try {
                    if (Objects.nonNull(context.getCurrentXmlTest().getParameter("age"))) {
                        age = Integer.valueOf(context.getCurrentXmlTest().getParameter("age"));
                    }
                    System.out.println(age + " is set from the XML file");
                } catch (Exception e) {
                    System.out.println("Not running using XML");
                    age = 255;
                }
                if (name.equals("Default Name")) {
                    try {
                        name = PropertyUtils.get("name");
                        System.out.println(name + ": name value is set");
                    } catch (Exception e) {
                        System.out.println(name + " not present in config.properties");
                        name = "Default Name";
                    } finally {
                        if (name.equals("")) {
                            name = "Default Name";
                        }
                    }
                    if (age == 255) {
                        try {
                            age = Integer.valueOf(PropertyUtils.get("age"));
                            System.out.println(age + ": age value is set");
                        } catch (Exception e) {
                            System.out.println(age + " not present in config.properties");
                            age = 255;
                        }
                    }
                }
            }
        }
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
