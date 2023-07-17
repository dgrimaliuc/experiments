package algorithms.denis.grimaliuc.cucumberActionLibrary;

import helpers.Helpers;
import helpers.PythonExecutor;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.log4j.Logger;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.PetStorePage;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import static helpers.Helpers.getValue;
import static helpers.Helpers.stepResults;
import static org.apache.commons.lang.StringUtils.EMPTY;
import static org.hamcrest.MatcherAssert.assertThat;

public class MainStepDefinition {
    Logger log = Helpers.createLogger(MainStepDefinition.class);
    Helpers.Appender app = null;
    public WebDriverWait wait = null;
    public WebDriver driver = null;

    private final Map<String, Object> scenarioContext = new HashMap<>();

    @Before
    public void initializeHeap(Scenario scenario) {
        app = new Helpers.Appender(scenario);
        log.addAppender(app);
        stepResults = new ArrayList<>();
    }

    @After
    public void clearHeap() {
        log.removeAllAppenders();
        stepResults.clear();
    }

    @Before
    public void before() {
        java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.INFO);
        java.util.logging.Logger.getLogger("io.netty.util").setLevel(Level.INFO);
        var pathToChrome = Path.of(System.getProperty("user.dir") + "/src/main/resources/chromedriver.exe").normalize().toString();
        System.setProperty("webdriver.chrome.driver", pathToChrome);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver();//options
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10L);
        //   MatcherAssert.assertThat(1, CoreMatchers.equalTo(1));
    }

    @After
    public void after() {
        if (driver != null)
            driver.close();
    }


    @Then("^Verify first name is valid \"([^\"]*)\"$")
    public void verifyPasswordIsValid(String firstName) {
        assertThat(true, Matchers.is(true));
//        assertThat(firstName.length(), Matchers.greaterThan(4));
//        assertThat(firstName.length(), Matchers.lessThan(33));
        assertThat(firstName, Matchers.matchesPattern("[A-Za-z]{5,32}"));


        assertThat(24, Matchers.greaterThanOrEqualTo(18));

    }


    @And("^Open Pet Store page$")
    public void openPointMdPage() {
        log.info("Open : https://petstore-kafka.swagger.io/?location=Plett322");
        driver.get("https://petstore-kafka.swagger.io/?location=Plett322");
//        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(".flex .p-8.flex-1:nth-child(1) tr"), 5));
        PetStorePage page = new PetStorePage(driver);
        page.headerElements.click();
        //assertThat("theBiscuit", equalTo("myBiscuit"));
        log.info("Test Passed");
    }

    @Then("^Verify current URL$")
    public void verifyCurrentURL() {
        String curUrl = driver.getCurrentUrl();
        String expectedUrl = "https://petstore-kafka.swagger.io/?location=Plett322";
        log.info(String.format("Url validation, \nexpected:\n %s\n to be\n %s", curUrl, expectedUrl));
        assertThat(curUrl, CoreMatchers.equalTo(expectedUrl));
    }

    @Given("^I have two numbers \"([^\"]*)\" and \"([^\"]*)\"$")
    public void iHaveTwoNumbersAnd(int arg0, int arg1) {
        log.info("I have two numbers: " + arg0 + ", " + arg1);
        stepResults.add(new int[]{arg0, arg1});

    }

    @Then("^The result in step \"([^\"]*)\" should be \"([^\"]*)\"$")
    public void theResultShouldBe(String step, Integer arg0) {
        Integer num = getValue(step, Integer.class);
        assertThat(num, Matchers.is(arg0));
        stepResults.add(EMPTY);

    }

    @When("^Execute Python Script \"(.+)\"$")
    public void executePythonScript(String script) {
        String scriptRes = getValue(script, String.class);
        System.out.println("Executing script: " + scriptRes);
        String res = PythonExecutor.executePythonScript(scriptRes);
        System.out.println("Result: " + res);
        stepResults.add(res);
    }

    @Then("^Assert that \"([^\"]*)\"$")
    public void assertThatPyScriptIsTrue(String pyScript) {
        String isTrue = getValue(pyScript, String.class);

        Boolean result = PythonExecutor.executePythonScript(isTrue, Boolean.class);
        System.out.println("Verify: " + isTrue);
        assertThat(result, Matchers.is(true));
        stepResults.add(EMPTY);
    }

    @Given("^I have two strings \"([^\"]*)\" and \"([^\"]*)\"$")
    public void iHaveTwoStringsAnd(String arg0, String arg1) {
        log.info("I have two strings: " + arg0 + ", " + arg1);
        stepResults.add(new String[]{arg0, arg1});
    }


    @Given("^I have a string \"([^\"]*)\"$")
    public void iHaveAString(String arg0) {
        log.info("I have a string: " + arg0);
        stepResults.add(arg0);
    }

    @Given("^I have a list of maps with elements$")
    public void iHaveAListWithElements(List<Map<String, String>> elements) {
        stepResults.add(elements);
    }

    @Given("^I have a list$")
    public void iHaveAList(List<String> list) {
        log.info("I have a string: " + list);
        stepResults.add(list);
    }
}
