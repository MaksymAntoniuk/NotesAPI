package io.maksym.web.ui.my_notes_tests;

import io.maksym.web.components.my_notes.ConfirmationModal;
import io.maksym.web.components.my_notes.NoteCard;
import io.maksym.web.components.my_notes.NoteModal;
import io.maksym.web.enums.CategoryNote;
import io.maksym.web.pages.my_notes.*;
import io.maksym.web.pages.practice.HomePage;
import io.maksym.web.records.ui.MyNoteBody;
import io.maksym.web.records.ui.MyNoteLoginUser;
import io.maksym.web.records.ui.MyNoteRegisterUser;
import io.maksym.web.ui.BaseTest;
import io.maksym.web.util.DataGenerators;
import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Verify Note creation")
public class CreationNoteTest extends BaseTest {

    @Test
    @DisplayName("Verify user is able to create note")
    @Description("""
    1. Navigate to https://practice.expandtesting.com/
    2. Click on the "Notes App | React" card
    3. Navigate to the Registration page
    4. Register a new user with random credentials
    5. Log in with the newly created account
    6. Click on 'Add Note' and fill out the form with a random title, description, and category
    7. Submit the form to create the note
    8. Verify that the note is visible on the dashboard with correct data (Title, Description, Category)
    9. Delete the created note and confirm it is no longer visible
    10. Navigate to the Profile page and delete the user account
    """)
    public void verifyUserIsAbleToCreateNote(){

        String title = new DataGenerators().generateRandomName(4,10);
        String description = new DataGenerators().generateRandomDescription();
        CategoryNote category = new DataGenerators().generateRandomCatNote();

        MyNoteBody note = new MyNoteBody(title, description, category, true);

        String email = new DataGenerators().generateRandomEmail(true);
        String username = new DataGenerators().generateRandomName(4,6);
        String password = new DataGenerators().generateRandomPassword(6,10);
        String confirmPassword = password;

        MyNoteRegisterUser user = new MyNoteRegisterUser(email, username, password, confirmPassword);
        MyNoteLoginUser loginUser = new MyNoteLoginUser(email, password);

        HomePage homePage = new HomePage(page()).open();
        MyNotesWelcomePage myNotesWelcomePage = homePage.goToMyNotesWelcomePage();
        myNotesWelcomePage.assertWelcomePageIsOpened();

        MyNotesRegisterPage myNotesRegisterPage = myNotesWelcomePage.goToRegisterPage();
        myNotesRegisterPage.assertRegistrationPageIsOpened();

        myNotesRegisterPage.registerNewUser(user);
        myNotesRegisterPage.successRegistration.assertSuccessMessageIsVisible();

        MyNotesLoginPage myNotesLoginPage = myNotesRegisterPage.clickLinkToLoginPage();
        myNotesLoginPage.assertLogInPageIsOpened();

        MyNotesPage myNotesPage = myNotesLoginPage.logInWithUser(loginUser);
        myNotesPage.assertMyNotesPageIsOpened();

           myNotesPage.clickOnAddNoteBtn();
           myNotesPage.noteModal.asserAddNewNoteFormIsOpened();
           myNotesPage.noteModal.createNewNote(note);

           NoteCard noteCard = myNotesPage.getNoteByTitle(note.title());
           noteCard.assertNoteData(note);

           noteCard.clickOnDeleteBtn();
           ConfirmationModal confirmationModal = new ConfirmationModal(page());
           confirmationModal.clickOnDeleteBtn();

           myNotesPage.assertNoteIsNotVisible(note.title());

           myNotesPage.navigationBar.clickOnProfileBtn().deleteUserProfile().assertLogInPageIsOpened();
    }
}
