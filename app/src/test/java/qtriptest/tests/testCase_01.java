package qtriptest.tests;

import java.net.MalformedURLException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;

public class testCase_01 {
   static RemoteWebDriver driver;

   @BeforeTest
   public static void createDriver() throws MalformedURLException {
      DriverSingleton sbc1 = DriverSingleton.getInstanceOfSingletonBrowserClass();
      driver = sbc1.getDriver();
   }

   // @Test(description = "Verify user registration - login - logout",dataProvider = "data-provider",dataProviderClass = DP.class,groups = {"Login Flow"})
   public static void TestCase01(String UserName, String Password) throws InterruptedException {
      HomePage home = new HomePage(driver);
      home.gotoHomePage();
      Thread.sleep(2000L);
      home.clickRegister();
      RegisterPage register = new RegisterPage(driver);
      register.registerNewUser(UserName, Password, Password, true);
      Thread.sleep(3000L);
      String username = register.lastGeneratedUsername;
      LoginPage Login = new LoginPage(driver);
      Login.performLogin(username, Password);
      Thread.sleep(3000L);
      Assert.assertTrue(home.isUserLoggedIn());
      home.logOutUser();
      Thread.sleep(3000L);
      Assert.assertFalse(home.isUserLoggedIn());
      home.gotoHomePage();
   }

   @AfterSuite
   public static void quitDriver() throws InterruptedException {
      driver.quit();
   }
}
