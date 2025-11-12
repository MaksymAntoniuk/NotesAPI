package io.maksym.web.util;

import com.github.javafaker.Faker;
import io.maksym.web.enums.CategoryNote;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataGenerators {
    String fakeName;
    public String generateRandomName(int minLength, int maxLength) {
        do{
            if(minLength > 30){
                fakeName = new Faker().name().lastName() + new Faker().name().name() + new Faker().name().name();
            }
            else {
                fakeName = new Faker().name().firstName();
            }
        } while((fakeName.length() < minLength || fakeName.length() > maxLength));
        return fakeName;
    }

    public String generateRandomEmail(boolean isValid) {
       if (!isValid){
           return "invalidEmail";
       }
       String email = new Faker().artist().name().replaceAll("\\s+", "_") +
               System.currentTimeMillis() + "@gmail.com";
           return email.toLowerCase();
    }

    public String generateRandomPassword(int minLength, int maxLength) {
        return new Faker().internet().password(minLength, maxLength);
    }

    public String generateRandomPhone() {
        return new Faker().phoneNumber().subscriberNumber(10);
    }
    public String generateRandomCompany() {
        String compannyName = new Faker().company().name();
        if (compannyName.length() > 30){
            return compannyName.substring(0, 30);
        }
        return compannyName;
    }

    public String generateRandomTitle() {
        String title = new Faker().book().title();
        if (title.length() > 100){
            return title.substring(0, 100);
        }
        return title;
    }

    public String generateRandomDescription() {
        String description = new Faker().lorem().paragraph();
        if (description.length() > 1000){
            return description.substring(0, 1000);
        }
        return description;
    }

    public String generateRandomCategoryNote(){
        List<String> categories = new ArrayList<String>(List.of(
                CategoryNote.HOME.getCategory(),
                CategoryNote.PERSONAL.getCategory(),
                CategoryNote.WORK.getCategory()));
        return categories.get(new Faker().number().numberBetween(0, categories.size() - 1));
    }
}
