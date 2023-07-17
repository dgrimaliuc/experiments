package algorithms.denis.grimaliuc.cucumberActionLibrary;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.nio.file.Path;
import java.util.logging.Level;

public class HowSeleniumWorksTest {

    public static WebDriverWait wait = null;
    public static WebDriver driver = null;

    public static void createDriver() {
        java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.INFO);
        java.util.logging.Logger.getLogger("io.netty.util").setLevel(Level.INFO);
        var pathToChrome = Path.of(
                        System.getProperty("user.dir") + "/src/main/resources/chromedriver.exe")
                .normalize().toString();
        System.setProperty("webdriver.chrome.driver", pathToChrome);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(); //options
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 10L);
    }

    public static void main(String[] args) {
        createDriver();
        driver.get("https://www.google.com");
        driver.findElement(By.xpath("//textarea[@type='search']")).sendKeys("Hello World\n");
    }
}
