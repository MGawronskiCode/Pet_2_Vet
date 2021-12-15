package pl.petlovers.Pet2Vet.note.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.petlovers.Pet2Vet.note.Note;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class NoteDTO {

    private String title;
    private String content;

    public Note toNote() {
        return Note.builder()
                .title(title)
                .content(content)
                .build();
    }

    public static NoteDTO of(Note note){
        return NoteDTO.builder()
                .title(note.getTitle())
                .content(note.getContent())
                .build();
    }
}
