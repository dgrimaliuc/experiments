package algorithms.denis.grimaliuc.testNGTest;

import helpers.TestRunnerInstance;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.PetStorePage;

import static helpers.Helpers.createInstance;

public class TestNgMainTest {
    private final TestRunnerInstance test = createInstance(TestRunnerInstance.class);

    @Test(groups = {"test-group-1"})
    public void mainTest() {
        //new PetStorePage(test.driver).availablePets
        test.driver.get("https://petstore-kafka.swagger.io/?location=Chisinau");
        var page = new PetStorePage(test.driver);
        System.out.println(Thread.currentThread().getId());
    }

    public static void main(String[] args) {
        SoftAssertions softAssertions = new SoftAssertions();

        // Perform multiple assertions
        softAssertions.assertThat(2 + 2).isEqualTo(5);
        softAssertions.assertThat("Hello").startsWith("H");
        softAssertions.assertThat(10.5).isGreaterThan(20.0);

        // Assert all the assertions and collect failures
        softAssertions.assertAll();
    }

    @Test
    public void mainTest2(String g) {
        System.out.println(Thread.currentThread().getId());
    }

    @Test
    public void mainTest3() {
        var softAssertion = new SoftAssert();
        softAssertion.assertEquals(3, 1);
        softAssertion.assertEquals(1, 2);
        softAssertion.assertEquals(3, 3);
        softAssertion.assertNotEquals(true, false);
        softAssertion.assertAll();
        System.out.println(Thread.currentThread().getId());
    }

    @AfterTest
    public void
    closeBrowser() {
        test.driver.close();
    }
}
