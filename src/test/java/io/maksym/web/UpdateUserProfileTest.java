package io.maksym.web;

import io.maksym.web.base.BaseTest;
import io.maksym.web.dto.Profile.ProfileResponse;
import io.maksym.web.util.DataGenerators;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

import static io.maksym.web.util.Constants.REPEAT_COUNT;

public class UpdateUserProfileTest extends BaseTest {

    @DisplayName("Verify that user is able to update [Name]")
    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    public void updateUserProfileNameTest(){
        String name = new DataGenerators().generateRandomName(4, 30);
        ProfileResponse response = updateUserProfileName(token, name);

    }
    @DisplayName("Verify that user is able to update [Email]")
    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    public void updateUserProfileEmailTest(){
        String email = new DataGenerators().generateRandomEmail(true);
        ProfileResponse response = updateUserProfileName(token, email);
    }
    @DisplayName("Verify that user is able to update [Phone]")
    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    public void updateUserProfilePhoneTest(){
        String phone = new DataGenerators().generateRandomEmail(true);
        ProfileResponse response = updateUserProfilePhone(token, phone);
    }

}
