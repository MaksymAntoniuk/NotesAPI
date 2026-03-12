package io.maksym.web.components.my_notes;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.maksym.web.pages.my_notes.MyNotesLoginPage;
import io.qameta.allure.Step;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static io.maksym.web.enums.UiModalTitle.DELETE_ACCOUNT_MODAL_TITLE;
import static io.maksym.web.enums.UiModalTitle.DELETE_NOTE_MODAL_TITLE;

public class ConfirmationModal {
    private final Page page;
    private final Locator root;
    private final Locator deleteBtn;
    private final Locator cancelBtn;


    public ConfirmationModal(Page page){
        this.page = page;
        this.root = page.locator(".modal-content");
        deleteBtn = root.getByTestId("note-delete-confirm");
        cancelBtn = root.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Cancel"));
    }

    @Step("Assert Modal is visible")
    public void assertModalIsVisible(String title){
        assertThat(root.getByText(title)).isVisible();
    }
    @Step("Assert Delete button is visible")
    public void assertDeleteBtnIsVisible(){
        deleteBtn.waitFor();
        assert(deleteBtn).isVisible();
    }
    @Step("Assert Cancel button is visible")
    public void assertCancelBtnIsVisible(){
        cancelBtn.waitFor();
    }
    @Step("Click on Cancel Button")
    public void clickOnDeleteBtn(){
        deleteBtn.click();
        assertThat(root).isHidden();
    }
    @Step("Click on Cancel Button")
    public void clickOnCancelBtn(){
        cancelBtn.click();
    }
}
