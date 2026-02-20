package io.maksym.web.ui.my_notes_tests;

import io.maksym.web.components.my_notes.ConfirmationModal;
import io.maksym.web.components.my_notes.NoteCard;
import io.maksym.web.dto.Note.Note;
import io.maksym.web.enums.CategoryNote;
import io.maksym.web.enums.UiModalTitle;
import io.maksym.web.pages.my_notes.MyNotesLoginPage;
import io.maksym.web.pages.my_notes.MyNotesPage;
import io.maksym.web.pages.my_notes.MyNotesWelcomePage;
import io.maksym.web.pages.practice.HomePage;
import io.maksym.web.records.NoteBody;
import io.maksym.web.records.ui.MyNoteBody;
import io.maksym.web.records.ui.MyNoteLoginUser;
import io.maksym.web.ui.pages.BaseTest;
import io.maksym.web.util.DataGenerators;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.maksym.web.enums.UiModalTitle.DELETE_NOTE_MODAL_TITLE;
import static io.maksym.web.test_data.TestUsers.validUser;

@DisplayName("Verify Note creation")
public class NoteCreationTest extends BaseTest {

    @Test
    @DisplayName("Verify user is able to create note")
    public void verifyUserIsAbleToCreateNote(){

        String title = new DataGenerators().generateRandomName(4,10);
        String description = new DataGenerators().generateRandomDescription();
        CategoryNote category = new DataGenerators().generateRandomCatNote();
        MyNoteLoginUser user = new MyNoteLoginUser(validUser().getEmail(), validUser().getPassword());
           HomePage homePage = new HomePage(page()).open();
           MyNotesWelcomePage myNotesWelcomePage = homePage.goToMyNotesWelcomePage();

           MyNoteBody note = new MyNoteBody(title, description, category, true);

           myNotesWelcomePage.assertWelcomePageIsOpened();
           MyNotesLoginPage myNotesLoginPage = myNotesWelcomePage.goToLoginPage();
           myNotesLoginPage.assertLogInPageIsOpened();

           MyNotesPage myNotesPage = myNotesLoginPage.logInWithUser(user);
           myNotesPage.assertMyNotesPageIsOpened();
           myNotesPage.clickOnAddNoteBtn();
           myNotesPage.addNewNoteForm.asserAddNewNoteFormIsOpened();
           myNotesPage.addNewNoteForm.crateNewNote(note);

           NoteCard noteCard = myNotesPage.getNoteByTitle(note.title());
           noteCard.assertNoteData(note);

           noteCard.clickOnDeleteBtn();
           ConfirmationModal confirmationModal = new ConfirmationModal(page());
           confirmationModal.clickOnDeleteBtn();

           myNotesPage.assertNoteIsNotVisible(note.title());


    }
}
