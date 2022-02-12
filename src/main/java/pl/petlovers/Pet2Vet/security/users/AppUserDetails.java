package pl.petlovers.Pet2Vet.security.users;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.petlovers.Pet2Vet.appUser.AppUser;

import java.util.Collection;
import java.util.Set;

public class AppUserDetails implements UserDetails {

  @Getter
  private final AppUser appUser;

  public AppUserDetails(AppUser appUser) {
    this.appUser = appUser;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {

    return getUserRoleAsCollection().stream()
        .map(SimpleGrantedAuthority::new)
        .toList();
  }

  @Override
  public String getPassword() {
    return this.appUser.getPassword();
  }

  @Override
  public String getUsername() {
    return this.appUser.getLogin();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  private Set<String> getUserRoleAsCollection() {
    return Set.of(appUser.getRole().name());
  }

  public boolean isAdmin() {
    return this.appUser.getRole() == Roles.ROLE_ADMIN;
  }

  public long getId() {
    return this.appUser.getId();
  }
}
