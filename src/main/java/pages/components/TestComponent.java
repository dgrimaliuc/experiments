package pages.components;

import helpers.customElements.Component;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class TestComponent extends Component {

    public TestComponent(WebDriver driver, WebElement parent) {
        super(driver, parent);
    }

    @FindBy(css = "input.px-5")
    public WebElement inputField;

    @FindBy(css = ".cursor-pointer")
    public List<WebElement> pets;
}
