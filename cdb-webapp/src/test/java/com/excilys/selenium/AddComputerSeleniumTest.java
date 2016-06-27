package com.excilys.selenium;

import static org.junit.Assert.fail;

import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;

import com.excilys.ResetDB;
import com.sun.jna.Platform;

public class AddComputerSeleniumTest {
	private WebDriver driver;
	private static DesiredCapabilities capabilities;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
	private ResourceBundle properties = ResourceBundle.getBundle("selenium");
//	private static final String BROWSER = "CHROME";
	private static final String BROWSER = "PHANTOMJS";
	
	@BeforeClass
	public static void configure() {
		if (BROWSER.equals("PHANTOMJS")) {
			capabilities = new DesiredCapabilities();
	        capabilities.setJavascriptEnabled(true);
	        capabilities.setCapability("takesScreenshot", false);
			capabilities.setCapability("platform", Platform.LINUX);
	        capabilities.setCapability("name", "Testing Selenium 2");
	        capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "src/test/resources/phantomjs");  
		} else {
			capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability("version", "11");
			capabilities.setCapability("platform", Platform.LINUX);
			capabilities.setCapability("name", "Testing Selenium 2");
		}
	}
	
	@Before
	public void setUp() throws Exception {
		ResetDB.setup();

		if (BROWSER.equals("PHANTOMJS")) {
			this.driver = new PhantomJSDriver(capabilities);
			driver.manage().window().setSize(new Dimension(1920, 1080));
		} else {
			System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");
			this.driver = new ChromeDriver();
		}
		
		baseUrl = properties.getString("URL");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	/**
	 * Test adding a valid computer and getting a success message
	 * @throws Exception
	 */
	@Test
	@Ignore("Not working on docker container")
	public void testAddGoodComputer() throws Exception {
		driver.get(baseUrl + "/computer-database/Dashboard");
		
		driver.findElement(By.cssSelector("body section > div:nth-child(1) a:nth-child(1)")).click();
		
		driver.findElement(By.cssSelector("body section div div form fieldset div:nth-child(1) input")).sendKeys("test 123");
		driver.findElement(By.cssSelector("body section div div form fieldset div:nth-child(2) input")).clear();
		driver.findElement(By.cssSelector("body section div div form fieldset div:nth-child(2) input")).sendKeys("16/05/2016");
		driver.findElement(By.cssSelector("body section div div form fieldset div:nth-child(3) input")).clear();
		driver.findElement(By.cssSelector("body section div div form fieldset div:nth-child(3) input")).sendKeys("31/05/2016");
		driver.findElement(By.cssSelector("body section div div form fieldset div:nth-child(4) select")).click();
		new Select(driver.findElement(By.cssSelector("body section div div form select"))).selectByVisibleText("RCA");
		driver.findElement(By.cssSelector("input.btn.btn-primary")).click();
		
		WebElement errorDivElt = driver.findElement(By.cssSelector(".alert"));
		Assert.assertEquals("Success computer added to the DB", errorDivElt.getText());
		
		driver.findElement(By.linkText("Application - Computer Database")).click();
	}

	/**
	 * Test adding a wrong (discontinued < introduced) computer and getting an error message
	 * @throws Exception
	 */
	@Test
	@Ignore("Not working on docker container")
	public void testAddWrongComputer() throws Exception {
		driver.get(baseUrl + "/computer-database/Dashboard");

		driver.findElement(By.cssSelector("body section > div:nth-child(1) a:nth-child(1)")).click();
		
		driver.findElement(By.cssSelector("body section div div form fieldset div:nth-child(1) input")).sendKeys("test 123");
		driver.findElement(By.cssSelector("body section div div form fieldset div:nth-child(2) input")).clear();
		driver.findElement(By.cssSelector("body section div div form fieldset div:nth-child(2) input")).sendKeys("31/05/2016");
		driver.findElement(By.cssSelector("body section div div form fieldset div:nth-child(3) input")).clear();
		driver.findElement(By.cssSelector("body section div div form fieldset div:nth-child(3) input")).sendKeys("16/05/2016");
		driver.findElement(By.cssSelector("body section div div form fieldset div:nth-child(4) select")).click();
		new Select(driver.findElement(By.cssSelector("body section div div form select"))).selectByVisibleText("RCA");
		driver.findElement(By.cssSelector("input.btn.btn-primary")).click();
		
		WebElement errorDivElt = driver.findElement(By.cssSelector(".alert"));
		Assert.assertEquals("Error! introduced date must be less than discontinued date!", errorDivElt.getText());

		driver.findElement(By.linkText("Application - Computer Database")).click();
	}
	
	/**
	 * Test editing a valid computer and getting a success message
	 * @throws Exception
	 */
	@Test
	@Ignore("Not working on docker container")
	public void testEditGoodComputer() throws Exception {
	    driver.get(baseUrl + "/computer-database/Dashboard");

	    // MacBook Pro
	    driver.findElement(By.cssSelector("table tbody tr:nth-child(1) a")).click();
	    driver.findElement(By.cssSelector("body section div div form fieldset div:nth-child(1) input")).sendKeys("Apple");
		driver.findElement(By.cssSelector("body section div div form fieldset div:nth-child(2) input")).clear();
		driver.findElement(By.cssSelector("body section div div form fieldset div:nth-child(2) input")).sendKeys("16/05/2016");
		driver.findElement(By.cssSelector("body section div div form fieldset div:nth-child(3) input")).clear();
		driver.findElement(By.cssSelector("body section div div form fieldset div:nth-child(3) input")).sendKeys("31/05/2016");
		driver.findElement(By.cssSelector("body section div div form fieldset div:nth-child(4) select")).click();
		new Select(driver.findElement(By.cssSelector("body section div div form select"))).selectByVisibleText("Thinking Machines");
		driver.findElement(By.cssSelector("input.btn.btn-primary")).click();
		WebElement errorDivElt = driver.findElement(By.id("errorMsg"));
		Assert.assertEquals("Success computer updated to the DB", errorDivElt.getText());

		driver.findElement(By.linkText("Application - Computer Database")).click();
	}
	
	/**
	 * Test editing a wrong (discontinued < introduced) computer and getting a success message
	 * @throws Exception
	 */
	@Test
	@Ignore("Not working on docker container")
	public void testEditWrongComputer() throws Exception {
	    driver.get(baseUrl + "/computer-database/Dashboard");

	    // MacBook Pro 15.4 inch
	    driver.findElement(By.cssSelector("table tbody tr:nth-child(1) a")).click();
		driver.findElement(By.cssSelector("body section div div form fieldset div:nth-child(1) input")).sendKeys("Apple");
		driver.findElement(By.cssSelector("body section div div form fieldset div:nth-child(2) input")).clear();
		driver.findElement(By.cssSelector("body section div div form fieldset div:nth-child(2) input")).sendKeys("31/05/2016");
		driver.findElement(By.cssSelector("body section div div form fieldset div:nth-child(3) input")).clear();
		driver.findElement(By.cssSelector("body section div div form fieldset div:nth-child(3) input")).sendKeys("16/05/2016");
		driver.findElement(By.cssSelector("body section div div form fieldset div:nth-child(4) select")).click();
		new Select(driver.findElement(By.cssSelector("body section div div form select"))).selectByVisibleText("Thinking Machines");
		driver.findElement(By.cssSelector("input.btn.btn-primary")).click();

		WebElement errorDivElt = driver.findElement(By.id("errorMsg"));
		Assert.assertEquals("Error! introduced date must be less than discontinued date!", errorDivElt.getText());

		driver.findElement(By.linkText("Application - Computer Database")).click();
	}
	
	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}
}