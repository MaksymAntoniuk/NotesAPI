package io.maksym.web.pages;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import io.maksym.web.helpers.AdBlocker;


import java.nio.file.Paths;

import static io.maksym.web.config.ApiEndpoints.BASE_URL;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;


public abstract class BaseTest {
    private final ThreadLocal<Playwright> playwright = new ThreadLocal<>();
    private static final ThreadLocal<Browser> browser = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> context = new ThreadLocal<>();
    public static final ThreadLocal<Page> page = new ThreadLocal<>();

    @BeforeEach
    public void beforeEach() {
        Boolean isHeadless = Boolean.parseBoolean(
                System.getenv().getOrDefault("HEADLESS", "true")
        );

        Playwright pw = Playwright.create();
        Browser br = pw
                .chromium()
                .launch(new BrowserType.LaunchOptions()
                        .setHeadless(isHeadless));
        System.out.println(br.version());
        BrowserContext ctx = br.newContext(new Browser.NewContextOptions()
                .setViewportSize(1920, 1080)
                .setRecordVideoDir(Paths.get("./target/video")));

        ctx.route("**/*", route -> {
            String url = route.request().url();

            if (url.contains("doubleclick") ||
                    url.contains("googlesyndication") ||
                    url.contains("adservice") ||
                    url.contains("googleads") ||
                    url.contains("g.doubleclick.net")) {

                System.out.println("🚫 Prevented AD request: " + url);
                route.abort();
            } else {
                route.resume();
            }
        });

        Page pg = ctx.newPage();
        playwright.set(pw);
        browser.set(br);
        context.set(ctx);
        page.set(pg);
        AdBlocker.killInterstitialAds(pg);

        pg.navigate(BASE_URL);
    }

    @AfterEach
    public void afterEach() {
        context.get().close();
        browser.get().close();
        playwright.get().close();

        page.remove();
        context.remove();
        browser.remove();
        playwright.remove();
    }

    protected Page page() {
        return page.get();
    }
}