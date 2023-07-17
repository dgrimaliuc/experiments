package algorithms.denis.grimaliuc.junitTest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class BaseJunitTest {

    @BeforeAll
    static void beforeAll() {
        System.out.println("Before All");
    }


    @BeforeEach
    public void beforeEach() {
        System.out.println("Before Each");
    }

}
