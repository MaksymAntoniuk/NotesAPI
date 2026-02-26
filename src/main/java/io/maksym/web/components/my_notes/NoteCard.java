package io.maksym.web.components.my_notes;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;
import io.maksym.web.enums.CategoryNote;
import io.maksym.web.pages.my_notes.MyNotesViewPage;
import io.maksym.web.records.ui.MyNoteBody;
import io.qameta.allure.Step;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
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
        viewNoteBtn = root.getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("View"));
        noteEditBtn = root.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Edit"));
        noteDeleteBtn = root.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Delete"));
    }

    @Step("Assert Note data")
    public void assertNoteData(MyNoteBody note){
        assertThat(this.noteTitle).hasText(note.title());
        assertThat(this.noteDescription).hasText(note.description());
        assertTimestampIsUpdated();
        if(note.completed()){
            assertThat(this.completedCheckbox).isChecked();
            assertThat(noteTitle).hasCSS("background-color", "rgba(40, 46, 41, 0.6)");

        } else {
            assertThat(this.completedCheckbox).not().isChecked();
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
        String uiTimeText = noteTimestamp.innerText();
        System.out.println(uiTimeText);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy 'at' HH:mm:ss", Locale.ENGLISH);

        LocalDateTime parsedUiTime = LocalDateTime.parse(uiTimeText, formatter);
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);

        long secondsBetween = ChronoUnit.SECONDS.between(parsedUiTime, now);

        if (Math.abs(secondsBetween) > 60){
            throw new AssertionError("Timestamp drift too high! UI (UTC): " + uiTimeText +
                    ", System (UTC): " + now.format(formatter));
        }
    }

    @Step("Click on View button")
    public MyNotesViewPage clickOnViewBtn(){
        viewNoteBtn.click();
        return new MyNotesViewPage(root.page());
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


