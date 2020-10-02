package com.cmccarthy.config;

import com.cmccarthy.utils.ApplicationProperties;
import com.cmccarthy.utils.InvisibilityOfElement;
import com.cmccarthy.utils.VisibilityOfElement;
import com.cmccarthy.utils.VisibilityOfElementLocated;
import com.saucelabs.common.SauceOnDemandAuthentication;
import org.junit.After;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.NoSuchElementException;

@Component
public abstract class AbstractPage {

    @Autowired
    private ApplicationProperties applicationProperties;

    private static WebDriver driver;
    private Wait<WebDriver> wait;
    public static String username = System.getenv("SAUCE_USERNAME");
    public static String accessKey = System.getenv("SAUCE_ACCESS_KEY");

    private final Thread CLOSE_THREAD = new Thread() {
        @Override
        public void run() {
            driver.quit();
            driver.close();
        }
    };

    @PostConstruct
    public void init() throws MalformedURLException {
        driver = createDriver();
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, 10, 500);
        Runtime.getRuntime().addShutdownHook(CLOSE_THREAD);
    }

    private WebDriver createDriver() throws MalformedURLException {
        switch (applicationProperties.getBrowser().toLowerCase()) {
            case "firefox":
                System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/src/test/resources/geckodriver");
                FirefoxOptions capabilities = new FirefoxOptions();
                capabilities.setHeadless(true);
                driver = new FirefoxDriver(capabilities);
                break;
            case "chrome":
                driver = new ChromeDriver();
                break;
            case "sauce":
                ChromeOptions chromeOpts = new ChromeOptions();

                MutableCapabilities browserOptions = new MutableCapabilities();
                browserOptions.setCapability(ChromeOptions.CAPABILITY,  chromeOpts);
                browserOptions.setCapability("name", "scenario.getName()");
                browserOptions.setCapability("browserName", "chrome");
                browserOptions.setCapability("browserVersion", "latest");
                browserOptions.setCapability("platformName", "windows 10");

                String sauceUrl = "https://"+username+":"+accessKey+"@ondemand.eu-central-1.saucelabs.com:443/wd/hub";
                URL url = new URL(sauceUrl);
                driver = new RemoteWebDriver(url, browserOptions);
                break;
            default:
                throw new NoSuchElementException("NoSuchElementException :"+applicationProperties.getBrowser().toLowerCase());
        }
        return driver;
    }

    public WebDriver getWebDriver() {
        return driver;
    }

    /**
     * Open inner path of site
     */
    protected void openAt(String url) {
        getWebDriver().get(url);
    }

    /**
     * Wait until element is present
     *
     * @param element element
     * @return boolean
     */
    protected boolean waitForElementPresent(WebElement element) {
        boolean result = true;
        try {
            wait.until(new VisibilityOfElement(element));
        } catch (TimeoutException e) {
            result = false;
        } catch (Throwable t) {
            throw new Error(t);
        }
        return result;
    }

    /**
     * Wait until element not present
     *
     * @param element element
     * @return boolean
     */
    protected boolean waitForElementNotPresent(WebElement element) {
        boolean result = true;
        try {
            wait.until(new InvisibilityOfElement(element));
        } catch (TimeoutException e) {
            result = false;
        } catch (Throwable t) {
            throw new Error(t);
        }
        return result;
    }

    /**
     * Wait until element present by locator
     *
     * @param locator locator
     * @return boolean
     */
    protected boolean waitForElementPresent(By locator) {
        boolean result = true;
        try {
            wait.until(new VisibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            result = false;
        } catch (Throwable t) {
            throw new Error(t);
        }
        return result;
    }

    /**
     * Type string into element
     *
     * @param element element
     * @param s       string
     */
    protected void typeInto(WebElement element, String s) {
        element.clear();
        element.sendKeys(s);
    }

    /**
     * Get attribute "value" of the element
     *
     * @param element element
     * @return value
     */
    protected String getValue(WebElement element) {
        return element.getAttribute("value");
    }
}
