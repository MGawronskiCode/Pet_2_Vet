package pl.petlovers.Pet2Vet.note;

import lombok.*;
import pl.petlovers.Pet2Vet.appUser.AppUser;
import pl.petlovers.Pet2Vet.pet.Pet;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser appUser;

    @ManyToOne(cascade = CascadeType.ALL)
    private Pet pet;

    private String title;
    private String content;
    private LocalDateTime created;
    private LocalDateTime modified; //OffsetDateTime / ZonedDateTime

    protected void modify(Note note) {
        setTitle(note.getTitle());
        setContent(note.getContent());
        setModified(LocalDateTime.now());
    }
}
