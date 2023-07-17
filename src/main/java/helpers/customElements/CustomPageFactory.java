package helpers.customElements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.Arrays;

/**
 * If a WebElement found it tries to find element and to set its value
 * If a Component found it tries to create its object and to initialize its webElements
 */
public class CustomPageFactory {

    public static void initElements(WebDriver driver, Object page) {
        var waitor = new WebDriverWait(driver, 5);
        try {
            Class<?> clazz = page.getClass();
            Field[] elements = clazz.getFields();
            // Needs for components
            var parentField = Arrays.stream(elements).filter(it -> it.isAnnotationPresent(ParentElement.class))
                    .findFirst().orElse(null);
            WebElement parentElement = null;
            if (parentField != null) {
                parentElement = (WebElement) parentField.get(page);
            }
            // Set every WebElement field
            for (Field element : elements) {
                String type = element.getType().getName();
                element.setAccessible(true);
                if (element.isAnnotationPresent(FindBy.class)) {
                    var initPath = getInitPath(element);

                    switch (type) {
                        case "org.openqa.selenium.WebElement":
                            if (parentElement == null) {
                                setProxyLocator(driver, element, page);
                            } else {
                                element.set(page, parentElement.findElement(initPath));
                            }
                            break;
                        case "java.util.List":
                            if (parentElement == null) {
                                setProxyLocator(driver, element, page);
                            } else {
                                waitor.until(ExpectedConditions.presenceOfAllElementsLocatedBy(initPath));
                                element.set(page, parentElement.findElements(initPath));
                            }
                            break;
                        default:
                            if (Component.class.isAssignableFrom(element.getType())) {
                                Class<?> componentClazz = element.getType();
                                var constructor = componentClazz.getDeclaredConstructor(WebDriver.class, WebElement.class);
                                var parent = driver.findElement(getInitPath(element));
                                var componentInstance = constructor.newInstance(driver, parent);
                                element.set(page, componentInstance);
                            } else if (element.get(page) == null)
                                throw new UnsupportedTemporalTypeException("Unsupported type of variable: " + element.getName() + " : " + type);
                    }
                }
            }
        } catch (IllegalAccessException | NoSuchMethodException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static By getInitPath(Field field) {
        if (field.isAnnotationPresent(FindBy.class)) {

            var findBy = field.getDeclaredAnnotation(FindBy.class);
            return new FindBy.FindByBuilder().buildIt(findBy, field);
        } else
            return null;
    }

    private static void setProxyLocator(WebDriver driver, Field field, Object page) throws IllegalAccessException {
        var factory = (ElementLocatorFactory) (new DefaultElementLocatorFactory(driver));
        var decorator = new DefaultFieldDecorator(factory);
        Object value = decorator.decorate(page.getClass().getClassLoader(), field);
        field.set(page, value);
    }
}
