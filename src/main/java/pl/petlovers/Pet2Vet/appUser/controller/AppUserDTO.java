package pl.petlovers.Pet2Vet.appUser.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.petlovers.Pet2Vet.Sex;
import pl.petlovers.Pet2Vet.appUser.AppUser;
import pl.petlovers.Pet2Vet.security.users.Roles;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUserDTO {

  private Long id;
  private String name;
  private Sex sex;
  private String login;
  private Roles role;

  public static AppUserDTO of(AppUser appUser) {
    return AppUserDTO.builder()
        .id(appUser.getId())
        .name(appUser.getName())
        .sex(appUser.getSex())
        .login(appUser.getLogin())
        .role(appUser.getRole())
        .build();
  }

  public AppUser toAppUser(String password) {
    return AppUser.builder()
        .id(id)
        .name(name)
        .sex(sex)
        .login(login)
        .role(role)
        .password(new BCryptPasswordEncoder().encode(password))
        .build();
  }
}
