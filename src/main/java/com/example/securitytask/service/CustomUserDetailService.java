package com.example.securitytask.service;

import com.example.securitytask.model.UserEntity;
import com.example.securitytask.repository.UserEntityRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class CustomUserDetailService implements UserDetailsService {
    private UserEntityRepository userEntityRepository;
    private LoginAttemptService loginAttemptService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userEntityRepository.findByUserName(username);
        if (userEntity == null) {
            log.error("No user found for name {}", username);
            throw new UsernameNotFoundException("UserEntity not found");
        }
        if(loginAttemptService.isBlocked(username)) {
            throw new LockedException("User is blocked");
        }
        return User.withUsername(userEntity.getUserName())
                    .password(userEntity.getUserPassword())
                    .authorities(userEntity.getUserAuthorities())
                    .build();
    }
}
