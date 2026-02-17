package io.maksym.web.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static io.maksym.web.config.ApiEndpoints.BASE_URL_UI;

public class RadioButtonsPage extends BasePage{

    public RadioButtonsPage(Page page) {
        super(page);
    }

    public RadioButtonsPage radioButtonsPageShouldBeOpened(){
        assertThat(page).hasURL(BASE_URL_UI + "/radio-buttons");
        return this;
    }

    private final Locator blueRadio = page.getByLabel("Blue");
    private final Locator redRadio = page.getByLabel("Red");
    private final Locator yellowRadio = page.getByLabel("Yellow");
    private final Locator blackRadio = page.getByLabel("Black");
    private final Locator greenRadio = page.locator("#green");

    public void selectBlue() {
        blueRadio.check();
    }

    public void selectRed() {
        redRadio.check();
    }

    public void selectYellow() {
        yellowRadio.check();
    }

    public void selectBlack() {
        blackRadio.check();
    }
    public void selectGreen(){
        greenRadio.check();
    }

    public void checkBlueIsSelected() {
        assertThat(blueRadio).isChecked();
    }

    public void checkRedIsSelected() {
        assertThat(redRadio).isChecked();
    }

    public void checkYellowIsSelected() {
        assertThat(yellowRadio).isChecked();
    }

    public void checkBlackIsSelected() {
        assertThat(blackRadio).isChecked();
    }

    public void checkGreenIsDisabled() {
        assertThat(greenRadio).isDisabled();
    }


    @Override
    protected String path() {
        return "/radio-buttons";
    }
}
