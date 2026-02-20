package io.maksym.web.ui.practice_tests;

import io.maksym.web.pages.practice.HomePage;
import io.maksym.web.ui.pages.BaseTest;
import io.maksym.web.pages.ScrollPage;
import org.junit.jupiter.api.Assertions;

public class ScrollTest extends BaseTest {
    @org.junit.jupiter.api.Test
    public void ScrollTest(){
        HomePage homePage = new HomePage(page()).open();
        ScrollPage scrollPage = homePage.goToScrollPage();

        Assertions.assertAll(
                "",
                () -> scrollPage.scrollPageShouldBeOpened(),
                () -> scrollPage.hidingButtonShouldBeVisible(),
                () -> scrollPage.clickAfterScrolling()
        );
    }
}
