package com.example.demo.security;

import com.example.demo.models.Person;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;


@Getter
public class PersonDetails implements UserDetails {

    //Что бы получать данные аутентифицированного пользователя
    private final Person person;

//    @Autowired
    public PersonDetails(Person person) {
        this.person = person;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(person.getRole()));
    }

    @Override
    public String getPassword() {
        return this.person.getPassword();
    }

    @Override
    public String getUsername() {
        return this.person.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; //аккаунт не просрочен
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // аккаунт не заблокирован
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // пароль не просрочен
    }

    @Override
    public boolean isEnabled() {
        return true; // аккаунт включен (работает)
    }



}
