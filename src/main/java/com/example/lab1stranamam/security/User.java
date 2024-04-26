package com.example.lab1stranamam.security;

import com.example.lab1stranamam.entity.UsersEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

@Getter
@Setter
@AllArgsConstructor
public class User implements UserDetails {
    @Serial
    private static final long serialVersionUID = 1L;

    private int id;

    private String username;

    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public User(UsersEntity usersEntity) {
        this.id = usersEntity.getId();
        this.username = usersEntity.getUsername();
        this.password = usersEntity.getPassword();
        this.authorities = Stream.of("ROLE_" + usersEntity.getRole()).map(SimpleGrantedAuthority::new).toList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
