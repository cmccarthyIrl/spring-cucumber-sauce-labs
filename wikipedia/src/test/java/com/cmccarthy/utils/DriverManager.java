package com.cmccarthy.utils;

import com.browserstack.local.Local;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Component
public class DriverManager {

    private WebDriver webDriver;
    private final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private final ThreadLocal<Wait<WebDriver>> driverWaitThreadLocal = new ThreadLocal<>();

    public static String username = System.getenv("BROWSERSTACK_USERNAME");
    public static String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    public void createWebDriver() throws Exception {
            setBrowserStackDriver();
    }

    private void setBrowserStackDriver() throws Exception {
        final DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("build", applicationProperties.getBuildName());
        caps.setCapability("browserName", applicationProperties.getBrowserName());
        caps.setCapability("browserVersion", applicationProperties.getBrowserVersion());
        caps.setCapability("os", applicationProperties.getOs());
        caps.setCapability("os_version", applicationProperties.getOsVersion());
        caps.setCapability("device", applicationProperties.getDevice());
        caps.setCapability("real_mobile", applicationProperties.getRealMobile());

        if ((applicationProperties.getLocal() != null) && (applicationProperties.getLocal().equalsIgnoreCase("true"))) {
            final Local local = new Local();
            Map<String, String> args = new HashMap<>();
            args.put("key", accessKey);
            local.start(args);
            caps.setCapability("browserstack.local", "true");
        }

        webDriver = new RemoteWebDriver(new URL("https://" + username + ":" + accessKey + "@hub.browserstack.com/wd/hub"), caps);
        driverThreadLocal.set(webDriver);
        final Wait<WebDriver> webDriverWait = new WebDriverWait(webDriver, 30, 500);
        driverWaitThreadLocal.set(webDriverWait);
        Runtime.getRuntime().addShutdownHook(CLOSE_THREAD);
    }

    public WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    public Wait<WebDriver> getDriverWait() {
        return driverWaitThreadLocal.get();
    }

    private final Thread CLOSE_THREAD = new Thread() {
        @Override
        public void run() {
            webDriver.quit();
        }
    };
}
