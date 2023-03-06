package runners;

import org.junit.AfterClass;
import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import cucumber.api.SnippetType;
import pages.BasePage;

@RunWith(Cucumber.class)
@CucumberOptions (features = "src/test/resources/features",
				  tags = "@Run",				  
				  monochrome =true,			
				  glue = "stepdefinitions",
				  plugin ={"json:reports/report/cucumber_report.json"},
				  snippets = SnippetType.CAMELCASE )				  
				  
public class RunnerTags {
	@AfterClass
	public static void cleanDriver(){		
		BasePage.closeBrowser();
		BasePage.finish();
	}
}