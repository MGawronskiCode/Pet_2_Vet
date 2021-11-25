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

  private String name;

  public static PetSpecieDTO of(PetSpecie specie) {
    return PetSpecieDTO.builder()
        .name(specie.getName())
        .build();
  }

  public PetSpecie toPetSpecie() {
    return PetSpecie.builder()
        .name(name)
        .build();
  }

}
