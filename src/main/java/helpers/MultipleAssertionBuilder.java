package helpers;

import org.apache.log4j.Logger;
import org.h2.util.StringUtils;
import org.testng.Assert;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Collectors;


public class MultipleAssertionBuilder {

    private static final String FAILED = "Failed";
    private static final String PASSED = "Passed";
    private static final String TEST_NAME = "TestName";
    private final Logger log;

    private List<String> failedGroups = new ArrayList<>();
    private List<String> passedGroups = new ArrayList<>();
    private final List<Map<String, String>> testResults = new ArrayList<>();

    private final String title1;
    private final String title2;


    public MultipleAssertionBuilder(String title1, String title2, Logger log) {
        this.log = log;
        this.title1 = title1;
        this.title2 = title2;
    }

    public <T> void assertEquals(String assertionValue, @Nonnull T v1, T v2) {
        if (Objects.requireNonNull(v1).equals(v2)) {
            passedGroups.add(format(assertionValue, v1, v2));
        } else {
            failedGroups.add(format(assertionValue, v1, v2));
        }
    }

    public void assertEquals(String assertionValue, double v1, double v2) {

        if (v1 - v2 <= 0.005 && v2 - v1 <= 0.005) {
            passedGroups.add(format(assertionValue, v1, v2));
        } else {
            failedGroups.add(format(assertionValue, v1, v2));
        }
    }

    public void dumpAssertions(String testName) {
        Map<String, String> currentTest = new HashMap<>();
        currentTest.put(TEST_NAME, testName);
        currentTest.put(FAILED, getFailedMessage());
        currentTest.put(PASSED, getPassedMessage());
        testResults.add(currentTest);
        failedGroups = new ArrayList<>();
        passedGroups = new ArrayList<>();
    }


    public void postResult() {
        String message = testResults.stream().map(test -> String.format("\n########## %s ##########\n%s%s", test.get(TEST_NAME), test.getOrDefault(FAILED, ""), test.getOrDefault(PASSED, ""))).collect(Collectors.joining("\n"));
        if (testResults.stream().anyMatch(map -> !StringUtils.isNullOrEmpty(map.get(FAILED)))) {
            Assert.fail(message);
        } else
            log.info(message);
    }

    private <T> String format(String title, T v1, T v2) {
        return String.format("\"%s\" -> { %7s : %7s }", title, (v1 == null ? "" : v1.toString()), (v2 == null ? "" : v2.toString()));
    }

    private String getPassedMessage() {
        String message = passedGroups.stream().map(s -> String.format("%s  true\n", s)).collect(Collectors.joining());
        return message.isEmpty() ? "" : String.format("\n\t%s: %s\n%s", title1, title2, message);
    }

    private String getFailedMessage() {
        String message = failedGroups.stream().map(s -> String.format("%s  false\n", s)).collect(Collectors.joining());
        return message.isEmpty() ? "" : String.format("\n\t%s: %s\n%s", title1, title2, message);
    }

}
