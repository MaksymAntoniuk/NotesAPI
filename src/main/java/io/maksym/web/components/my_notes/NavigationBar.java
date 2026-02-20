package io.maksym.web.components.my_notes;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.maksym.web.pages.my_notes.MyNotesProfilePage;
import io.maksym.web.pages.my_notes.MyNotesWelcomePage;
import io.qameta.allure.Step;

public class NavigationBar {
    Page page;
    Locator profileBtn;
    Locator logOutBtn;
    Locator homeLogoBtn;

    public NavigationBar(Page page) {
        this.page = page;
        profileBtn = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Profile"));
        logOutBtn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Logout"));
        homeLogoBtn = page.getByTestId("home");
    }
    @Step("Assert Profile Button is visible")
    public void assertProfileBtnIsVisible(){
        profileBtn.waitFor();
        assert(profileBtn).isVisible();
    }
    @Step("Assert Log Out Button is visible")
    public void assertLogOutBtnIsVisible(){
        logOutBtn.waitFor();
        assert(logOutBtn).isVisible();
    }

    @Step("Click on Profile Button")
    public MyNotesWelcomePage clickOnLogOutBtn(){
        logOutBtn.waitFor();
        logOutBtn.click();
        return new MyNotesWelcomePage(page);
    }

    public MyNotesProfilePage clickOnProfileBtn(){
        profileBtn.waitFor();
        profileBtn.click();
        return new MyNotesProfilePage(page);
    }



}
