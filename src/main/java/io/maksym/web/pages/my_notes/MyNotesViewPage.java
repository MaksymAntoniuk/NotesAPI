package io.maksym.web.pages.my_notes;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.maksym.web.components.my_notes.ConfirmationModal;
import io.maksym.web.enums.CategoryNote;
import io.maksym.web.pages.BasePage;
import io.maksym.web.records.ui.MyNoteBody;
import io.qameta.allure.Step;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class MyNotesViewPage extends BasePage {
    Locator noteTitle = page.getByTestId("note-card-title");
    Locator noteDescription = page.getByTestId("note-card-description");
    Locator noteUpdatedAt = page.getByTestId("note-card-updated-at");

    Locator editBtn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Edit"));
    Locator deleteBtn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Delete"));

    Locator isCompleted = page.getByTestId("toggle-note-switch");

    ConfirmationModal confirmationModal;


    public MyNotesViewPage(Page page){
        super(page);
        this.confirmationModal = new ConfirmationModal(page);
    }

    @Step("Assert View note page is opened")
    public void assertViewNotePageIsOpened(){
        page.waitForURL("https://practice.expandtesting.com/notes/app/notes/**");
    }

    @Step("Assert note data is correct")
    public void assertNoteData(MyNoteBody note){
        assertThat(noteTitle).hasText(note.title());
        assertThat(noteDescription).hasText(note.description());

        if (note.completed()) {
            assertThat(isCompleted).isChecked();
        }else{
            assertThat(isCompleted).not().isChecked();
        }

        assertTimestampIsUpdated();

        if(note.completed()){
            assertThat(this.isCompleted).isChecked();
            assertThat(noteTitle).hasCSS("background-color", "rgba(40, 46, 41, 0.6)");

        } else {
            assertThat(this.isCompleted).not().isChecked();
            assertCategoryColor(note.category());
        }
    }

    @Step("Assert color of category")
    private void assertCategoryColor(CategoryNote category){
        String expectedColor = switch (category) {
            case HOME -> "rgb(255, 145, 0)";
            case WORK -> "rgb(92, 107, 192)";
            case PERSONAL -> "rgb(50, 140, 160)";
        };
        assertThat(this.noteTitle).hasCSS("background-color", expectedColor);
    }

    @Step("Assert timestamp")
    public void assertTimestampIsUpdated(){
        String uiTimeText = noteUpdatedAt.innerText();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy 'at' HH:mm:ss", Locale.ENGLISH);

        LocalDateTime parsedUiTime = LocalDateTime.parse(uiTimeText, formatter);
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);

        long secondsBetween = ChronoUnit.SECONDS.between(parsedUiTime, now);

        if (Math.abs(secondsBetween) > 5){
            throw new AssertionError("");
        }
    }

    @Step("Click on Edit button")
    public void clickOnEditBtn(){
        editBtn.click();
    }

    @Step("Click on Delete button")
    public void clickOnDeleteBtn(){
        deleteBtn.click();
    }

    @Step("Confirm Delete note")
    public MyNotesPage deleteNote(){
        clickOnDeleteBtn();
        confirmationModal.clickOnDeleteBtn();
        return new MyNotesPage(page);
    }

    @Step("Check as Completed")
    public void setIsCompleted(){
        isCompleted.click();
    }

    @Override
    protected String path() {
        return "";
    }
}
