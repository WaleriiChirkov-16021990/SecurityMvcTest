package com.example.demo.config;

import com.example.demo.security.AuthProviderImpl;
import com.example.demo.services.PersonDetailsService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Getter
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //    private final AuthProviderImpl authProvider; custom authentication
    private final PersonDetailsService personDetailsService;

    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    // указываем на свою аутентификацию (custom page)
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        конфигурируем сам спринг Security
//        конфигурируем авторизацию
        // отключаем защиту от межсайтовой подделки запросов
        // отправляем на страницу регистрации всех
        // указываем доступные страницы до момента аутентификации
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/auth/login", "/error", "/auth/registration")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin().loginPage("/auth/login")
                .loginProcessingUrl("/process_login")
                .defaultSuccessUrl("/hello/say_hello", true)
                .failureUrl("/auth/login?error")
                .and()
                .logout().logoutUrl("/logout")
                .logoutSuccessUrl("/auth/login");
    }

    // Настраиваем аутентификацию
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(personDetailsService)
                .passwordEncoder(getPasswordEncoder());
    }

    @Bean // passwordEncode
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
