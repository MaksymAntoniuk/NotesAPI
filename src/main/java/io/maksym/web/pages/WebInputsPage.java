package io.maksym.web.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static io.maksym.web.config.ApiEndpoints.BASE_URL_UI;

public class WebInputsPage extends BasePage {
    public Locator displayInputsBtn = page.locator("#btn-display-inputs");
    public Locator clearInputsBtn = page.locator("#btn-clear-inputs");

    public Locator numberInput = page.locator("#input-number");
    public Locator textInput = page.locator("#input-text");
    public Locator passwordInput = page.locator("#input-password");
    public Locator dateInput = page.locator("#input-date");

    public Locator numberOutput = page.locator("#output-number");
    public Locator textOutput = page.locator("#output-text");
    public Locator passwordOutput = page.locator("#output-password");
    public Locator dateOutput = page.locator("#output-date");

    public Locator numberLabel = page.locator("label[for='input-number']");
    public Locator textLabel = page.locator("label[for='input-text']");
    public Locator passwordLabel = page.locator("label[for='input-password']");
    public Locator dateLabel = page.locator("label[for='input-date']");

    public Locator numberOutputLabel = page.locator("label[for='output-number']");
    public Locator textOutputLabel = page.locator("label[for='output-text']");
    public Locator passwordOutputLabel = page.locator("label[for='output-password']");
    public Locator dateOutputLabel = page.locator("label[for='output-date']");

    public Locator title = page.getByRole(AriaRole.PARAGRAPH);

    public WebInputsPage(Page page) {
        super(page);
    }

    public WebInputsPage webInputsPageShouldBeOpened() {
        assertThat(page).hasURL(BASE_URL_UI + "/inputs");
        return this;
    }

    public void displayInputs() {
        displayInputsBtn.click();
    }

    public void clearInputs() {
        clearInputsBtn.click();
    }

    public void inputFieldsVisible() {
        assertThat(numberInput).isVisible();
        assertThat(textInput).isVisible();
        assertThat(passwordInput).isVisible();
        assertThat(dateInput).isVisible();
        page.waitForTimeout(2000);
    }

    public void outputFieldsVisible() {
        assertThat(numberOutput).isVisible();
        assertThat(textOutput).isVisible();
        assertThat(passwordOutput).isVisible();
        assertThat(dateOutput).isVisible();
    }

    public void fillInputsWithValues(String number, String text, String password, String date) {
        inputFieldsVisible();

        numberInput.fill(number);
        textInput.fill(text);
        passwordInput.fill(password);
        dateInput.fill(date);
        displayInputs();
        page.waitForTimeout(2000);
    }

    public String getNumberLabelText() {
        return numberLabel.innerText();
    }

    public String getTextLabelText() {
        return textLabel.innerText();
    }

    public String getPasswordLabelText() {
        return passwordLabel.innerText();
    }

    public String getDateLabelText() {
        return dateLabel.innerText();
    }

    public String getNumberOutputText() {
        assertThat(numberOutput).isVisible();
        return numberOutput.innerText();
    }

    public String getTextOutputText() {
        return textOutput.innerText();
    }

    public String getPasswordOutputText() {
        return passwordOutput.innerText();
    }

    public String getDateOutputText() {
        return dateOutput.innerText();
    }

    public String getNumberLabelTextOutput() {
        return numberOutputLabel.innerText();
    }

    public String getTextLabelTextOutput() {
        return textOutputLabel.innerText();
    }

    public String getPasswordLabelTextOutput() {
        return passwordOutputLabel.innerText();
    }

    public String getDateLabelTextOutput() {
        return dateOutputLabel.innerText();
    }


    @Override
    protected String path() {
        return "/inputs";
    }
}
