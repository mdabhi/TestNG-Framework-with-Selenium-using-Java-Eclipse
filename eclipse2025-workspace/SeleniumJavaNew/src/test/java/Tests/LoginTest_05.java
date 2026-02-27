package Tests;

/*Script for Day 5
 */

import static org.junit.Assert.assertEquals;
import static org.testng.Assert.*;

import java.time.Duration;
//import java.util.concurrent.TimeUnit;

//import java.util.concurrent.TimeUnit;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import utils.BasicUtils;

/**
 * 
 * @author Minita Dabhi
 * 
 * Verify the Login Section. 
 * The script uses parameterization to verify more test cases. 
 * Parameterization using TestNG
 * 
 */
public class LoginTest_05{

	private WebDriver driver; // Selenium control driver
	private String baseUrl; // baseUrl of website Guru99
	WebDriverWait wait;

	/**
	 * create test data for testing The test data include set of username,
	 * password
	 * 
	 * @return
	 * @throws Exception 
	 */
	@DataProvider(name = "Database1")
	public String[][] getTestData() throws Exception {
		
		String[][] data = BasicUtils.getDataFromExcel(BasicUtils.FILE_PATH, 
                BasicUtils.SHEET_NAME,
                BasicUtils.TABLE_NAME);

		// Debug: Print how many rows and columns were found
		//System.out.println("------Inside LoginTest Dataprovider loop-------");
		//System.out.println("Rows found: " + data.length);
		//System.out.println("Columns found: " + data[0].length);

		return data;
	}

	/**
	 * Before Testing Setup test environment before executing test
	 * 
	 * @throws Exception
	 * 
	 */
	@BeforeMethod
	public void setUp() throws Exception {

	   	// 1. Create FirefoxOptions instead of FirefoxBinary
        FirefoxOptions options = new FirefoxOptions();
        
        // 2. Set the binary path directly in options
        options.setBinary(BasicUtils.FIREFOX_PATH);
        
        // 3. Set the profile if needed
        FirefoxProfile profile = new FirefoxProfile();
        options.setProfile(profile);
        
	
        driver = new FirefoxDriver(options);
        driver.manage().window().maximize();
                
        baseUrl = BasicUtils.baseURL;
        //driver.manage().implicitlyWait(3, TimeUnit.SECONDS);
        driver.get(baseUrl);
     // Initialize WebDriverWait
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	}

	/**
	 * Above test script executed several times for each set of data used in @DataProvider
	 * annotation. Any failed test does not impact other set of execution.
	 * 
	 * SS1: Enter valid userid & password 
	 * Expected: Login successful home page shown 
	 * SS2: Enter invalid userid & valid password 
	 * SS3: Enter valid userid & invalid password 
	 * SS4: Enter invalid userid & invalid password 
	 * Expected:
	 * A pop-up �User or Password is not valid� is shown
	 * 	/**
	 * Start Testing Test script 05 
	 * 1) Go to http://www.demo.guru99.com/V4/ 
	 * 2) Enter valid UserId 
	 * 3) Enter valid Password 
	 * 4) Click Login Expected
	 * result: Login successful home page shown Message shown Welcome
	 * <managerid>
	 * @param username
	 * @param password
	 * @throws Exception
	 */

	@Test(dataProvider = "Database1")
	public void LoginPage(String username, String pass) throws Exception {
		String actualTitle;
		String actualBoxMsg;
		
		if (username== null && pass==null){
			tearDown();
		} 
		//setUp();
		driver.findElement(By.name("uid")).clear();
		driver.findElement(By.name("uid")).sendKeys(username);
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys(pass);
		driver.findElement(By.name("btnLogin")).click();

		// delay some seconds
		// Use this statement if your internet speed is slow
		// driver.manage().timeouts()
		// .implicitlyWait(Util.WAIT_TIME, TimeUnit.SECONDS);

		/* Determine Pass Fail Status of the Script
         * If login credentials are correct,  Alert(Pop up) is NOT present. An Exception is thrown and code in catch block is executed	
         * If login credentials are invalid, Alert is present. Code in try block is executed 	    
         */
	    try{ 
	    
	       	Alert alt = driver.switchTo().alert();
			actualBoxMsg = alt.getText(); // get content of the Alter Message
			alt.accept();
			//System.out.println("------------Inside try loop-----------------");
			 // Compare Error Text with Expected Error Value					
			assertEquals(actualBoxMsg,BasicUtils.EXPECT_ERROR);
			
		}    
	    catch (NoAlertPresentException Ex){ 
	    	actualTitle = driver.getTitle();
	    	//System.out.println("------------Inside catch loop-----------------");
			// On Successful login compare Actual Page Title with Expected Title
	    	assertEquals(actualTitle,BasicUtils.EXPECT_TITLE);
	    	
	    	// Get text displayes on login page 
			String pageText = driver.findElement(By.tagName("tbody")).getText();
			System.out.print(pageText);

			// Extract the dynamic text mngrXXXX on page		
			String[] parts = pageText.split(BasicUtils.PATTERN);
			String dynamicText = parts[1];
			System.out.print(dynamicText); 
			
			// Check that the dynamic text is of pattern mngrXXXX
			// First 4 characters must be "mngr"
			assertTrue(dynamicText.substring(1, 5).equals(BasicUtils.FIRST_PATTERN));
			// remain stores the "XXXX" in pattern mngrXXXX
			String remain = dynamicText.substring(dynamicText.length() - 4);
			// Check remain string must be numbers;
			assertTrue(remain.matches(BasicUtils.SECOND_PATTERN));
        } 
	}

	/**
	 * Complete the testing
	 * 
	 * @throws Exception
	 */
	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}
}