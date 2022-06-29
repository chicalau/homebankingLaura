package com.mindhub.homebanking.Configurations;


import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity
@Configuration
public class WebAuthorization extends WebSecurityConfigurerAdapter {

    @Override

    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/payments").permitAll()
                .antMatchers("/web/index.html").permitAll()
                .antMatchers("/web/style/**").permitAll()
                .antMatchers("/web/scripts/**").permitAll()
                .antMatchers("/rest/**").hasAuthority("ADMIN")
                .antMatchers("/manager.html").hasAuthority("ADMIN")
                .antMatchers("/manager.js").hasAuthority("ADMIN")
                .antMatchers("/style.css").hasAuthority("ADMIN")
                .antMatchers("/h2-console/").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "api/clients/current/accounts").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.POST, "api/clients/current/cards").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.POST, "api/transactions").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.POST, "api/loans").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.POST, "api/loans/admin").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PATCH, "api/cards").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.PATCH, "api/accounts").hasAuthority("CLIENT")
                .antMatchers("/api/clients/current/**").hasAnyAuthority("CLIENT","ADMIN")
                .antMatchers("/api/accounts/**").hasAnyAuthority("CLIENT","ADMIN")
                .antMatchers("/api/loans").hasAnyAuthority("CLIENT","ADMIN")
                .antMatchers("/api/transactions").hasAnyAuthority("CLIENT","ADMIN")
                .antMatchers("/web/admin-loan.html").hasAuthority("ADMIN")
                .antMatchers("/web/**").hasAnyAuthority("CLIENT","ADMIN");

        http.formLogin()

                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/api/login");

        http.logout().logoutUrl("/api/logout");
        http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());
        // turn off checking for CSRF tokens
        http.csrf().disable();
        //disabling frameOptions so h2-console can be accessed
        http.headers().frameOptions().disable();
        // if user is not authenticated, just send an authentication failure response
        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
        // if login is successful, just clear the flags asking for authentication
        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));
        // if login fails, just send an authentication failure response
        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
        // if logout is successful, just send a success response
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }


}

