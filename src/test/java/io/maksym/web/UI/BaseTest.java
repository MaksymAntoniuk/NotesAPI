package io.maksym.web.ui;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.nio.file.Paths;
import static io.maksym.web.config.ApiEndpoints.BASE_URL_UI;

public abstract class BaseTest {

    private static final ThreadLocal<Playwright> threadPlaywright = new ThreadLocal<>();
    private static final ThreadLocal<Browser> threadBrowser = new ThreadLocal<>();

    protected BrowserContext context;
    protected Page page;

    @BeforeEach
    public void beforeEach() {
        if (threadPlaywright.get() == null) {
            Playwright pw = Playwright.create();
            String isHeadlessString = System.getenv("HEADLESS");
            boolean isHeadless = isHeadlessString != null && Boolean.parseBoolean(isHeadlessString);

            Browser br = pw.chromium().launch(
                    new BrowserType.LaunchOptions().setHeadless(isHeadless)
            );

            threadPlaywright.set(pw);
            threadBrowser.set(br);
        }

        context = threadBrowser.get().newContext(new Browser.NewContextOptions()
                .setViewportSize(1920, 1080)
                .setRecordVideoDir(Paths.get("./target/video")));

        page = context.newPage();
        navigateToPageUrl(BASE_URL_UI);
    }

    @AfterEach
    public void afterEach() {
        if (context != null) {
            context.close();
        }
    }

    public void navigateToPageUrl(String url) {
        page.navigate(url);
    }

    public void linkClick(String linkTitle) {
        page.locator(linkTitle).click();
    }
}