package runner;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import io.cucumber.junit.platform.engine.Constants;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, value = "stepDefinitions, hooks")
@ConfigurationParameter(
        key = Constants.PLUGIN_PROPERTY_NAME,
        value = "pretty, com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:, html:target/CucumberReports/RegressionReport.html"
)
@ConfigurationParameter(key = Constants.EXECUTION_DRY_RUN_PROPERTY_NAME, value = "false")
@ConfigurationParameter(key = "cucumber.ansi-colors.disabled", value = "false")
@ConfigurationParameter(key = "cucumber.publish.quiet", value = "false")
// Filters specifically for the individual @Regression markers
@ConfigurationParameter(key = Constants.FILTER_TAGS_PROPERTY_NAME, value = "@Regression")
public class RegressionTestRunner
{
    // Left blank intentionally for JUnit 5 Engine routing
}