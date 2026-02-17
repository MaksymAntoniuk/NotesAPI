package io.maksym.web.ui.practice_tests;

import io.maksym.web.ui.pages.BaseTest;
import io.maksym.web.pages.HomePage;
import io.maksym.web.pages.RadioButtonsPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;

public class RadioButtonsTest extends BaseTest {

    @Test
    @DisplayName("Verify that radio buttons page is visible and functional")
    public void RadioButtonsTest(){
        HomePage homePage = new HomePage(page()).open();
        RadioButtonsPage radioButtonsPage = homePage.goToRadioButtonsPage();

        assertAll("",
                () -> radioButtonsPage.radioButtonsPageShouldBeOpened(),

                () ->radioButtonsPage.checkBlueIsSelected(),

                () ->radioButtonsPage.selectRed(),
                () ->radioButtonsPage.checkRedIsSelected(),

                () ->radioButtonsPage.selectYellow(),
                () ->radioButtonsPage.checkYellowIsSelected(),

                () ->radioButtonsPage.selectBlack(),
                () ->radioButtonsPage.checkBlackIsSelected(),

                () ->radioButtonsPage.checkGreenIsDisabled()
        );


    }
}
