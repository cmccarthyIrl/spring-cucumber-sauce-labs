package com.cmccarthy.page;

import com.cmccarthy.config.AbstractPage;
import com.cmccarthy.utils.DriverManager;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.stereotype.Component;

@Component
public class WikipediaPage extends AbstractPage {

    @FindBy(className = "central-featured-logo")
    private WebElement centerLogo;

    public WikipediaPage(DriverManager driverManager) {
        super(driverManager);
    }

    public void open(String url) {
        openAt(url);
    }

    public boolean isPageOpened() throws NoSuchFieldException {
        return waitForElementPresent(centerLogo);
    }
}

