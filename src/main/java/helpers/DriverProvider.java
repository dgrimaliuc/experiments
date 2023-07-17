package helpers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.nio.file.Path;
import java.util.logging.Level;

import static helpers.PropertiesReader.getProperty;
import static java.util.logging.Logger.getLogger;

public class DriverProvider {

    private static void setLoggerLevel() {
        getLogger("org.openqa.selenium").setLevel(Level.INFO);
        getLogger("io.netty.util").setLevel(Level.INFO);
    }

    public static WebDriver createDriver() {
        setLoggerLevel();
        var pathToChrome = Path.of(getProperty("driver-path")).normalize().toString();
        System.setProperty("webdriver.chrome.driver", pathToChrome);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        return driver;
    }
}
