package io.maksym.web.ui.my_notes_tests;

import io.maksym.web.components.my_notes.NoteCard;
import io.maksym.web.components.my_notes.NoteModal;
import io.maksym.web.enums.CategoryNote;
import io.maksym.web.pages.my_notes.MyNotesPage;
import io.maksym.web.pages.my_notes.MyNotesRegisterPage;
import io.maksym.web.pages.my_notes.MyNotesViewPage;
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
@Name("Verify user is able to edit Note")
@Severity(io.qameta.allure.SeverityLevel.CRITICAL)
public class EditingNoteTest extends BaseTest {
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
