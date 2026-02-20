package io.maksym.web.pages.my_notes;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.maksym.web.components.my_notes.AddNewNoteForm;
import io.maksym.web.components.my_notes.NavigationBar;
import io.maksym.web.components.my_notes.NoteCard;
import io.maksym.web.pages.BasePage;
import io.qameta.allure.Step;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class MyNotesPage extends BasePage {

    private final Locator addNoteBtn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("+ Add Note"));
    public NavigationBar navigationBar;
    public AddNewNoteForm addNewNoteForm;

    private final Locator allFilterBtn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("All"));
    private final Locator homeFilterBtn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Home"));
    private final Locator workFilterBtn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Work"));
    private final Locator personalFilterBtn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Personal"));

    private final Locator searchField = page.getByPlaceholder("Search notes...").first();
    private final Locator searchBtn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Search"));

    private final Locator noteCard = page.getByTestId("note-card");

    public MyNotesPage(Page page) {
        super(page);
        navigationBar = new NavigationBar(page);
        addNewNoteForm = new AddNewNoteForm(page);
    }

    @Step("Assert My Notes page is opened")
    public void assertMyNotesPageIsOpened(){
        navigationBar.assertProfileBtnIsVisible();
        navigationBar.assertLogOutBtnIsVisible();
    }
    @Step("Click on + Add Note button")
    public void clickOnAddNoteBtn(){
        addNoteBtn.click();
    }
    @Step("Get Note Card by title: {title}")
    public NoteCard getNoteByTitle(String title) {
        Locator card = noteCard
                .filter(new Locator.FilterOptions().setHasText(title))
                .first();
        return new NoteCard(card);
    }

    @Step("Assert Note Card is not visible with title: {title}")
    public void assertNoteIsNotVisible(String title){
        assertThat(page.getByTestId("note-card").filter(new Locator.FilterOptions().setHasText(title))).isHidden();
    }

    @Override
    protected String path() {
        return "/";
    }
}
