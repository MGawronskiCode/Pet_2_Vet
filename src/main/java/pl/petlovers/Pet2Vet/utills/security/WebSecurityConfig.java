package pl.petlovers.Pet2Vet.utills.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.petlovers.Pet2Vet.utills.security.users.UserDetailsServiceImpl;

@EnableGlobalMethodSecurity(securedEnabled = true)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserDetailsServiceImpl userDetailsService;

  @Autowired
  public WebSecurityConfig(UserDetailsServiceImpl userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }


//  todo handle unauthorized exception
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.httpBasic()
        .and().authorizeRequests()
        .and().formLogin().loginPage("http://localhost:3000").permitAll() // loginPage("http://localhost:3000")
        .and().logout().permitAll()
        .and().csrf().disable();
  }

}
