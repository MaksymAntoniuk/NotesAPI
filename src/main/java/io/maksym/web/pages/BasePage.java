package io.maksym.web.pages;

import com.microsoft.playwright.Page;

import static io.maksym.web.config.ApiEndpoints.BASE_URL_UI;


public abstract class BasePage {
    protected final Page page;

    public BasePage(Page page) {
        this.page = page;
    }

    protected abstract String path();

    public <T extends BasePage> T open(){
        page.navigate(BASE_URL_UI + path());
        return (T) this;
    }
}
