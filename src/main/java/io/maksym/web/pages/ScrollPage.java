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
        waitInterstitialAdToDisappear();
        hidingButton.scrollIntoViewIfNeeded();
        hidingButton.click();
    }

    public void scrollPageShouldBeOpened(){
        waitInterstitialAdToDisappear();
        assertThat(page).hasURL(BASE_URL_UI + "/scrollbars");
    }
    public void hidingButtonShouldBeVisible(){
        waitInterstitialAdToDisappear();
        assertThat(hidingButton).isVisible();
    }
    private void waitInterstitialAdToDisappear(){
        page.waitForSelector("body:not(:has(#google_vignette)):not(:has(#adtech_redirect))",
                new Page.WaitForSelectorOptions()
                        .setState(WaitForSelectorState.VISIBLE)
                        .setTimeout(5000)
        );
    }
    @Override
    protected String path() {
        return "";
    }
}
