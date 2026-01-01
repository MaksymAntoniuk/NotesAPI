package io.maksym.web.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.maksym.web.components.FlashAlert;
import io.maksym.web.records.ui.UiUser;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static io.maksym.web.config.ApiEndpoints.BASE_URL_UI;

public class RegisterPage extends BasePage{
    private final FlashAlert flashAlert;
    private final Locator username = page.locator("//input[@name='username']");
    private final Locator password = page.locator("//input[@name='password']");
    private final Locator confirmPassword = page.locator("//input[@name='confirmPassword']");
    private final Locator submit = page.getByRole(AriaRole.BUTTON,
            new Page.GetByRoleOptions().setName("Register"));

    public RegisterPage(Page page) {
        super(page);
        this.flashAlert = new FlashAlert(page);
    }
    @Override
    protected String path() {
        return "/register";
    }
    
    private void fillUserName(String username){
        this.username.fill(username);
    }
    private void fillPassword(String password){
        this.password.fill(password);
    }
    private void fillConfirmPassword(String confirmPassword){
        this.confirmPassword.fill(confirmPassword);
    }
    
    private void registerClick(){
        submit.click();
    }
    
    public LoginPage registerNewUser(UiUser user){
        fillUserName(user.username());
        fillPassword(user.password());
        fillConfirmPassword(user.confirmPassword());
        registerClick();
        return new LoginPage(page);
    }

    public RegisterPage tryToRegisterWithInvalidUser(UiUser user){
        fillUserName(user.username());
        fillPassword(user.password());
        fillConfirmPassword(user.confirmPassword());
        registerClick();
        return this;
    }

    public FlashAlert flashAlert(){
        return flashAlert;
    }

    public FlashAlert submitExpectingError(){
        submit.click();
        return flashAlert;
    }

    public RegisterPage registerPageShouldBeOpened(){
        waitInterstitialAdToDisappear();
        assertThat(page).hasURL(BASE_URL_UI + "/register");
        assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setLevel(1)))
                .containsText("Test Register page for Automation Testing Practice");
        return this;
    }
    private void waitInterstitialAdToDisappear(){
        page.waitForSelector("body:not(:has(#google_vignette)):not(:has(#adtech_redirect))",
                new Page.WaitForSelectorOptions()
                        .setState(WaitForSelectorState.VISIBLE)
                        .setTimeout(5000)
        );
    }

}
