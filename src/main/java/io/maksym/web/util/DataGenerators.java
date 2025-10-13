package io.maksym.web.Helpers;

import com.github.javafaker.Faker;

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
            System.out.println(fakeName);
        } while((fakeName.length() < minLength || fakeName.length() > maxLength));
        return fakeName;
    }

    public String generateRandomEmail(boolean isValid) {
       if (!isValid){
           return "invalidEmail";
       }
           return new Faker().artist().name().replaceAll("\\s+", "_") + System.currentTimeMillis() + "@gmail.com";
    }

    public String generateRandomPassword(int minLength, int maxLength) {
        return new Faker().internet().password(minLength, maxLength);
    }
//    public String generateRandomName(int minLength, int maxLength) {
//        return new Faker().name().firstName().replaceAll("\\s+", "_");
//    }

}
