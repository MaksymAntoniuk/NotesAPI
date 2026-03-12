package io.maksym.web.ui.practice_tests;

import io.maksym.web.pages.practice.HomePage;
import io.maksym.web.ui.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HomeTest extends BaseTest {

    @Test
    public void HomeTest() {
        HomePage homePage = new HomePage(page()).open();
        var title = homePage.getPageTitle();

        Assertions.assertEquals("Automation Testing Practice WebSite for QA and Developers", title);

    }
}
