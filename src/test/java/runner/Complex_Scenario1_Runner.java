package runner;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import io.cucumber.junit.platform.engine.Constants;

@Suite
@IncludeEngines("cucumber")

@SelectClasspathResource("features")

@ConfigurationParameter(
        key = Constants.GLUE_PROPERTY_NAME,
        value = "stepDefinitions, hooks")

@ConfigurationParameter(
        key = Constants.PLUGIN_PROPERTY_NAME,
        value = "pretty, com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:, html:target/CucumberReports/AllTestsReport.html")

@ConfigurationParameter(
        key = Constants.EXECUTION_DRY_RUN_PROPERTY_NAME,
        value = "false")

@ConfigurationParameter(
        key = "cucumber.ansi-colors.disabled",
        value = "false")

@ConfigurationParameter(
        key = "cucumber.publish.quiet",
        value = "true")

@ConfigurationParameter(
        key = Constants.FILTER_TAGS_PROPERTY_NAME,
        value = "@ComplexScenario1")

public class Complex_Scenario1_Runner {
    // Left blank intentionally for JUnit 5 Engine routing
}

















//package runner;
//
//import org.junit.platform.suite.api.ConfigurationParameter;
//import org.junit.platform.suite.api.IncludeEngines;
//import org.junit.platform.suite.api.SelectClasspathResource;
//import org.junit.platform.suite.api.Suite;
//import io.cucumber.junit.platform.engine.Constants;
//
//@Suite
//@IncludeEngines("cucumber")
//@SelectClasspathResource("features") // Points to the features directory on the classpath (src/test/resources/features)
//@ConfigurationParameter(
//        key = Constants.GLUE_PROPERTY_NAME,
//        value = "stepDefinitions, hooks"
//)
//@ConfigurationParameter(
//        key = Constants.PLUGIN_PROPERTY_NAME,
//        value = "pretty, com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:, html:target/CucumberReports/CucumberReport.html"
//)
//@ConfigurationParameter(
//        key = Constants.EXECUTION_DRY_RUN_PROPERTY_NAME,
//        value = "false"
//)
//@ConfigurationParameter(
//        key = "cucumber.ansi-colors.disabled",
//        value = "false" // Replaces monochrome = true (false means colors are NOT disabled)
//)
//@ConfigurationParameter(
//        key = "cucumber.publish.quiet",
//        value = "false" // Suppresses the online report publishing banner in the console logs
//)
//public class TestRunner {
//    // This class remains completely empty.
//    // JUnit 5 uses the annotations above to handle all configuration mappings.
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
////package runner;
////
////import io.cucumber.junit.Cucumber;
////import io.cucumber.junit.CucumberOptions;
////import org.junit.runner.RunWith;
////
////@RunWith(Cucumber.class)
////@CucumberOptions(
////features = "src/test/resources/features",
////        glue = {"stepDefinitions", "hooks"},
////        plugin = {
////                "pretty",
////                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
////                "html:target/CucumberReports/CucumberReport.html"
////        },
////        dryRun=false,
////        monochrome=true,
////        publish=false
////
////)
////public class TestRunner
////{
////
////}
