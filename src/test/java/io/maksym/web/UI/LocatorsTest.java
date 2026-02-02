package io.maksym.web.UI;

import io.maksym.web.pages.BaseTest;
import io.maksym.web.pages.HomePage;
import io.maksym.web.pages.LocatorsPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;

public class LocatorsTest extends BaseTest {
    @Test
    @DisplayName("Verify that locators page is visible")
    public void testLocatorsVisibility() {
        HomePage homePage = new HomePage(page()).open();
        LocatorsPage locatorsPage = homePage.goToLocatorsPage();

        assertAll("Verify that locators page is visible and all elements are displayed on the page.",
                () -> locatorsPage.locatorsPageShouldBeOpened(),
                () -> locatorsPage.checkAllElementsAreDisplayed());
    }

}
