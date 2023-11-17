package com.example.securitytask.listener;

import com.example.securitytask.repository.UserEntityRepository;
import com.example.securitytask.service.LoginAttemptService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
    private UserEntityRepository repository;
    private LoginAttemptService loginAttemptService;

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        Object principal = event.getAuthentication().getPrincipal();
        if(principal instanceof String) {
            String userName = (String) principal;
            if(repository.findByUserName(userName) == null) {
                loginAttemptService.loginFailed(userName);
            }
        }

    }
}
