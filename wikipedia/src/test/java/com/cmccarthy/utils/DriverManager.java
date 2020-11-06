package com.cmccarthy.utils;

import com.browserstack.local.Local;
import org.apache.commons.lang3.RandomStringUtils;
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
    private Local local;

    private final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private final ThreadLocal<Wait<WebDriver>> driverWaitThreadLocal = new ThreadLocal<>();

    public static String usernameInTravis = System.getenv("BROWSERSTACK_USERNAME");
    public static String accessKeyInTravis = System.getenv("BROWSERSTACK_ACCESS_KEY");

    public static String username = "";
    public static String accessKey = "";

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    public void createWebDriver() throws Exception {
            setBrowserStackDriver();
    }

    private void setBrowserStackDriver() throws Exception {
        local = new Local();
        final String localIdentifier = RandomStringUtils.random(10, true, false);

        final DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("build", applicationProperties.getBuildName());
        caps.setCapability("browserName", applicationProperties.getBrowser());
        caps.setCapability("browserVersion", applicationProperties.getBrowserVersion());
        caps.setCapability("os", applicationProperties.getOs());
        caps.setCapability("os_version", applicationProperties.getOsVersion());
        caps.setCapability("device", applicationProperties.getDevice());
        caps.setCapability("real_mobile", applicationProperties.getRealMobile());

        if ((applicationProperties.getLocal() != null) && (applicationProperties.getLocal().equalsIgnoreCase("true"))) {
            Map<String, String> localConnectionOptions = new HashMap<>();
            if(username.length() > 3){
                localConnectionOptions.put("key", accessKey);
            }else{
                localConnectionOptions.put("key", accessKeyInTravis);
            }
            localConnectionOptions.put("localIdentifier", localIdentifier);
            local.start(localConnectionOptions);
            caps.setCapability("browserstack.local", "true");
            caps.setCapability("browserstack.localIdentifier", localIdentifier);
        }

        if(username.length() > 3){
            webDriver = new RemoteWebDriver(new URL("https://" + username + ":" + accessKey + "@hub.browserstack.com/wd/hub"), caps);
        }else{
            webDriver = new RemoteWebDriver(new URL("https://" + usernameInTravis + ":" + accessKeyInTravis + "@hub.browserstack.com/wd/hub"), caps);
        }

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
            try {
                local.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
            webDriver.quit();
        }
    };
}
