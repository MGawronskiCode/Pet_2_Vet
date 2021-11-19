package pl.petlovers.Pet2Vet.appUser.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.petlovers.Pet2Vet.appUser.AppUser;
import pl.petlovers.Pet2Vet.note.Note;
import pl.petlovers.Pet2Vet.pet.Pet;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUserDTO {

    private Long id;
    private String name;
    private String sex;
    private List<Pet> pets;
    private List<Note> notes;

    public static AppUserDTO of(AppUser appUser) {
        return AppUserDTO.builder()
                .id(appUser.getId())
                .name(appUser.getName())
                .sex(appUser.getSex())
                .pets(appUser.getPets())
                .notes(appUser.getNotes())
                .build();
    }

    public AppUser toAppUser() {
        return AppUser.builder()
                .id(id)
                .name(name)
                .sex(sex)
                .pets(pets)
                .notes(notes)
                .build();
    }
}
