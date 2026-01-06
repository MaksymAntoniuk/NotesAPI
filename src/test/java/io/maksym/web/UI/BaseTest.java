package io.maksym.web.UI;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static io.maksym.web.config.ApiEndpoints.BASE_URL_UI;

public abstract class BaseTest {

    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    @BeforeEach
    void setUp() {
        boolean headless = Boolean.parseBoolean(
                System.getenv().getOrDefault("HEADLESS", "true")
        );

        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(headless)
        );

        context = browser.newContext(
                new Browser.NewContextOptions().setViewportSize(1920, 1080)
        );

        page = context.newPage();
        page.navigate(BASE_URL_UI);
    }

    @AfterEach
    void tearDown() {
        context.close();
        browser.close();
        playwright.close();
    }
}
