package pl.petlovers.Pet2Vet.file.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import pl.petlovers.Pet2Vet.file.VisitFileService;
import pl.petlovers.Pet2Vet.visit.VisitService;

import java.util.List;

@RestController
public class VisitFileController {

  private final VisitFileService visitFileService;
  private final VisitService visitService;

  @Autowired
  public VisitFileController(VisitFileService visitFileService, VisitService visitService) {
    this.visitFileService = visitFileService;
    this.visitService = visitService;
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/visits/{visitId}/files")
  public List<FileDTO> getAll(@PathVariable long visitId) {
    return visitFileService.getAll(visitId)
      .stream()
      .map(FileDTO::of)
      .toList();
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/visits/{visitId}/files/{fileId}")
  public FileDTO get(@PathVariable long visitId, @PathVariable long fileId) {
    if(visitService.get(visitId).containsFile(fileId)) {
      return FileDTO.of(visitFileService.get(fileId));
    }
    throw new IllegalArgumentException("You naughty naughty user, no hacking here!");
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/visits/{visitId}/files")
  public FileDTO create(@PathVariable long visitId, @RequestBody FileDTO fileDTO) {
    return FileDTO.of(visitFileService.create(visitId, fileDTO.toFile()));
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PutMapping("/visits/{visitId}/files/{fileId}")
  public FileDTO update(@PathVariable long visitId, @PathVariable long fileId, @RequestBody FileDTO fileDTO) {
    if(visitService.get(visitId).containsFile(fileId)) {
      return FileDTO.of(visitFileService.update(visitId, fileId, fileDTO.toFile()));
    }
    throw new IllegalArgumentException("You naughty naughty user, no hacking here!");
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/visits/{visitId}/files/{fileId}")
  public void delete(@PathVariable long visitId, @PathVariable long fileId) {
    if(visitService.get(visitId).containsFile(fileId)) {
      visitFileService.delete(fileId);
    }
    throw new IllegalArgumentException("You naughty naughty user, no hacking here!");
  }





}
