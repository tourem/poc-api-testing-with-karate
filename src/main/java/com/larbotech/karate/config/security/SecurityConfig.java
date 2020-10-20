package com.larbotech.karate.config.security;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private CustomBasicAuthenticationEntryPoint authEntryPoint;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
        .withUser("sensei").password(passwordEncoder().encode("suchWisdom"))
        .authorities("ROLE_USER");
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    if (http != null) {
      http.authorizeRequests()
          .antMatchers("/secret").authenticated()
          .anyRequest().permitAll()
          .and()
          .httpBasic()
          .authenticationEntryPoint(authEntryPoint);
      http.csrf().disable();
    }
  }

}

@Component
class CustomBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException {
    response.addHeader("WWW-Authenticate", "Basic realm=$realmName");
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    PrintWriter writer = response.getWriter();
    writer.println("HTTP Status 401 - " + authException.getMessage());
  }

  @Override
  public void afterPropertiesSet() {
    this.setRealmName("Karate");
    super.afterPropertiesSet();
  }

}
