package stepDefinitions;

import org.testng.Assert;

import base.BaseClass;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginSteps extends BaseClass {

    @Given("user launches the Flipkart application")
    public void user_launches_the_flipkart_application() {
        initializeBrowser();
        System.out.println("✅ Browser launched and Flipkart opened");
    }

    @When("user clicks on the Login button")
    public void user_clicks_on_the_login_button() {
        System.out.println("🖱️ Clicked on Login button (we’ll automate later)");
    }

    @Then("login popup should be displayed")
    public void login_popup_should_be_displayed() {
        // Intentionally fail this step to check Extent report
        Assert.fail("For testing Extent Report — forcing failure");
    }

}
