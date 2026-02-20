package io.maksym.web.components.my_notes;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;
import io.maksym.web.records.ui.MyNoteBody;
import io.qameta.allure.Step;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


public class NoteCard {
    private Locator root;
    private Locator noteTitle;
    private Locator noteDescription;
    private Locator noteTimestamp;

    private Locator completedCheckbox;
    private Locator viewNoteBtn;
    private Locator noteEditBtn;
    private Locator noteDeleteBtn;


    public NoteCard(Locator root) {
        this.root = root;
        noteTitle = root.getByTestId("note-card-title");
        noteDescription = root.getByTestId("note-card-description");
        noteTimestamp = root.getByTestId("note-card-updated-at");
        completedCheckbox = root.getByTestId("toggle-note-switch");
        viewNoteBtn = root.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("View"));
        noteEditBtn = root.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Edit"));
        noteDeleteBtn = root.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Delete"));
    }

    @Step("Assert Note data")
    public void assertNoteData(MyNoteBody note){
        assertThat(this.noteTitle).hasText(note.title());
        assertThat(this.noteDescription).hasText(note.description());
        if(note.completed()){
            assertThat(this.completedCheckbox).isChecked();
        } else {
            assertThat(this.completedCheckbox).not().isChecked();
        }
    }

    @Step("Click on View button")
    public void clickOnViewBtn(){
        viewNoteBtn.click();
    }
    @Step("Click on Edit button")
    public void clickOnEditBtn(){
        noteEditBtn.click();
    }
    @Step("Click on Delete button")
    public void clickOnDeleteBtn(){
        noteDeleteBtn.click();
    }
}


