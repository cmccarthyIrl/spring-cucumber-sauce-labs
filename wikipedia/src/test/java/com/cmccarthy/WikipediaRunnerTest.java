package com.cmccarthy;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {
                "html:target/cucumber-html-reports",
                "json:target/cucumber-json-report.json",
                "com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/report.html"
        },
        glue = {"com/cmccarthy/step",
                "com/cmccarthy/utils"
        },
        features = {"classpath:feature/WikipediaTest.feature"}
)
public class WikipediaRunnerTest {


}


