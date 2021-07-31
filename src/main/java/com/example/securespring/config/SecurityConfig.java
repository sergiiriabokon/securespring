package com.example.securespring.config;

import com.example.securespring.model.Permission;
import com.example.securespring.model.Role;

import java.util.Base64;
import java.util.Random;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Value("${admin}")
    private String _adminPassword;

    @Value("${user}")
    private String _userPassword;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
        http
        .csrf().disable()
         .authorizeRequests()
         .antMatchers("/").permitAll()

         //.antMatchers(HttpMethod.GET, "/api/**").hasAnyRole(Role.ADMIN.name(), Role.USER.name())
         //.antMatchers(HttpMethod.GET, "/api/**").hasAuthority(Permission.DEVELOPERS_READ.getPermission())
         
         //.antMatchers(HttpMethod.POST, "/api/**").hasRole(Role.ADMIN.name())
         //.antMatchers(HttpMethod.POST, "/api/**").hasAuthority(Permission.DEVELOPERS_WRITE.getPermission())
         
         //.antMatchers(HttpMethod.DELETE, "/api/**").hasRole(Role.ADMIN.name())
         //.antMatchers(HttpMethod.DELETE, "/api/**").hasAnyAuthority(Permission.DEVELOPERS_WRITE.getPermission())
         
         .anyRequest()
         .authenticated()
         .and()
         .httpBasic();
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        printEncodedAuthentication();
        return new InMemoryUserDetailsManager(
            User.builder()
            .username("admin")
            .password(passwordEncoder().encode(_adminPassword))
            //.roles(Role.ADMIN.name())
            .authorities(Role.ADMIN.getAuthorities())
            .build(),

            User.builder()
            .username("user")
            .password(passwordEncoder().encode(_userPassword))
            //.roles(Role.USER.name())
            .authorities(Role.USER.getAuthorities())
            .build()
        );
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    private void printEncodedAuthentication() {
        String decoded = "admin:" + this._adminPassword;
        System.out.println( "\n" + Base64.getEncoder().encodeToString(decoded.getBytes()) + "\n");
    }

}
