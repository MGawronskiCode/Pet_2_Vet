package pl.petlovers.Pet2Vet;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Bean
  public UserDetailsService userDetailsService() {
//    fixme change deprecated method storing plaintext data in memory into protected data stored in db (use bcrypt)
    UserDetails adminDetails = User.withDefaultPasswordEncoder()
        .username("admin")
        .password("123")
        .roles("ADMIN")
        .build();
    UserDetails ownerDetails = User.withDefaultPasswordEncoder()
        .username("owner")
        .password("123")
        .roles("OWNER")
        .build();

    return new InMemoryUserDetailsManager(adminDetails, ownerDetails);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/users").permitAll()
        .antMatchers("/pets").hasAnyRole("ADMIN", "OWNER")
        .anyRequest().hasAnyRole("ADMIN")
        .and()
        .formLogin().permitAll()
        .and()
        .logout().permitAll();
  }
}
