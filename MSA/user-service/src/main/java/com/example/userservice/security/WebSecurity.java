package com.example.userservice.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration // Spring Context가 구동될 때, Spring Bean으로 등록
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    // 권한관련
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); // csrf 사용 안함
        http.authorizeRequests().antMatchers("/users/**").permitAll();

        http.headers().frameOptions().disable(); // frame error 무시
    }
}
