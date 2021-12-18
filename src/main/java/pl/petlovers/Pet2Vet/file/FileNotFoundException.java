package pl.petlovers.Pet2Vet.file;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

public class FileNotFoundException extends RuntimeException {

  public FileNotFoundException (Long id) { super("Couldn't find file with id = "+ id);
  }
}
