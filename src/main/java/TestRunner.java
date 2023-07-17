import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/",
        glue = {"algorithms/denis/grimaliuc/cucumberActionLibrary"},
//        tags = {"@End2End"}, // and not @SmokeWrapper
        plugin = {"pretty", "html:target/cucumber", "json:target/cucumber-html-reports/cucumber.json"})

public class TestRunner {

}