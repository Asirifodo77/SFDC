package runner;


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;






@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"classpath:features_files","classpath:features_files/Cobrand"} ,
        plugin = {"pretty", "html:target/cucumber-html-report","json:target/cucumber_json.json", "junit:target/cucumber-results.xml"},
        monochrome = true,glue ={"step_definitions"}
        //tags = {"@Senario_Validate_Dummy_Member_in_Sales_Force"}
)


public class TestRunner  {
}
