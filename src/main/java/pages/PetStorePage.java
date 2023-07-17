package pages;

import helpers.customElements.CustomPageFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.components.TestComponent;

import java.util.List;

/**
 * Page - https://petstore-kafka.swagger.io/?location=Plett322
 */
public class PetStorePage {
    protected WebDriver driver;

    public PetStorePage(WebDriver driver) {
        this.driver = driver;
        CustomPageFactory.initElements(driver, this);

    }

    @FindBy(css = ".p-8")
    public List<WebElement> elements;

    @FindBy(css = ".p-8.flex.items-center")
    public WebElement headerElements;

    @FindBy(xpath = "//div[contains(@class,'lign-middle inline-block min-w-full')]")
    public TestComponent petsSection;

    @FindBy(xpath = "//div[./h2[text()=' The game ']]")
    public WebElement theGameSection;

    @FindBy(css = ".flex .p-8.flex-1:nth-child(1) tr")
    public List<WebElement> availablePets;

}
