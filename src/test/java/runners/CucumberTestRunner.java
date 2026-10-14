
package runners;

import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {
                "stepdefinitions",
                "com.automation.framework.hooks"
        },
        tags = "@Smoke and @Cart",
        plugin = {
                "pretty",
                "html:target/cucumber-reports.html"
        },
        monochrome = true
)
public class CucumberTestRunner
        extends AbstractTestNGCucumberTests {

        @Override
        @DataProvider(parallel = false)
        public Object[][] scenarios() {
                return super.scenarios();
        }
}
