package io.maksym.web.myNotes.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class AlertToast {
    Page page;
    private final Locator toast;
    public AlertToast(Page page) {
        this.page = page;
        toast = page.getByTestId("#alert-message");
    }
    public void shouldBeVisible(){
        toast.waitFor();
        toast.isVisible();
    }
    public String getAlertText(){
        return toast.innerText();
    }
}
