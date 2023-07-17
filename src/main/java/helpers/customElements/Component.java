package helpers.customElements;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Component {

    @ParentElement
    public final WebElement parent;

    protected final WebDriver driver;

    public Component(WebDriver driver) {
        this(driver, null);
    }

    public Component(WebDriver driver, WebElement parent) {
        this.driver = driver;
        this.parent = parent;
        CustomPageFactory.initElements(driver, this);
    }


}
