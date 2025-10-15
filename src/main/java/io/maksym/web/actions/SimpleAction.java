package io.maksym.web.actions;

import io.maksym.web.dto.HealthCheck.HealthCheckResponse;
import io.maksym.web.dto.Login.LoginResponse;
import io.maksym.web.dto.Registration.RegistrationSuccResponse.RegistrationSuccessfulResponse;
import io.maksym.web.dto.User.User;
import io.maksym.web.library.RequestLibrary;

public interface SimpleAction extends RequestLibrary{
    default RegistrationSuccessfulResponse createUser(User user){
        return postCreateUser(user);
    };
    default LoginResponse logIn(String email, String password){
        return postLogIn(email, password);
    };
    default HealthCheckResponse checkHealth(){
        return getCheckHealth();
    };
}
