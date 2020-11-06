package com.cmccarthy.step;

import com.cmccarthy.config.AbstractTestDefinition;
import com.cmccarthy.utils.HookUtils;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;


public class Hooks extends AbstractTestDefinition {

    @Autowired
    private HookUtils hookUtils;

    @Before
    public void beforeScenario(Scenario scenario) {
        String scenarioName = hookUtils.getFeatureFileName(scenario);
        hookUtils.createLogger(scenarioName);
    }

    @After
    public void afterScenario(Scenario scenario) {
        hookUtils.endOfTest(StringUtils.capitalize(scenario.getStatus()));
    }
}