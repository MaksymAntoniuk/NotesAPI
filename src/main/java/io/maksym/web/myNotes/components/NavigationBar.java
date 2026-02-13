package io.maksym.web.myNotes.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

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
    public void profileBtnShouldBeVisible(){
        profileBtn.waitFor();
        profileBtn.isVisible();
    }
    public void logOutBtnShouldBeVisible(){
        logOutBtn.waitFor();
        logOutBtn.isVisible();
    }
    public void homeLogoBtnShouldBeVisible(){
        homeLogoBtn.waitFor();
        homeLogoBtn.isVisible();
    }

    public void clickOnLogOutBtn(){
        logOutBtn.waitFor();
        logOutBtn.click();
    }



}
