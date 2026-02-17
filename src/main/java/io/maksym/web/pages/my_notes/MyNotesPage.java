package io.maksym.web.pages.my_notes;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.maksym.web.components.my_notes.NavigationBar;
import io.maksym.web.pages.BasePage;
import io.qameta.allure.Step;

public class MyNotesPage extends BasePage {

    public NavigationBar navigationBar;
    Locator profileButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Profile"));


    public MyNotesPage(Page page) {
        super(page);
        navigationBar = new NavigationBar(page);
    }

    @Step("Click on Log Out button")
    public MyNotesWelcomePage clickOnLogOutBtn(){
        navigationBar.clickOnLogOutBtn();
        return new MyNotesWelcomePage(page);
    }
    @Step("Assert My Notes page is opened")
    public void assertMyNotesPageIsOpened(){
        navigationBar.assertProfileBtnIsVisible();
        navigationBar.assertLogOutBtnIsVisible();
    }
    @Step("Assert Profile Button is visible")
    public void assertProfileBtnIsVisible(){
        profileButton.waitFor();
        profileButton.isVisible();
    }
    @Step("Navigate to Profile Page")
    public MyNotesProfilePage navigateToProfilePage(){
        profileButton.click();
        return new MyNotesProfilePage(page);
    }


    @Override
    protected String path() {
        return "/";
    }
}
