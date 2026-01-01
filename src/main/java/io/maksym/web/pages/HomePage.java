package io.maksym.web.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

public class HomePage extends BasePage {
    public HomePage(Page page) {
        super(page);
    }

    private final Locator webInputLink = page.getByRole(
            AriaRole.LINK,
            new Page.GetByRoleOptions().setName("Web inputs"));

    private final Locator loginPageLink = page.getByRole(
            AriaRole.LINK,
            new Page.GetByRoleOptions().setName("Test Login Page"));

    private final Locator registrationLink = page.getByRole(
            AriaRole.LINK,
            new Page.GetByRoleOptions().setName("Test Register Page"));
    private final Locator mainTitle = page.locator("#main-title");
    private final Locator scrollbarsLink = page.getByRole(
            AriaRole.LINK,
            new Page.GetByRoleOptions().setName("Scrollbars"));

public LoginPage goToLogin() {
    waitInterstitialAdToDisappear();
    loginPageLink.click();
    return new LoginPage(page);
}


    public RegisterPage goToRegisterPage(){
        waitInterstitialAdToDisappear();
        registrationLink.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(5000));

        registrationLink.click();

        page.waitForSelector("//h1[contains(.,'Test Register page')]",
                new Page.WaitForSelectorOptions()
                        .setState(WaitForSelectorState.VISIBLE)
                        .setTimeout(5000)
        );

        return new RegisterPage(page);
    }

    public ScrollPage goToScrollPage(){
    waitInterstitialAdToDisappear();
        scrollbarsLink.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(5000));
        scrollbarsLink.click();
        return new ScrollPage(page);
    }

    public String getPageTitle(){
        return mainTitle.innerText();
    }

    private void waitInterstitialAdToDisappear(){
        page.waitForSelector("body:not(:has(#google_vignette)):not(:has(#adtech_redirect))",
                new Page.WaitForSelectorOptions()
                        .setState(WaitForSelectorState.VISIBLE)
                        .setTimeout(5000)
        );
    }

    @Override
    protected String path() {
        return "/";
    }
}
