package io.maksym.web.UI;

import io.maksym.web.pages.HomePage;
import io.maksym.web.pages.ScrollPage;
import io.maksym.web.ui.BaseTest;
import org.junit.jupiter.api.Assertions;

public class ScrollTest extends BaseTest {
    @org.junit.jupiter.api.Test
    public void ScrollTest(){
        HomePage homePage = new HomePage(page).open();
        ScrollPage scrollPage = homePage.goToScrollPage();

        Assertions.assertAll(
                "",
                () -> scrollPage.scrollPageShouldBeOpened(),
                () -> scrollPage.hidingButtonShouldBeVisible(),
                () -> scrollPage.clickAfterScrolling()
        );
    }
}
