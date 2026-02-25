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
public class NoteCreationTest extends BaseTest {

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

    @Test
    @DisplayName("Verify user is able to Filter and Search Notes")
    @Description("""
    1. Navigate to https://practice.expandtesting.com/
    2. Click on the "Notes App | React" card
    3. Navigate to the Registration page
    4. Register a new user and log in to the application
    5. Create two notes: 
        - one with category "Work"
        - one with category "Personal"
    6. Apply the "Work" category filter
    7. Verify that the "Work" note is visible and the "Personal" note is hidden
    8. Apply the "Personal" category filter
    9. Verify that the "Personal" note is visible and the "Work" note is hidden
    10. Apply the "All" filter and verify that notes can be found via search
    11. Navigate to the Profile page and delete the user account
    """)
    public void verifyUserCanFilterNotesByCategories(){

        String workNoteTitle = new DataGenerators().generateRandomName(4,10);
        String workNoteDescription = new DataGenerators().generateRandomDescription();
        CategoryNote workNoteCategory = CategoryNote.WORK;

        String personalNoteTitle = new DataGenerators().generateRandomName(4,10);
        String personalNoteDescription = new DataGenerators().generateRandomDescription();
        CategoryNote personalNoteCategory = CategoryNote.PERSONAL;

        MyNoteBody workNote = new MyNoteBody(workNoteTitle, workNoteDescription, workNoteCategory, false);
        MyNoteBody personalNote = new MyNoteBody(personalNoteTitle, personalNoteDescription, personalNoteCategory, false);

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

        myNotesPage.noteModal.createNewNote(workNote);
        NoteCard workNoteCard = myNotesPage.getNoteByTitle(workNote.title());
        workNoteCard.assertNoteData(workNote);

        myNotesPage.clickOnAddNoteBtn();

        myNotesPage.noteModal.createNewNote(personalNote);
        NoteCard perosanalNoteCard = myNotesPage.getNoteByTitle(personalNote.title());
        perosanalNoteCard.assertNoteData(personalNote);

        myNotesPage.clickOnWorkFilterButton();
        NoteCard workNoteC = myNotesPage.getNoteByTitle(workNoteTitle);
        workNoteC.assertNoteData(workNote);
        myNotesPage.assertNoteIsNotVisible(personalNoteTitle);

        myNotesPage.clickOnPersonalFilterButton();
        NoteCard personalNoteC = myNotesPage.getNoteByTitle(personalNoteTitle);
        personalNoteC.assertNoteData(personalNote);
        myNotesPage.assertNoteIsNotVisible(workNoteTitle);

        myNotesPage.clickOnAllFilterButton();
        myNotesPage.searchNoteByTitle(workNoteTitle);
        myNotesPage.getNoteByTitle(workNoteTitle).assertNoteData(workNote);

        myNotesPage.navigationBar.clickOnProfileBtn().deleteUserProfile().assertLogInPageIsOpened();
    }

    @Test
    @DisplayName("Verify user is able to edit note")
    @Description("""
    1. Navigate to https://practice.expandtesting.com/
    2. Click on the "Notes App | React" card
    3. Navigate to the Registration page and create a new account
    4. Log in and create a new note with initial data
    5. Navigate to the Note View page by clicking the "View" button
    6. Verify initial note data and click the "Edit" button
    7. Update the note with new Title, Description, and Category
    8. Verify that the Note View page displays the updated information
    9. Change the note status to 'Completed'
    10. Delete the note and verify it is removed from the dashboard
    11. Navigate to the Profile page and delete the user account
    """)
    public void verifyUserIsAbleToEditNote(){

        String email = new DataGenerators().generateRandomEmail(true);
        String username = new DataGenerators().generateRandomName(4,6);
        String password = new DataGenerators().generateRandomPassword(6,10);
        String confirmPassword = password;

        String title = new DataGenerators().generateRandomName(4,10);
        String description = new DataGenerators().generateRandomDescription();
        CategoryNote category = new DataGenerators().generateRandomCatNote();

        String updatedTitle = new DataGenerators().generateRandomName(4,10);
        String updatedDescription = new DataGenerators().generateRandomDescription();
        CategoryNote updatedCategory = new DataGenerators().generateRandomCatNote();

        HomePage homePage = new HomePage(page()).open();
        MyNotesRegisterPage registerPage = homePage.goToMyNotesWelcomePage().goToRegisterPage();
        registerPage.registerNewUser(new MyNoteRegisterUser(email, username, password, confirmPassword));
        MyNotesPage myNotesPage =registerPage.clickLinkToLoginPage().logInWithUser(new MyNoteLoginUser(email, password));
        myNotesPage.assertMyNotesPageIsOpened();

        myNotesPage.clickOnAddNoteBtn();

        MyNoteBody note = new MyNoteBody(title, description, category, false);

        myNotesPage.noteModal.createNewNote(note);
        NoteCard createdNote = myNotesPage.getNoteByTitle(title);
        MyNotesViewPage viewPage = createdNote.clickOnViewBtn();

        viewPage.assertViewNotePageIsOpened();
        viewPage.assertNoteData(note);
        viewPage.clickOnEditBtn();
        NoteModal noteModal = new NoteModal(page());
        noteModal.editNoteWithNewData(new MyNoteBody(updatedTitle, updatedDescription, updatedCategory, false));
        viewPage.assertViewNotePageIsOpened();
        viewPage.assertNoteData(new MyNoteBody(updatedTitle, updatedDescription, updatedCategory, false));
        viewPage.setIsCompleted();

        MyNotesPage myNotesPage2 = viewPage.deleteNote();
        myNotesPage2.assertNoteIsNotVisible(updatedTitle);

        myNotesPage.navigationBar.clickOnProfileBtn().deleteUserProfile();
    }
}
