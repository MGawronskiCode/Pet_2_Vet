package pl.petlovers.Pet2Vet.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private UserDetailsServiceImpl userDetailsService;

  @Autowired
  public WebSecurityConfig(UserDetailsServiceImpl userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.httpBasic()
        .and().authorizeRequests()
//        .antMatchers(HttpMethod.GET, "/users").permitAll()
//        .antMatchers(HttpMethod.POST, "/users").authenticated()
//        .antMatchers(HttpMethod.PUT, "/users").authenticated()
//        .antMatchers(HttpMethod.DELETE, "/users").hasRole(ADMIN)
//        .antMatchers(HttpMethod.GET, "/users/*").permitAll()
//        .antMatchers(HttpMethod.POST, "/users/*").authenticated()
//        .antMatchers(HttpMethod.PUT, "/users/*").authenticated()
//        .antMatchers(HttpMethod.DELETE, "/users/*").hasRole(ADMIN)
        .antMatchers("/pets").hasRole(Roles.OWNER.name())
        .antMatchers("/pets/*").hasRole(Roles.OWNER.name())
        .antMatchers("/vaccines").hasAnyRole("ADMIN", Roles.OWNER.name())
        .antMatchers("/vaccines/*").hasAnyRole(Roles.ADMIN.name(), Roles.OWNER.name())
//        .antMatchers("/species").authenticated()
//        .antMatchers("/species/*").authenticated()
//        .antMatchers(HttpMethod.GET).permitAll()
//        .antMatchers(HttpMethod.POST).hasAnyRole(ADMIN, OWNER)
//        .antMatchers(HttpMethod.PUT).hasAnyRole(ADMIN)
//        .antMatchers(HttpMethod.DELETE).hasRole(ADMIN)
        .anyRequest().permitAll()
//        .anyRequest().denyAll()
        .and().formLogin().permitAll()
        .and().logout().permitAll()
        .and().csrf().disable();
  }

}
