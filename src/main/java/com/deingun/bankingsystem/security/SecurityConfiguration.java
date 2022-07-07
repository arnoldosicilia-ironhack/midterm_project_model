package com.deingun.bankingsystem.security;

import com.deingun.bankingsystem.enums.Role;
import com.deingun.bankingsystem.service.impl.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
        http.csrf().disable();
        http.authorizeRequests()

                .mvcMatchers(HttpMethod.GET, "/users").hasRole(Role.ADMIN.toString())
                .mvcMatchers(HttpMethod.GET, "/accountholders/**").hasRole(Role.ADMIN.toString())
                .mvcMatchers(HttpMethod.GET, "/accountholders").hasRole(Role.ADMIN.toString())
                .mvcMatchers(HttpMethod.POST, "/accountholders").hasRole(Role.ADMIN.toString())
                .mvcMatchers(HttpMethod.POST, "/thirdparties").hasRole(Role.ADMIN.toString())
                .mvcMatchers(HttpMethod.POST, "/thirdpartytransactions").hasRole(Role.THIRDPARTY.toString())
                .mvcMatchers(HttpMethod.POST, "/checkingaccounts").hasRole(Role.ADMIN.toString())
                .mvcMatchers(HttpMethod.POST, "/savingaccounts").hasRole(Role.ADMIN.toString())
                .mvcMatchers(HttpMethod.POST, "/creditcardaccounts").hasRole(Role.ADMIN.toString())
                .mvcMatchers(HttpMethod.PATCH, "/accounts/**").hasRole(Role.ADMIN.toString())
                .mvcMatchers(HttpMethod.PATCH, "/status/**").hasRole(Role.ADMIN.toString())
                .anyRequest().permitAll();
        ;
    }
}
