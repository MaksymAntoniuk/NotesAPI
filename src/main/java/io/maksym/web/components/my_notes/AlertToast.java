package io.maksym.web.components.my_notes;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

public class AlertToast {
    Page page;
    private final Locator toast;
    private final Locator closeBtn;

    public AlertToast(Page page, String message) {
        this.page = page;
        toast = page.getByTestId("alert-message").getByText(message);
        closeBtn = page.getByTestId("alert-close");
    }
    @Step("Assert Alert Toast is visible")
    public void assertAlertToastIsVisible(){
        toast.waitFor();
        toast.isVisible();
    }
    @Step("Click on Close Button")
    public void clickOnCloseBtn(){
        closeBtn.waitFor();
        closeBtn.click();
    }
    @Step("Assert Alert is closed")
    public void assertAlertIsClosed(){
        assert(toast).isHidden();
        assert(closeBtn).isHidden();
        page.waitForTimeout(2000);
    }
}
