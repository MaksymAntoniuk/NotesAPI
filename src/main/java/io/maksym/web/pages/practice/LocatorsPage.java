package io.maksym.web.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static io.maksym.web.config.ApiEndpoints.BASE_URL_UI;

public class LocatorsPage extends BasePage{
    private Locator addItemBtn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add Item"));
    private Locator contactLink = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Contact"));
    private Locator getAlertByText = page.getByText("\uD83D\uDD25 Hot Deal: Buy 1 Get 1 Free");
    private Locator getByTextParagraph = page.getByText("Latest news and updates");

    private Locator getDdlCounryByLabel = page.getByLabel("Choose a country");
    private Locator getEmailFieldByLabel = page.getByLabel("Email for newsletter");

    private Locator getSearchByPlaceholder = page.getByPlaceholder("Search the site");
    private Locator getFilterByPlaceholder = page.getByPlaceholder("Filter by tag");

    private Locator getImageByAlt = page.getByAltText("User avatar");
    private Locator getAlertByTestId = page.getByTestId("status-message");
    private Locator getUsernameByTestId = page.getByTestId("user-name");

    private Locator getByLegacyCssSelector = page.locator("span.legacy-css.text-primary");

    private Locator getGroupByXPath = page.locator("//ul[@class='list-group legacy-list']");
    private Locator getFirstTask = page.locator("//ul[contains(@class,'list-group legacy-list')]/li[1]");
    private Locator getSecondTask = page.locator("//ul[contains(@class,'list-group legacy-list')]/li[2]");
    private Locator getThirdTask = page.locator("//ul[contains(@class,'list-group legacy-list')]/li[3]");

    public LocatorsPage(Page page) {
        super(page);
    }

    public LocatorsPage locatorsPageShouldBeOpened(){
        assertThat(page).hasURL(BASE_URL_UI + "/locators");
        return this;
    }

    public void checkAllElementsAreDisplayed(){
        assertThat(addItemBtn).isVisible();
        assertThat(contactLink).isVisible();
        assertThat(getAlertByText).isVisible();
        assertThat(getByTextParagraph).isVisible();
        assertThat(getDdlCounryByLabel).isVisible();
        assertThat(getEmailFieldByLabel).isVisible();
        assertThat(getSearchByPlaceholder).isVisible();
        assertThat(getFilterByPlaceholder).isVisible();
        assertThat(getImageByAlt).isVisible();
        assertThat(getAlertByTestId).isVisible();
        assertThat(getUsernameByTestId).isVisible();
        assertThat(getByLegacyCssSelector).isVisible();
        assertThat(getGroupByXPath).isVisible();
        assertThat(getFirstTask).isVisible();
        assertThat(getSecondTask).isVisible();
        assertThat(getThirdTask).isVisible();
    }


    @Override
    protected String path() {
        return "/locators";
    }
}
