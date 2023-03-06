package stepdefinitions;

import pages.BasePage;
import cucumber.api.java.After;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import cucumber.api.Scenario;

public class Hooks extends BasePage{
    public Hooks(){
        super(driver);
    }    

    @After
    public static void tearDown(Scenario scenario){ 
            if(scenario.isFailed()){
                byte[] screenshot =((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
                scenario.embed(screenshot,"image/png");                
        }
        //driver.quit();
    }    
}