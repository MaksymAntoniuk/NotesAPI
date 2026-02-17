package io.maksym.web.components.my_notes;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.maksym.web.pages.my_notes.MyNotesLoginPage;
import io.qameta.allure.Step;

public class DeleteAccountModal {
    Page page;
    Locator modalTitle;
    Locator deleteBtn;
    Locator cancelBtn;

    public DeleteAccountModal(Page page){
        this.page = page;
        modalTitle = page.getByText("Do you really want to delete your account?");
        deleteBtn = page.getByTestId("note-delete-confirm");
        cancelBtn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Cancel"));
    }

    @Step("Assert Modal is visible")
    public void assertModalIsVisible(){
        modalTitle.waitFor();
        modalTitle.isVisible();
    }
    @Step("Assert Delete button is visible")
    public void assertDeleteBtnIsVisible(){
        deleteBtn.waitFor();
        deleteBtn.isVisible();
    }
    @Step("Assert Cancel button is visible")
    public void assertCancelBtnIsVisible(){
        cancelBtn.waitFor();
    }
    @Step("Click on Cancel Button")
    public MyNotesLoginPage clickOnDeleteBtn(){
        deleteBtn.click();
        return new MyNotesLoginPage(page);
    }
    @Step("Click on Cancel Button")
    public void clickOnCancelBtn(){
        cancelBtn.click();
    }
}
