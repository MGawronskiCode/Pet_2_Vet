package pl.petlovers.Pet2Vet.appUser.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.petlovers.Pet2Vet.Sex;
import pl.petlovers.Pet2Vet.appUser.AppUser;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUserDTO {

    private Long id;
    private String name;
    private Sex sex;

    public static AppUserDTO of(AppUser appUser) {
        return AppUserDTO.builder()
                .id(appUser.getId())
                .name(appUser.getName())
                .sex(appUser.getSex())
                .build();
    }

    public AppUser toAppUser() {//todo unused?
        return AppUser.builder()
                .id(id)
                .name(name)
                .sex(sex)
                .build();
    }
}
