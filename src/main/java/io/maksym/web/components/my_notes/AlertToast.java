package io.maksym.web.components.my_notes;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

public class AlertToast {
    Page page;
    private final Locator toast;
    private final Locator closeBtn;

    public AlertToast(Page page) {
        this.page = page;
        toast = page.getByTestId("alert-message");
        closeBtn = page.getByTestId("alert-close");
    }

    @Step("Assert Alert is visible with message {message}")
    public void assertAlertToastIsVisible(String message){
        Locator specificToast = toast.getByText(message);
        specificToast.waitFor();
        specificToast.isVisible();
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
    }
}
