package com.example.demo.service;

//package com.example.demo.service;

import com.example.demo.entity.AppUser;
import com.example.demo.repository.UserRepository;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService
        implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(
            UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        AppUser appUser = userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                    new UsernameNotFoundException(
                        "User not found: " + username
                    )
                );

        String[] authorities = appUser.getRoles()
                .stream()
                .map(role -> role.getName())
                .toArray(String[]::new);

        return User.withUsername(appUser.getUsername())
                .password(appUser.getPassword())
                .authorities(authorities)
                .disabled(!appUser.isEnabled())
                .build();
    }
}