package pl.petlovers.Pet2Vet.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.petlovers.Pet2Vet.exceptions.not_found_exceptions.FileNotFoundException;
import pl.petlovers.Pet2Vet.visit.Visit;
import pl.petlovers.Pet2Vet.visit.VisitRepository;

import java.util.List;

@Service
public class VisitFileService {

  private final FileRepository fileRepository;
  private final VisitRepository visitRepository;

  @Autowired
  public VisitFileService(FileRepository fileRepository, VisitRepository visitRepository) {
    this.fileRepository = fileRepository;
    this.visitRepository = visitRepository;
  }

  public List<File> getAll(long visitId){

    return visitRepository.getById(visitId).getFiles()
        .stream()
        .filter(file -> !file.isDeleted())
        .toList();
  }

  public File get(long fileId) {
    final File file = fileRepository.getById(fileId);
    if (file.isDeleted()) {

      throw new FileNotFoundException(fileId);
    } else {

      return file;
    }

  }

  public File create(long visitId, File file) {
    Visit visit = visitRepository.getById(visitId);
    visit.addFile(file);

    return fileRepository.save(file);
  }

  public File update(long visitId, long fileId, File file) { //fixme unused visitId
    File fileFromDb = get(fileId);
    fileFromDb.modify(file);

    return fileRepository.save(fileFromDb);
  }

  public void delete(long fileId) {
    File fileFromDb = get(fileId);
    fileFromDb.delete();

    fileRepository.save(fileFromDb);
  }


}
