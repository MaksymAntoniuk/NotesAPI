package io.maksym.web.ui.practice_tests;

import io.maksym.web.pages.practice.HomePage;
import io.maksym.web.ui.BaseTest;
import io.maksym.web.pages.WebInputsPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WebInputsTest extends BaseTest {

    @ParameterizedTest
    @CsvSource({
            "123, Text, Password, 2020-10-20",
            "456, Text2, Password2, 2021-11-21",
            "789, Text3, Password3, 2022-12-22"
    })
    @DisplayName("Verify that user is able to fill web inputs successfully")
    public void testWebInputs(String number, String text, String password, String date) {
        HomePage homePage = new HomePage(page()).open();

        WebInputsPage webInputsTest = homePage.goToWebInputsPage();
        webInputsTest.webInputsPageShouldBeOpened();

        webInputsTest.fillInputsWithValues(number, text, password, date);
        webInputsTest.outputFieldsVisible();

        assertAll("Verify inputs and outputs",
                () -> assertEquals(number, webInputsTest.getNumberOutputText(), "Incorrect number value"),
                () -> assertEquals(text, webInputsTest.getTextOutputText(), "Incorrect text value"),
                () -> assertEquals(password, webInputsTest.getPasswordOutputText(), "Incorrect password value"),
                () -> assertEquals(date, webInputsTest.getDateOutputText(), "Incorrect date value"),

                () -> assertEquals("Input: Number", webInputsTest.getNumberLabelText()),
                () -> assertEquals("Input: Text", webInputsTest.getTextLabelText()),
                () -> assertEquals("Input: Password", webInputsTest.getPasswordLabelText()),
                () -> assertEquals("Input: Date", webInputsTest.getDateLabelText()),

                () -> assertEquals("Output: Number", webInputsTest.getNumberLabelTextOutput()),
                () -> assertEquals("Output: Text", webInputsTest.getTextLabelTextOutput()),
                () -> assertEquals("Output: Password", webInputsTest.getPasswordLabelTextOutput()),
                () -> assertEquals("Output: Date", webInputsTest.getDateLabelTextOutput())

        );


    }

    @Test
    @DisplayName("Verify that user is able to clear web inputs successfully")
    public void testClearInputs() {
        HomePage homePage = new HomePage(page()).open();
        WebInputsPage webInputsTest = homePage.goToWebInputsPage();

        webInputsTest.fillInputsWithValues("1", "t", "p", "2020-01-01");
        webInputsTest.clearInputs();

        assertAll("Verify inputs are cleared",
                () -> assertEquals("", webInputsTest.numberInput.inputValue()),
                () -> assertEquals("", webInputsTest.textInput.inputValue()),
                () -> assertEquals("", webInputsTest.passwordInput.inputValue()),
                () -> assertEquals("", webInputsTest.dateInput.inputValue()));
    }


}
