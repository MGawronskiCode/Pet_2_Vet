package pl.petlovers.Pet2Vet.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private static final String ADMIN = "ADMIN";
  private static final String OWNER = "OWNER";

  @Bean
  @Override
  public UserDetailsService userDetailsService() {
//    fixme change deprecated method storing plaintext data in memory into protected data stored in db (use bcrypt)
    UserDetails adminDetails = User.withDefaultPasswordEncoder()
        .username("admin")
        .password("123")
        .roles(ADMIN)
        .build();
    UserDetails ownerDetails = User.withDefaultPasswordEncoder()
        .username("owner")
        .password("123")
        .roles(OWNER)
        .build();

    return new InMemoryUserDetailsManager(adminDetails, ownerDetails);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.httpBasic().and().authorizeRequests()
        .antMatchers(HttpMethod.GET).permitAll()
        .antMatchers(HttpMethod.POST).hasAnyRole(ADMIN, OWNER)
        .antMatchers(HttpMethod.PUT).hasAnyRole(ADMIN)
        .antMatchers(HttpMethod.DELETE).hasRole(ADMIN)
        .anyRequest().permitAll()
        .and()
        .formLogin().permitAll()
        .and()
        .logout().permitAll()
        .and()
        .csrf().disable();
  }
}
