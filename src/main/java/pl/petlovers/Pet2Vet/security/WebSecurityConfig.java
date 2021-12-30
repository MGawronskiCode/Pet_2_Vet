package pl.petlovers.Pet2Vet.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private static final String ADMIN = "ADMIN";
  private static final String OWNER = "OWNER";

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
        .withUser(new User("admin", passwordEncoder().encode("123"), Collections.emptyList()));
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.httpBasic()
        .and().authorizeRequests()
        .antMatchers(HttpMethod.GET, "/users").permitAll()
        .antMatchers(HttpMethod.POST, "/users").authenticated()
        .antMatchers(HttpMethod.PUT, "/users").authenticated()
        .antMatchers(HttpMethod.DELETE, "/users").hasRole(ADMIN)
        .antMatchers(HttpMethod.GET, "/users/*").permitAll()
        .antMatchers(HttpMethod.POST, "/users/*").authenticated()
        .antMatchers(HttpMethod.PUT, "/users/*").authenticated()
        .antMatchers(HttpMethod.DELETE, "/users/*").hasRole(ADMIN)
        .antMatchers("/pets").hasRole(OWNER)
        .antMatchers("/pets/*").hasRole(OWNER)
        .antMatchers("/vaccines").hasAnyRole(ADMIN, OWNER)
        .antMatchers("/vaccines/*").hasAnyRole(ADMIN, OWNER)
        .antMatchers("/species").authenticated()
        .antMatchers("/species/*").authenticated()
//        .antMatchers(HttpMethod.GET).permitAll()
//        .antMatchers(HttpMethod.POST).hasAnyRole(ADMIN, OWNER)
//        .antMatchers(HttpMethod.PUT).hasAnyRole(ADMIN)
//        .antMatchers(HttpMethod.DELETE).hasRole(ADMIN)
//        .anyRequest().permitAll()
        .anyRequest().denyAll()
        .and().formLogin().permitAll()
        .and().logout().permitAll()
        .and().csrf().disable();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
