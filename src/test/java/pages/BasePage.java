package pages;

import java.io.File;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.OutputType;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class BasePage {

    protected static WebDriver driver;
    private static WebDriverWait wait;
    private static Actions action;

    static {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/driver/chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();        
        driver = new ChromeDriver(chromeOptions);         
        wait = new WebDriverWait(driver, 5);
        driver.manage().window().maximize();                   
    }     
    public BasePage(WebDriver driver) {
        BasePage.driver = driver;
        wait = new WebDriverWait(driver, 5);     
    }
    public static void navigateTo(String url) {       
        driver.get(url);      
    }
    //cierra el navegador al final de la ejecucion
    public static void closeBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }
    /*metodo para usar PageFactory
        PageFactory.initElements(driver, this);*/    
     /*Clase para usar PageFactory asi como la pagina PageFactoryPage
    public BasePage(WebDriver driver) {
        BasePage.driver = driver;
        PageFactory.initElements(driver, this);
        wait =new WebDriverWait(driver, 10);
       }*/

    /*Agrega espera explicita hasta que encuentre el localizador del elemento (implicita es esperar 
    siempre  explicita es esperar hasta que alguna condicion se cumpla ejemplo encontrar el objeto)*/
    private WebElement Find(String locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
    }          

    public void clickElement(String locator) {
        Find(locator).click();
    }
    
    public void write(String locator, String textoToWrite){
        Find(locator).clear();
        Find(locator).sendKeys(textoToWrite);
    }  

     //Seleciona de una lista desplegable por valor, texto ú Orden
    public void selectFromDropdownByValue(String locator,String valueToSelect){
        Select dropdown =new Select(Find (locator));
        dropdown.selectByValue(valueToSelect);
    }
    public void selectFromDropdownByIndex(String locator,int valueToSelect){
        Select dropdown =new Select(Find (locator));
        dropdown.selectByIndex(valueToSelect);
    }
    public void selectFromDropdownByText(String locator,String valueToSelect){
        Select dropdown =new Select(Find (locator));
        dropdown.selectByVisibleText(valueToSelect);
    }
      //Pasar el mouse por encima
      public void hoverOverElement (String locator){
        action.moveToElement(Find(locator));
    }
  
    //Doble Click
    public void doubleClick (String locator){
        action.doubleClick(Find(locator));
    }
    //Click Derecho
    public void rightClick(String locator){
        action.contextClick(Find(locator));
    }
    
    //devuelve el string de una grilla estatica o de una tabla
    public String getValueFromTable(String locator, int row, int column){
        String cellINeed = locator+"/table/tbody/tr["+row+"]/td["+column+"]";
        return Find(cellINeed).getText();
    }
    //Envia valor a una grilla estatica o de una tabla
    public void steValueOnTable(String locator, int row, int column, String stringToSend){
        String cellToFill = locator+"/table/tbody/tr["+row+"]/td["+column+"]";
        Find(cellToFill).sendKeys(stringToSend);
    }
    //Estos 2 metodos es para ubicarse en los frames de las paginas 
    public void switchToiFrame(int iFrameIndex){
        driver.switchTo().frame(iFrameIndex);
    } 
    public void switchToParentFrame(){
        driver.switchTo().parentFrame();
    }

    //Manejo de Alertas Ventanas emergenteso alert box
    public void dismissAlert(){      
        driver.switchTo().alert().dismiss();
    }
    //Para el uso de Asserts o validaciones (captura el valor de un elemento que definimos en el locator)
    public String textFromElement(String locator){
        return Find(locator).getText();
    }

    // para usar  con assert true o false, si es boolean devuelve (V) verdadero o (F) falso    
    //verifica que por ejemplo un boton esta disponible para hacer click
    public boolean elementIsEnabled(String locator){
        return Find(locator).isEnabled();
    }
    //verifica que el elemento esta ahi
    public boolean elementIsDisplayed(String locator){
        return Find(locator).isDisplayed();
    }
    //verifica que en un dropdown  o checkbox este selecionado 
    public boolean elementIsSelected(String locator){
        return Find(locator).isSelected();
    }

    //trae una lista de elementos 
    public List<WebElement>bringMeAllElements(String locator){
        return driver.findElements(By.xpath(locator));
    }
    //Trae elementos y le da clic 
    public void selectNthElements(String locator , int index){
        List<WebElement> results = driver.findElements(By.xpath(locator));
        results.get(index).click();
    }

    //buscar un elemento por linktext
    public void goToLinkText(String linkText){
        driver.findElement(By.linkText(linkText)).click();
    }  

    //Mueve el scroll de la pantalla
    public void moveScroll(){      
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,800)");    
    }
    //Hace zoom a la pantalla
    public void zoomPage(){      
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("document.body.style.zoom='80%'");    
    } 
    //Toma pantallazo y lo almacena en una ruta
    public static void takeSnapShot(){
        try {
            //Convertir el objeto WebDriver en TakeScreenshot
            TakesScreenshot scrShot =((TakesScreenshot)driver);
            //Llamar al método getScreenshotAs method para crear el archivo de imagen
            File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
            //Mover el archivo de imagen a la nueva ruta de destino
            File DestFile=new File("C:/VisualCode/TestNode/reports/Imgen.png");
            //Copiar el archivo en el destino
            FileUtils.copyFile(SrcFile, DestFile);
      
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    //Ejecuta comando para la generacion del reporte NodeJS             
    public static void finish() {
        try {   
            File directory = new File("C:/VisualCode/BasePOM_Gradle_NodeJS/reports");         
            String[] cmd = {"cmd.exe", "/c", "npm run report"};            
            System.out.println("Generating Reports...");
            ProcessBuilder pb = new ProcessBuilder(cmd);
            pb.directory(directory);
            Process p = pb.start();
            p.waitFor(); 
            System.out.println("Reports Generated Successfully!");
        } catch (Exception ex) {
            System.err.println("The process was interrupted: " + ex.getMessage());
        }                    
    }    
}