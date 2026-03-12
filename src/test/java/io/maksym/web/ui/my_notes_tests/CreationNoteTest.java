package io.maksym.web.ui.my_notes_tests;

import io.maksym.web.components.my_notes.ConfirmationModal;
import io.maksym.web.components.my_notes.NoteCard;
import io.maksym.web.components.my_notes.NoteModal;
import io.maksym.web.enums.CategoryNote;
import io.maksym.web.pages.RegisterPage;
import io.maksym.web.pages.my_notes.*;
import io.maksym.web.pages.practice.HomePage;
import io.maksym.web.records.ui.MyNoteBody;
import io.maksym.web.records.ui.MyNoteLoginUser;
import io.maksym.web.records.ui.MyNoteRegisterUser;
import io.maksym.web.ui.BaseTest;
import io.maksym.web.util.DataGenerators;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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
    8. Assert counter of notes shows correct value
    9. Verify that the note is visible on the dashboard with correct data (Title, Description, Category)
    10. Set note as Completed
    11. Assert that message is shown when all notes are completed
    12. Delete the created note and confirm it is no longer visible
    13. Navigate to the Profile page and delete the user account
    """)
    @Severity(SeverityLevel.CRITICAL)
    public void verifyUserIsAbleToCreateNote(){

        String title = new DataGenerators().generateRandomName(4,10);
        String description = new DataGenerators().generateRandomDescription();
        CategoryNote category = new DataGenerators().generateRandomCatNote();

        MyNoteBody note = new MyNoteBody(title, description, category, false);

        String email = new DataGenerators().generateRandomEmail(true);
        String username = new DataGenerators().generateRandomName(4,6);
        String password = new DataGenerators().generateRandomPassword(6,10);
        String confirmPassword = password;
        int expectedCompleted = 0;
        int expectedTotal = 1;

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

        myNotesPage.assertCounterOfNotes(expectedCompleted, expectedTotal);

        NoteCard noteCard = myNotesPage.getNoteByTitle(note.title());
        noteCard.assertNoteData(note);

        noteCard.setNoteIsCompleted();
        myNotesPage.asserAllNotesCompleted();

        noteCard.clickOnDeleteBtn();
        ConfirmationModal confirmationModal = new ConfirmationModal(page());
        confirmationModal.clickOnDeleteBtn();

        myNotesPage.assertNoteIsNotVisible(note.title());

        myNotesPage.navigationBar.clickOnProfileBtn().deleteUserProfile().assertLogInPageIsOpened();
    }

    @Test
    @DisplayName("Verify user is able to create 20 notes")
    @Description("""
    1. Navigate to https://practice.expandtesting.com/
    2. Click on the "Notes App | React" card
    3. Navigate to the Registration page
    4. Register a new user with random credentials
    5. Log in with the newly created account
    6. Click on 'Add Note' and fill out the form with a random title, description, and category
    7. Repeat step 6 twenty (20) times
    8. Verify the counter of total notes and completed notes matches the created data
    9. Delete the created notes and confirm it is no longer visible
    10. Navigate to the Profile page and delete the user account
    """)
    @Severity(SeverityLevel.CRITICAL)
    public void verifyUserIsAbleToCreate20Notes(){
        int noteCount = 5;
        int expectedCompleted = 0;

        String email = new DataGenerators().generateRandomEmail(true);
        String username = new DataGenerators().generateRandomName(4,6);
        String password = new DataGenerators().generateRandomPassword(6,10);
        String confirmPassword = password;

        List<String> createdNotes = new ArrayList<>();

        HomePage homePage = new HomePage(page()).open();
        MyNotesWelcomePage myNotesWelcomePage = homePage.goToMyNotesWelcomePage();
        myNotesWelcomePage.assertWelcomePageIsOpened();

        MyNoteRegisterUser registerUser = new MyNoteRegisterUser(email, username, password, confirmPassword);
        MyNoteLoginUser loginUser = new MyNoteLoginUser(email, password);

        MyNotesRegisterPage myNotesRegisterPage = myNotesWelcomePage.goToRegisterPage();
        myNotesRegisterPage.assertRegistrationPageIsOpened();
        myNotesRegisterPage.registerNewUser(registerUser);
        myNotesRegisterPage.successRegistration.assertSuccessMessageIsVisible();

        MyNotesPage myNotesPage = myNotesRegisterPage.clickLinkToLoginPage().logInWithUser(loginUser);
        myNotesPage.assertMyNotesPageIsOpened();
        for (int i = 0; i < noteCount; i++) {
            MyNoteBody note = new MyNoteBody(new DataGenerators().generateRandomName(4,10),
                    new DataGenerators().generateRandomDescription(), CategoryNote.PERSONAL,
                    new DataGenerators().generateRandomBoolean());

            if(note.completed()){
                expectedCompleted++;
            }

            myNotesPage.clickOnAddNoteBtn();
            myNotesPage.noteModal.createNewNote(note);
            createdNotes.add(note.title());
        }

        myNotesPage.assertCounterOfNotes(expectedCompleted, createdNotes.size());

        for (int i = 0; i < createdNotes.size(); i++) {
            myNotesPage.getNoteByTitle(createdNotes.get(i)).clickOnDeleteBtn();
            ConfirmationModal confirmationModal = new ConfirmationModal(page());
            confirmationModal.clickOnDeleteBtn();

        }
        myNotesPage.navigationBar.clickOnProfileBtn().deleteUserProfile();
    }
}
