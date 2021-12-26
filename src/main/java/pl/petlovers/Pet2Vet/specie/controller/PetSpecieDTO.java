package pl.petlovers.Pet2Vet.specie.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.petlovers.Pet2Vet.specie.PetSpecie;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetSpecieDTO {

  private Long id;
  private String name;

  public static PetSpecieDTO of(PetSpecie specie) {
    return PetSpecieDTO.builder()
            .id(specie.getId())
        .name(specie.getName())
        .build();
  }

  public PetSpecie toPetSpecie() {
    return PetSpecie.builder()
            .id(id)
        .name(name)
        .build();
  }

}
