package io.maksym.web.ui.my_notes_tests;

import io.maksym.web.components.my_notes.NoteCard;
import io.maksym.web.enums.CategoryNote;
import io.maksym.web.pages.my_notes.MyNotesLoginPage;
import io.maksym.web.pages.my_notes.MyNotesPage;
import io.maksym.web.pages.my_notes.MyNotesRegisterPage;
import io.maksym.web.pages.my_notes.MyNotesWelcomePage;
import io.maksym.web.pages.practice.HomePage;
import io.maksym.web.records.ui.MyNoteBody;
import io.maksym.web.records.ui.MyNoteLoginUser;
import io.maksym.web.records.ui.MyNoteRegisterUser;
import io.maksym.web.ui.BaseTest;
import io.maksym.web.util.DataGenerators;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import jdk.jfr.Name;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Epic("My Notes App")
@Name("Verify user is able to Filter and Search Notes")
@Severity(io.qameta.allure.SeverityLevel.CRITICAL)
public class FilteringNoteTest extends BaseTest {
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
}
