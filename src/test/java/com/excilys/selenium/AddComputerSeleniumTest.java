package com.excilys.selenium;

import static org.junit.Assert.fail;

import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;

import com.excilys.ResetDB;

public class AddComputerSeleniumTest {
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	private ResourceBundle properties = ResourceBundle.getBundle("selenium");
	
	@Before
	public void setUp() throws Exception {
		ResetDB.setup();
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability("version", "11");
		capabilities.setCapability("platform", Platform.LINUX);
		capabilities.setCapability("name", "Testing Selenium 2");

		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");
		this.driver = new ChromeDriver();
		baseUrl = properties.getString("URL");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	/**
	 * Test adding a valid computer and getting a success message
	 * @throws Exception
	 */
	@Test
	public void testAddGoodComputer() throws Exception {
		driver.get(baseUrl + "/computer-database/Dashboard");
		driver.findElement(By.id("addComputer")).click();
		driver.findElement(By.id("computerName")).sendKeys("test 123");
		driver.findElement(By.id("introduced")).clear();
		driver.findElement(By.id("introduced")).sendKeys("16/05/2016");
		driver.findElement(By.id("discontinued")).clear();
		driver.findElement(By.id("discontinued")).sendKeys("31/05/2016");
		driver.findElement(By.id("companyId")).click();
		new Select(driver.findElement(By.id("companyId"))).selectByVisibleText("RCA");
		driver.findElement(By.cssSelector("input.btn.btn-primary")).click();

		WebElement errorDivElt = driver.findElement(By.id("errorMsg"));
		Assert.assertEquals("Success computer added to the DB", errorDivElt.getText());
		
		driver.findElement(By.linkText("Application - Computer Database")).click();
	}

	/**
	 * Test adding a wrong (discontinued < introduced) computer and getting an error message
	 * @throws Exception
	 */
	@Test
	public void testAddWrongComputer() throws Exception {
		driver.get(baseUrl + "/computer-database/Dashboard");
		driver.findElement(By.id("addComputer")).click();
		driver.findElement(By.id("computerName")).sendKeys("test 123");
		driver.findElement(By.id("introduced")).clear();
		driver.findElement(By.id("introduced")).sendKeys("31/05/2016");
		driver.findElement(By.id("discontinued")).clear();
		driver.findElement(By.id("discontinued")).sendKeys("16/05/2016");
		driver.findElement(By.id("companyId")).click();
		new Select(driver.findElement(By.id("companyId"))).selectByVisibleText("RCA");
		driver.findElement(By.cssSelector("input.btn.btn-primary")).click();

		WebElement errorDivElt = driver.findElement(By.id("errorMsg"));
		Assert.assertEquals("Error! introduced date must be less than discontinued date!", errorDivElt.getText());

		driver.findElement(By.linkText("Application - Computer Database")).click();
	}
	
	/**
	 * Test editing a valid computer and getting a success message
	 * @throws Exception
	 */
	@Test
	public void testEditGoodComputer() throws Exception {
	    driver.get(baseUrl + "/computer-database/Dashboard");
	    driver.findElement(By.linkText("Apple")).click();
	    driver.findElement(By.id("computerName")).clear();
	    driver.findElement(By.id("computerName")).sendKeys("Apple");
		driver.findElement(By.id("introduced")).clear();
		driver.findElement(By.id("introduced")).sendKeys("31/05/2016");
		driver.findElement(By.id("discontinued")).clear();
		driver.findElement(By.id("discontinued")).sendKeys("16/06/2016");
	    new Select(driver.findElement(By.id("companyId"))).selectByVisibleText("Thinking Machines");
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
	public void testEditWrongComputer() throws Exception {
	    driver.get(baseUrl + "/computer-database/Dashboard");
	    driver.findElement(By.linkText("Apple")).click();
	    driver.findElement(By.id("computerName")).clear();
	    driver.findElement(By.id("computerName")).sendKeys("Apple");
		driver.findElement(By.id("introduced")).clear();
		driver.findElement(By.id("introduced")).sendKeys("31/05/2016");
		driver.findElement(By.id("discontinued")).clear();
		driver.findElement(By.id("discontinued")).sendKeys("16/04/2016");
	    new Select(driver.findElement(By.id("companyId"))).selectByVisibleText("Thinking Machines");
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