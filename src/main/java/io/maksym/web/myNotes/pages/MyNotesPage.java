package io.maksym.web.myNotes.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.maksym.web.myNotes.components.NavigationBar;
import io.maksym.web.pages.BasePage;
import io.qameta.allure.Step;

public class MyNotesPage extends BasePage {

    NavigationBar navigationBar;

    public MyNotesPage(Page page) {
        super(page);
        navigationBar = new NavigationBar(page);
    }

    public void profileButtonShouldBeVisible(){
        navigationBar.profileBtnShouldBeVisible();
    }
    public void logOutBtnShouldBeVisible(){
        navigationBar.logOutBtnShouldBeVisible();
    }
    public void homeLogoBtnShouldBeVisible(){
        navigationBar.homeLogoBtnShouldBeVisible();
    }

    @Step("Click on Log Out button")
    public WelcomePage clickOnLogOutBtn(){
        navigationBar.clickOnLogOutBtn();
        return new WelcomePage(page);
    }


    @Override
    protected String path() {
        return "/";
    }
}
