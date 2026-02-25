package io.maksym.web.components.my_notes;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.maksym.web.records.ui.MyNoteBody;
import io.qameta.allure.Step;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class NoteModal {
    Page page;
    private final Locator createBtn;
    private final Locator cancelBtn;

    private final Locator titleField;
    private final Locator descriptionField;

    private final Locator completedCheckbox;
    private final Locator categorySelect;

    private final Locator title;

    public NoteModal(Page page) {
        this.page = page;
        createBtn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(Pattern.compile("Create|Save")));
        cancelBtn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Cancel"));
        titleField = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Title"));
        descriptionField = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Description"));
        completedCheckbox = page.getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName("Completed"));
        categorySelect = page.getByTestId("note-category");
        title = page.locator(".modal-title");
    }

    @Step("Assert Add new note form is open")
    public void asserAddNewNoteFormIsOpened(){
        title.waitFor();
        assertThat(title).isVisible();
    }

    @Step("Fill in title field")
    public void fillInTitleField(String title){
        titleField.fill(title);
    }

    @Step("Fill in description field")
    public void fillInDescriptionField(String description){
        descriptionField.fill(description);
    }

    @Step("Select {0} option")
    public void selectCategory(String category){
        categorySelect.selectOption(category);
    }

    @Step("Check completed checkbox")
    public void checkCompletedCheckbox(){
        completedCheckbox.check();
        assertThat(completedCheckbox).isChecked();
    }

    @Step("Create new note")
    public void createNewNote(MyNoteBody note) {
        fillInTitleField(note.title());
        fillInDescriptionField(note.description());
        selectCategory(note.category().getCategory());
        if (note.completed()) {
            checkCompletedCheckbox();
        }
        clickOnCreateBtn();
    }

    @Step("Edit note")
    public void editNoteWithNewData(MyNoteBody note) {
        fillInTitleField(note.title());
        fillInDescriptionField(note.description());
        selectCategory(note.category().getCategory());
        if (note.completed()) {
            checkCompletedCheckbox();
        }
        clickOnCreateBtn();
    }

    @Step("Click on Create button")
    public void clickOnCreateBtn(){
        createBtn.click();
    }

    @Step("Click on Cancel button")
    public void clickOnCancelBtn(){
        cancelBtn.click();
    }

}
