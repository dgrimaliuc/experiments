package algorithms.denis.grimaliuc.junitTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static org.apache.commons.lang.StringUtils.isBlank;

public class ParameterizedJunitTest extends BaseJunitTest {

    private static Stream<Arguments> testData() {
        return Stream.of(
                Arguments.of("Hello"),
                Arguments.of("World"),
                Arguments.of("")
        );
    }


    @ParameterizedTest
    @MethodSource("testData")
    void isBlank_ShouldReturnTrueForNullOrBlankStrings(String input) {
        Assertions.assertFalse(isBlank(input), "Assert \"" + input + "\" is not Blank");
    }


    @ParameterizedTest
    @ValueSource(ints = {1, 3, 5, -3, 15, Integer.MAX_VALUE})
    void isGreaterThan0(Integer input) {
        Assertions.assertTrue(input > 0, "Assert \"" + input + "\" is > 0");
    }

    @ParameterizedTest
    @NullSource
    void isNotNull(Integer input) {
        Assertions.assertTrue(input > 0, "Assert \"" + input + "\" is > 0");
    }

    @ParameterizedTest(name = "{index} {0} == {1} ?")
    @CsvSource({"test,TEST", "test,test", "tEst,TEST", "TEST,TEST", "Java,JAVA"})
    void isEqualIgnoreCase(String str1, String str2) {
        Assertions.assertEquals(str1, str2, "Assert \"" + str1 + "\" equals \"" + str2 + "\"");
    }
}