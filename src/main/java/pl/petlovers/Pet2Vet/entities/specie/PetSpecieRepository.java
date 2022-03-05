package pl.petlovers.Pet2Vet.entities.specie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetSpecieRepository extends JpaRepository<PetSpecie, Long> {
}
