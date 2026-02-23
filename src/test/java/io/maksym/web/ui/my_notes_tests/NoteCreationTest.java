package io.maksym.web.ui.my_notes_tests;

import io.maksym.web.components.my_notes.ConfirmationModal;
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
import io.qameta.allure.Step;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.maksym.web.test_data.TestUsers.validUser;

@DisplayName("Verify Note creation")
public class NoteCreationTest extends BaseTest {

    @Test
    @DisplayName("Verify user is able to create note")
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
           myNotesPage.addNewNoteForm.asserAddNewNoteFormIsOpened();
           myNotesPage.addNewNoteForm.crateNewNote(note);

           NoteCard noteCard = myNotesPage.getNoteByTitle(note.title());
           noteCard.assertNoteData(note);

           noteCard.clickOnDeleteBtn();
           ConfirmationModal confirmationModal = new ConfirmationModal(page());
           confirmationModal.clickOnDeleteBtn();

           myNotesPage.assertNoteIsNotVisible(note.title());

           myNotesPage.navigationBar.clickOnProfileBtn().clickDeleteAccountButton().assertLogInPageIsOpened();
    }

    @Test
    @DisplayName("Verify user is able to Filter and Search Notes")
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
        myNotesPage.addNewNoteForm.asserAddNewNoteFormIsOpened();

        myNotesPage.addNewNoteForm.crateNewNote(workNote);
        NoteCard workNoteCard = myNotesPage.getNoteByTitle(workNote.title());
        workNoteCard.assertNoteData(workNote);

        myNotesPage.clickOnAddNoteBtn();

        myNotesPage.addNewNoteForm.crateNewNote(personalNote);
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

        myNotesPage.navigationBar.clickOnProfileBtn().clickDeleteAccountButton().assertLogInPageIsOpened();
    }
}
