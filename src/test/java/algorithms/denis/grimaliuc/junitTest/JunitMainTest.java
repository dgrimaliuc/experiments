package algorithms.denis.grimaliuc.junitTest;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;

import static org.hamcrest.MatcherAssert.assertThat;

public class JunitMainTest extends BaseJunitTest {

    @Test
    @DisplayName("Test my test 1")
    public void test_y_test() {
        System.out.println("Test passed");

    }

    @Test
    @Tags(@Tag("smoke"))
    @DisplayName("Test my test 2")
    public void test_2_test() {
        assertThat(2, Matchers.greaterThan(1));
        System.out.println("Test passed");
    }

    @Test
    @Timeout(1)
    @DisplayName("Test my test 3")
    public void test_3_test() {
        Assertions.assertTrue(false);
        Assertions.fail("Error");
        System.out.println("Test passed");
    }
}
