package com.mishinyura.books.config;

import com.mishinyura.books.security.AuthProviderImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Class SecurityConfig.
 * Implements Security Configuration for Spring App.
 *
 * @author Mishin Yura (mishin.inbox@gmail.com)
 * @since 29.06.2022
 */
@Profile("dev-h2")
@AllArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * Auth Provider.
     */
    private final AuthProviderImpl authProvider;

    /**
     * User Details Service.
     */
    private final UserDetailsService userDetailsService;

    /**
     * @param auth AuthenticationManagerBuilder
     */
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(authProvider);
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(getPasswordEncoder());
    }

    /**
     * @param http Http
     * @throws Exception Exception
     */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        var freeAccessPaths = new String[]{
                "/h2-console/**",
                "/auth/login",
                "/auth/registration",
                "/error",
                "/css/**",
                "/js/**",
                "/favicon.ico",
                "/api/**"
        };

        http
                .headers().frameOptions().disable()
                .and()
                .csrf().disable()
                .authorizeRequests()
//                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers(freeAccessPaths).permitAll()
                .anyRequest().hasAnyRole("USER", "ADMIN")
                .and()
                .formLogin()
                .loginPage("/auth/login")
                .loginProcessingUrl("/process_login")
                .defaultSuccessUrl("/books", true)
                .failureUrl("/auth/login?error")
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/auth/login");
    }

    /**
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
