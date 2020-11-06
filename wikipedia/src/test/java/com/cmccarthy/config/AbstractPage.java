package com.cmccarthy.config;

import com.cmccarthy.service.LogFactoryService;
import com.cmccarthy.utils.ApplicationProperties;
import com.cmccarthy.utils.DriverManager;
import com.cmccarthy.utils.VisibilityOfElement;
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractPage {

    @Autowired
    private LogFactoryService logFactoryService;

    @Autowired
    private DriverManager driverManager;

    public AbstractPage(DriverManager driverManager) {
        PageFactory.initElements(driverManager.getDriver(), this);
    }

    protected void openAt(String url) {
        driverManager.getDriver().get(url);
    }

    public WebDriver getDriver() {
        return driverManager.getDriver();
    }

    protected boolean waitForElementPresent(WebElement element) throws NoSuchFieldException {
        waitForPageLoaded();
        getElementLocator(element);
        boolean result = true;
        try {
            driverManager.getDriverWait().until(new VisibilityOfElement(element));
        } catch (TimeoutException e) {
            result = false;
        } catch (Exception t) {
            throw new NoSuchFieldException(t.getLocalizedMessage());
        }
        return result;
    }

    protected boolean checkForElementPresent(WebElement element) {
        waitForPageLoaded();
        getElementLocator(element);
        boolean result = true;
        try {
            driverManager.getDriverWait().until(new VisibilityOfElement(element));
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    private void waitForPageLoaded() {
        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
            }
        };
        try {
            Thread.sleep(800);
            driverManager.getDriverWait().until(expectation);
        } catch (Throwable error) {
            Assert.fail("Timeout waiting for Page Load Request to complete.");
        }
    }

    public void switchToFrame(WebElement element) {
        driverManager.getDriver().switchTo().frame(element);
    }

    public void switchToMainContent() {
        driverManager.getDriver().switchTo().parentFrame();
        driverManager.getDriver().switchTo().defaultContent();
    }

    private void getElementLocator(WebElement element) {
        try {
            logFactoryService.getLogger().debug("Waiting for element to be present by locator :" + element.toString().split(">")[1].replace("]", ""));
        } catch (IndexOutOfBoundsException ex) {
            logFactoryService.getLogger().debug("Waiting for element to be present by locator :" + element.toString().split("'")[1].replace("]", ""));
        }
    }
}
