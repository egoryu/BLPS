package com.example.lab1stranamam.service;

import com.example.lab1stranamam.repositories.UsersRepository;
import com.example.lab1stranamam.security.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new User(usersRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Not found" + username)));
    }
}
