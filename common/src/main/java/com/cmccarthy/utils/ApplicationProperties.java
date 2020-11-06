package com.cmccarthy.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationProperties {

    @Value("${browser.value}")
    private String browser;

    @Value("${build.name.value}")
    private String browserName;

    @Value("${browser.version.value}")
    private String browserVersion;

    @Value("${os.value}")
    private String os;

    @Value("${os.version.value}")
    private String osVersion;

    @Value("${device.value}")
    private String device;

    @Value("${real.mobile.value}")
    private String realMobile;

    @Value("${build.name.value}")
    private String buildName;

    @Value("${local.value}")
    private String local;

    public String getBuildName() {
        return buildName;
    }

    public void setBuildName(String buildName) {
        this.buildName = buildName;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getBrowserName() {
        return browserName;
    }

    public void setBrowserName(String browserName) {
        this.browserName = browserName;
    }

    public String getBrowserVersion() {
        return browserVersion;
    }

    public void setBrowserVersion(String browserVersion) {
        this.browserVersion = browserVersion;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getRealMobile() {
        return realMobile;
    }

    public void setRealMobile(String realMobile) {
        this.realMobile = realMobile;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }
}