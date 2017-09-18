package com.kicinger.spring.security.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * This security configuration will by default: - allow the user to login and logout - CSRF attack
 * prevention - Session Fixation protection - Security Header integration: - HTTP
 * Strict-Transport-Security for secure requests - X-Content-Type-Options integration - Cache
 * Control - X-XSS-Protection integration - X-Frame-Options - Integrate with the following Servlet
 * API methods (HttpServletRequest.*): - getRemoteUser() - getUserPrincipal() - isUserInRole(String)
 * - login(String, String) - logout()
 */
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/css/**", "/index")
                .permitAll()
                .antMatchers("/user/**")
                .hasRole("USER")
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login-error")
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
    }
}
