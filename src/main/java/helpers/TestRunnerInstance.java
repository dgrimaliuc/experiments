package helpers;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestRunnerInstance {
    public final WebDriver driver;
    public final Logger logger;
    public final WebDriverWait waitor;

    TestRunnerInstance(WebDriver driver, Logger logger, WebDriverWait waitor) {
        this.driver = driver;
        this.logger = logger;
        this.waitor = waitor;
    }
    
}
