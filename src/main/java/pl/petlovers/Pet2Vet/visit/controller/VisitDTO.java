package pl.petlovers.Pet2Vet.visit.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.petlovers.Pet2Vet.file.File;
import pl.petlovers.Pet2Vet.file.controller.FileDTO;
import pl.petlovers.Pet2Vet.visit.Visit;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisitDTO {

  private List<File> files;
  //TODO FileDTO instead of List<File>
  private String purpose;
  private LocalDateTime dateTime;
  private String place;
  private String description;
  private String recommendation;

  public static VisitDTO of(Visit visit) {
    return VisitDTO.builder()
      .files(visit.getFiles())
      .purpose(visit.getPurpose())
      .dateTime(visit.getDateTime())
      .place(visit.getPlace())
      .description(visit.getDescription())
      .recommendation(visit.getRecommendation())
      .build();
  }

  public Visit toVisit() {
    return Visit.builder()
      .files(files)
      .purpose(purpose)
      .dateTime(dateTime)
      .place(place)
      .description(description)
      .recommendation(recommendation)
      .build();
  }
}
