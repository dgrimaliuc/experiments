package actions;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class BaseActions {

    public static Object executeScript(WebDriver driver, String script, Object object) {
        return ((JavascriptExecutor) driver).executeScript(script, object);
    }

    public static void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
