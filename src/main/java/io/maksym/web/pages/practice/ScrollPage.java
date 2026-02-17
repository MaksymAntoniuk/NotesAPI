package io.maksym.web.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static io.maksym.web.config.ApiEndpoints.BASE_URL_UI;

public class ScrollPage extends BasePage{
    private final Locator hidingButton = page.locator("#hidingButton");

    public ScrollPage(Page page) {
        super(page);
    }

    public void navigateTo(String url){
        page.navigate(url);
    }

    public void clickAfterScrolling(){
        hidingButton.scrollIntoViewIfNeeded();
        hidingButton.click();
    }

    public void scrollPageShouldBeOpened(){
        assertThat(page).hasURL(BASE_URL_UI + "/scrollbars");
    }
    public void hidingButtonShouldBeVisible(){
        assertThat(hidingButton).isVisible();
    }
    @Override
    protected String path() {
        return "/scrollbars";
    }
}
