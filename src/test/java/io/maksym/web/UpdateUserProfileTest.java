package io.maksym.web;

import io.maksym.web.base.BaseTest;
import io.maksym.web.dto.Profile.ProfileResponse;
import io.maksym.web.enums.ErrorMessage;
import io.maksym.web.enums.StatusCode;
import io.maksym.web.util.DataGenerators;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

import static io.maksym.web.enums.ErrorMessage.UNAUTHORIZED_MESSAGE;
import static io.maksym.web.enums.StatusCode.SUCCESSFUL_STATUS;
import static io.maksym.web.enums.StatusCode.UNAUTHORIZED_STATUS;
import static io.maksym.web.util.Constants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import static io.maksym.web.enums.ErrorMessage.PROFILE_SUCCESSFUL;


public class UpdateUserProfileTest extends BaseTest {

    @DisplayName("Verify that user is able to update [Name]")
    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    public void updateUserProfileNameTest(){
        String name = new DataGenerators().generateRandomName(4, 30);
        ProfileResponse response = updateUserProfileName(token, name);

        assertAll("Verify that user is able to update [Name]",
                () -> Assertions.assertEquals(SUCCESSFUL_STATUS.getStatus(), response.getStatus(), "Incorrect status code"),
                () -> Assertions.assertEquals(EXPECTED_SUCCESS_TRUE, response.isSuccess(), "Incorrect success status"),
                () -> Assertions.assertEquals(email, response.getData().getEmail(), "Incorrect [Email]]"),
                () -> Assertions.assertEquals(name, response.getData().getName(), "Incorrect [Name]"),
                () -> Assertions.assertNull(response.getData().getPhone(), "Incorrect [Phone]"),
                () -> Assertions.assertNull(response.getData().getCompany(), "Incorrect [Company]")
        );
    }

    @DisplayName("Verify that user is able to update [Phone]")
    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    public void updateUserProfilePhoneTest(){
        String phone = new DataGenerators().generateRandomPhone();
        String name = new DataGenerators().generateRandomName(4, 30);

        ProfileResponse response = updateUserProfilePhone(token, name, phone);

        assertAll("Verify that user is able to update [Phone]",
                () -> Assertions.assertEquals(SUCCESSFUL_STATUS.getStatus(), response.getStatus(), "Incorrect status code"),
                () -> Assertions.assertEquals(EXPECTED_SUCCESS_TRUE, response.isSuccess(), "Incorrect success status"),
                () -> Assertions.assertEquals(email, response.getData().getEmail(), "Incorrect [Email]]"),
                () -> Assertions.assertEquals(name, response.getData().getName(), "Incorrect [Name]"),
                () -> Assertions.assertEquals(phone, response.getData().getPhone(), "Incorrect [Phone]"),
                () -> Assertions.assertNull(response.getData().getCompany(), "Incorrect [Company]")
        );
    }

    @DisplayName("Verify that user is able to update [Company]")
    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    public void updateUserProfileCompanyTest(){
        String company = new DataGenerators().generateRandomCompany();
        ProfileResponse response = updateUserProfileCompany(token, name, company);

        assertAll("Veerify that user is able to update [Company]",
                () -> Assertions.assertEquals(SUCCESSFUL_STATUS.getStatus(), response.getStatus(), "Incorrect status code"),
                () -> Assertions.assertEquals(EXPECTED_SUCCESS_TRUE, response.isSuccess(), "Incorrect success status"),
                () -> Assertions.assertEquals(email, response.getData().getEmail(), "Incorrect [Email]]"),
                () -> Assertions.assertEquals(name, response.getData().getName(), "Incorrect [Name]"),
                () -> Assertions.assertNull(response.getData().getPhone(), "Incorrect [Phone]"),
                () -> Assertions.assertEquals(company, response.getData().getCompany(), "Incorrect [Company]")
        );
    }

    @DisplayName("Verify that user is able to update [Name], [Phone], [Company] in the same time")
    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    public void updateUserProfileTest(){
        String company = new DataGenerators().generateRandomCompany();
        String phone = new DataGenerators().generateRandomPhone();
        String name = new DataGenerators().generateRandomName(4, 30);

        ProfileResponse response = updateUserProfile(token, name, phone, company);
        assertAll("Verify that user is able to update [Name], [Phone], [Company] in the same time",
                () -> Assertions.assertEquals(SUCCESSFUL_STATUS.getStatus(), response.getStatus(), "Incorrect status code"),
                () -> Assertions.assertEquals(EXPECTED_SUCCESS_TRUE, response.isSuccess(), "Incorrect success status"),
                () -> Assertions.assertEquals(email, response.getData().getEmail(), "Incorrect [Email]]"),
                () -> Assertions.assertEquals(name, response.getData().getName(), "Incorrect [Name]"),
                () -> Assertions.assertEquals(phone, response.getData().getPhone(), "Incorrect [Phone]"),
                () -> Assertions.assertEquals(company, response.getData().getCompany(), "Incorrect [Company]")
        );
    }

    @DisplayName("Verify that user is NOT able to update Profile with invalid Token")
    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    public void updateUserProfileWithInvalidTokenTest(){
        String company = new DataGenerators().generateRandomCompany();
        String phone = new DataGenerators().generateRandomPhone();
        String name = new DataGenerators().generateRandomName(4, 30);

        ProfileResponse response = updateUserProfile("wrongToken", name, phone, company);

        assertAll("Verify that user is NOT able to update Profile with invalid Token",
                () -> Assertions.assertEquals(UNAUTHORIZED_STATUS.getStatus(), response.getStatus(), "Incorrect status code"),
                () -> Assertions.assertEquals(EXPECTED_SUCCESS_FALSE, response.isSuccess(), "Incorrect success status"),
                () -> Assertions.assertEquals(UNAUTHORIZED_MESSAGE.getMessage(), response.getMessage(), "Incorrect message")
        );
    }

    @DisplayName("Verify that user is able to update with NULL value in [Name], [Phone], [Company]")
    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    public void updateUserProfileWithNullTest(){
        String company = new DataGenerators().generateRandomCompany();
        String phone = new DataGenerators().generateRandomPhone();
        String name = new DataGenerators().generateRandomName(4, 30);

        ProfileResponse response = updateUserProfile(token, name, null, null);

        assertAll("Verify that user is able to update with NULL value in [Name], [Phone], [Company]",
                () -> Assertions.assertEquals(StatusCode.BAD_REQUEST_STATUS.getStatus(), response.getStatus(), "Incorrect status code"),
                () -> Assertions.assertEquals(EXPECTED_SUCCESS_FALSE, response.isSuccess(), "Incorrect success status"),
                () -> Assertions.assertEquals(ErrorMessage.INVALID_REQUEST_MESSAGE.getMessage(), response.getMessage(), "Incorrect message")
        );
    }


}
