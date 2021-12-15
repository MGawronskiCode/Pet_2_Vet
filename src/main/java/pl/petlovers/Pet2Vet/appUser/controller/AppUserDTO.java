package pl.petlovers.Pet2Vet.appUser.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.petlovers.Pet2Vet.appUser.AppUser;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUserDTO {

    private Long id;
    private String name;
    private String sex;

    public static AppUserDTO of(AppUser appUser) {
        return AppUserDTO.builder()
                .id(appUser.getId())
                .name(appUser.getName())
                .sex(appUser.getSex())
                .build();
    }

    public AppUser toAppUser() {
        return AppUser.builder()
                .id(id)
                .name(name)
                .sex(sex)
                .build();
    }
}