package pl.petlovers.Pet2Vet.note;

import lombok.*;
import org.apache.commons.lang3.StringUtils;
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
        if (StringUtils.isNoneBlank(note.getTitle())){
            this.setTitle(note.getTitle());
            setModified(LocalDateTime.now());
        }
        if (StringUtils.isNoneBlank(note.getContent())){
            this.setContent(note.getContent());
            setModified(LocalDateTime.now());
        }
    }
}
